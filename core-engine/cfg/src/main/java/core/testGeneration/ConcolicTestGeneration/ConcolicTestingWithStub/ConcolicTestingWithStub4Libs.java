package core.testGeneration.ConcolicTestGeneration.ConcolicTestingWithStub;

import core.FilePath;
import core.ast.Expression.MethodInvocationNode;
import core.cfg.CfgBlockNode;
import core.cfg.CfgBoolExprNode;
import core.cfg.CfgEndBlockNode;
import core.cfg.CfgNode;
import core.cfg.utils.ASTHelper;
import core.cfg.utils.ProjectParser;
import core.path.FindPath;
import core.path.MarkedPath;
import core.path.MarkedStatement;
import core.path.Path;
import core.symbolicExecution.SymbolicExecutionRewrite;
import core.testDriver.LoopPathGenerator;
import core.testDriver.TestDriverGenerator;
import core.testDriver.TestDriverRunner;
import core.testDriver.TestDriverUtils;
import core.testGeneration.ConcolicTestGeneration.ConcolicTestGeneration;
import core.testGeneration.TestGeneration;
import core.testResult.coveredStatement.CoveredStatement;
import core.testResult.result.autoTestResult.TestData;
import core.testResult.result.autoTestResult.TestResult;
import core.uploadProjectUtils.cloneProjectUtils.CloneProject;
import core.utils.Utils;
import org.eclipse.jdt.core.dom.*;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConcolicTestingWithStub4Libs extends ConcolicTestGeneration {
    private static String simpleClassName;
    private static String fullyClonedClassName;
    private static String originalFileLocation;
    private static List<ASTNode> originalParameters; // parameters before adding stub vars

    private ConcolicTestingWithStub4Libs() {
    }

    public static TestResult runFullConcolic(int id, String path, String methodName, String className, TestGeneration.Coverage coverage) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException, InterruptedException {
        setup(path, className, methodName, coverage);
        setupCfgTree(coverage);
        setupParameters(methodName);
        TestGeneration.isSetup = true;

        TestResult result = startGenerating(id, coverage);

        TestGeneration.isSetup = false;

        return result;
    }

    private static TestResult startGenerating(int id, TestGeneration.Coverage coverage) throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException, IOException, InterruptedException, NoSuchMethodException {
        DecimalFormat df = new DecimalFormat("#.##");
        List<Double> memorySamples = Collections.synchronizedList(new ArrayList<>());
        AtomicBoolean isRunning = new AtomicBoolean(true);

        Thread monitorThread = new Thread(() -> {
            Runtime runtime = Runtime.getRuntime();
            while (isRunning.get()) {
                // Đo RAM hiện tại (MB)
                long usedMemory = runtime.totalMemory() - runtime.freeMemory();
                double usedMB = usedMemory / (1024.0 * 1024.0);

                memorySamples.add(usedMB);

                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        monitorThread.start();
        long startTime = System.currentTimeMillis();

        System.out.println(" bắt đầu đo đạc");
        TestResult testResult = new TestResult();

        try {
            testResult.setId(id);

            Object[] evaluatedValues = SymbolicExecutionRewrite.createRandomTestData(TestGeneration.parameterClasses);

            TestGeneration.writeDataToFile("", FilePath.concreteExecuteResultPath, false);

            String testDriver = TestDriverGenerator.generateTestDriverNew((MethodDeclaration) TestGeneration.testFunc, evaluatedValues, TestGeneration.getCoverageType(coverage), originalFileLocation, simpleClassName);
            List<MarkedStatement> markedStatements = TestDriverRunner.newRunTestDriver(testDriver, originalFileLocation);

            MarkedPath.markPathToCFGV2(TestGeneration.cfgBeginNode, markedStatements);

            List<CoveredStatement> coveredStatements = CoveredStatement.switchToCoveredStatementList(markedStatements);

            testResult.addToFullTestData(new TestData(TestGeneration.parameterNames, TestGeneration.parameterClasses, evaluatedValues, coveredStatements,
                    TestDriverRunner.getOutput(), TestDriverRunner.getRuntime(), calculateRequiredCoverage(coverage), calculateFunctionCoverage(), calculateSourceCodeCoverage()));


            // ====== LICO testing method ======
            List<Path> licoPaths = LoopPathGenerator.generateLicoPaths(TestGeneration.cfgBeginNode, TestGeneration.cfgEndNode);
            int cnt = 1;
            for (Path path : licoPaths) {
                System.out.println("LICO đang chạy path: " + cnt);
                cnt++;
                solveAndRunTest(path, testResult, coverage);
            }
            // ==================================


            boolean isTestedSuccessfully = true;

            for (CfgNode uncoveredNode = TestGeneration.findUncoverNode(TestGeneration.cfgBeginNode, coverage); uncoveredNode != null; uncoveredNode = TestGeneration.findUncoverNode(TestGeneration.cfgBeginNode, coverage)) {
                System.out.println("Uncovered Node: " + uncoveredNode);

                Path newPath = (new FindPath(TestGeneration.cfgBeginNode, uncoveredNode, TestGeneration.cfgEndNode)).getPath();

                boolean success = solveAndRunTest(newPath, testResult, coverage);

                if (!success) {
                    uncoveredNode.setFakeMarked(true);

                    if (coverage == Coverage.MCDC || coverage == Coverage.BRANCH) {
                        CfgNode parent = uncoveredNode.getParent();
                        if (parent instanceof CfgBoolExprNode) {
                            CfgBoolExprNode cfgBoolExprNode = (CfgBoolExprNode) parent;
                            if (cfgBoolExprNode.getTrueNode() == uncoveredNode) {
                                cfgBoolExprNode.setFakeTrueMarked(true);
                            } else if (cfgBoolExprNode.getFalseNode() == uncoveredNode) {
                                cfgBoolExprNode.setFakeFalseMarked(true);
                            }
                        }
                    }
                }
            }

            if (isTestedSuccessfully) System.out.println("Tested successfully with 100% coverage");
            else System.out.println("Test fail due to UNSATISFIABLE constraint");

            testResult.setFullCoverage(calculateFullTestSuiteCoverage(coverage));

            // 1. Xây dựng Map tìm kiếm nhanh từ Content -> CfgNode
            Map<String, CfgNode> contentToCfgNodeMap = buildContentToNodeMap(TestGeneration.cfgBeginNode);

            // 2. Duyệt qua từng TestData (mỗi test case sinh ra)
            for (TestData data : testResult.getFullTestData()) {
                List<CoveredStatement> originalList = data.getCoveredStatements();
                List<CoveredStatement> translatedList = new ArrayList<>();

                // Dùng Set để tránh trùng lặp (vì if/else con có thể trỏ về cùng 1 cha)
                Set<String> addedStatements = new HashSet<>();

                for (CoveredStatement stmt : originalList) {
                    String oldContent = stmt.getStatementContent();

                    // Tìm node trong CFG
                    CfgNode node = contentToCfgNodeMap.get(oldContent.trim());

                    ASTNode originalAst = null;
                    if (node != null) {
                        // Tra từ điển ánh xạ ngược (ASTHelper)
                        originalAst = ASTHelper.syntheticToOriginalMap.get(node.getAst());
                    }

                    if (originalAst != null) {
                        String newContent = originalAst.toString();

                        // Chỉ thêm nếu chưa có trong danh sách hiển thị
                        if (!addedStatements.contains(newContent)) {
                            // Tạo statement mới với nội dung gốc
                            // Giữ nguyên lineNumber vì logic setSourceRange đã làm nó đúng
                            translatedList.add(new CoveredStatement(newContent, stmt.getLineNumber(), stmt.getConditionStatus()));
                            addedStatements.add(newContent);
                        }
                    } else {
                        // Không tìm thấy cha, đây là node bình thường -> Giữ nguyên
                        if (!addedStatements.contains(oldContent)) {
                            translatedList.add(stmt);
                            addedStatements.add(oldContent);
                        }
                    }
                }
                // Cập nhật lại danh sách hiển thị cho TestData này
                data.setCoveredStatements(translatedList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            isRunning.set(false);
            monitorThread.interrupt();
            long endTime = System.currentTimeMillis();

            double sum = 0;
            for (Double sample : memorySamples) {
                sum += sample;
            }

            double averageMemory = 0;
            if (memorySamples.isEmpty()) {
                long used = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                averageMemory = used / (1024.0 * 1024.0);
                System.out.println("Chạy quá nhanh, lấy ram tức thời.");
            } else {
                averageMemory = sum / memorySamples.size();
            }

            System.out.println("\n==========================================");
            System.out.println("BÁO CÁO HIỆU NĂNG");
            System.out.println("==========================================");
            System.out.println("Số mẫu đã đo    : " + memorySamples.size() + " lần");
            System.out.println("RAM Trung bình  : " + df.format(averageMemory) + " MB");

            if (!memorySamples.isEmpty()) {
                double maxMem = Collections.max(memorySamples);
                System.out.println("RAM Đỉnh: " + df.format(maxMem) + " MB");
            }

            System.out.println("Tổng thời gian: " + df.format((endTime - startTime) / 1000.0) + " s");
            System.out.println("==========================================\n");
        }

        double totalTime = 0;
        for (TestData data : testResult.getFullTestData()) {
            totalTime += data.getExecuteTime();
        }

        System.out.println("Tổng thời gian Execute Time: " + totalTime);

        try {
            List<Object[]> generatedInputs = new ArrayList<>();
            for (TestData data : testResult.getFullTestData()) {
                generatedInputs.add(data.getTestDataSet().toArray());
            }

            // Specify the name of the target method to be analyzed.
            // This value must match the Java class and method name in the source project.
            String classBaseName = "binaryGap";

            String fullyClonedClassName = classBaseName + "." + classBaseName;

            List<String> mutants = new ArrayList<>();
            for (int i = 1; i <= 4; i++) {
                mutants.add(classBaseName + "." + classBaseName + i);
            }

            String targetMethodName =
                    ((MethodDeclaration) TestGeneration.testFunc)
                            .getName()
                            .getIdentifier();

            // Define the absolute path to the Java source file of the target method.
            // Users should ensure that the directory structure and file name
            // correspond to the specified method name.
            String fullPath = Paths.get(
                    System.getProperty("user.home"),
                    "working", "uet", "LICO",
                    "project", "anonymous", "tmp-prj",
                    "example.zip.project", "example",
                    "src", "main", "java",
                    classBaseName,
                    classBaseName + ".java"
            ).toString();

            String packageName = classBaseName;

            String rootPath = getRootSourcePath(fullPath, packageName);

            System.out.println("Path nạp vào ClassLoader: " + rootPath);

            int killed = runMutationTest(fullyClonedClassName, mutants, generatedInputs, targetMethodName, TestGeneration.parameterClasses, rootPath);

        } catch (Exception e) {
            System.err.println("Lỗi khi chạy Test: " + e.getMessage());
            e.printStackTrace();
        }

        return testResult;
    }

    public static String getRootSourcePath(String fullFilePath, String packageName) {

        File file = new File(fullFilePath);
        String parentDir = file.getParent();

        int srcIndex = fullFilePath.indexOf("src" + File.separator + "main" + File.separator + "java");
        if (srcIndex != -1) {
            String rootPath = fullFilePath.substring(0, srcIndex + ("src" + File.separator + "main" + File.separator + "java").length());
            return rootPath;
        }
        File currentDir = new File(parentDir);
        String[] packageParts = packageName.split("\\.");
        for (int i = 0; i < packageParts.length; i++) {
            currentDir = currentDir.getParentFile();
        }
        return currentDir.getAbsolutePath();
    }

    private static void setup(String path, String className, String methodName, TestGeneration.Coverage coverage) throws IOException, InterruptedException {
        TestGeneration.funcAstNodeList = ProjectParser.parseFile(path);
        TestGeneration.compilationUnit = ProjectParser.parseFileToCompilationUnit(path);
        classKey = (TestGeneration.compilationUnit.getPackage() != null ? TestGeneration.compilationUnit.getPackage().getName().toString() : "") + className.replace(".java", "") + "totalStatement";

        setupFullyClonedClassName(className, path, coverage);
        setUpTestFunc(methodName);
        MarkedPath.resetFullTestSuiteCoveredStatements();

        MethodInvocationNode.resetNumberOfFunctionsCall();
    }

    private static void setupFullyClonedClassName(String className, String path, TestGeneration.Coverage coverage) throws IOException, InterruptedException {
        String newPath = getRootProjectPath(path);
        java.nio.file.Path rootPackage = CloneProject.findRootPackage(Paths.get(newPath));
        CloneProject.cloneProject(rootPackage.toString(), FilePath.clonedProjectPath, getCoverageType(coverage), className);
        className = className.replace(".java", "");
        simpleClassName = getClassFromCU(compilationUnit);

        // Xóa file .java (bỏ cả tên file)
        String relative = path.substring(rootPackage.toString().length() + 1);
//        relative = relative.replace("\\", "/");
        int lastSlash = relative.lastIndexOf(File.separator);
        if (lastSlash != -1) {
            relative = relative.substring(0, lastSlash + 1);
        } else {
            relative = "";
        }

        // Đổi "/" thành "."
        String packetName = relative.replace(File.separator, ".");
        System.out.println(packetName);

        originalFileLocation = "data.clonedProject." + packetName + className;
        fullyClonedClassName = "data.clonedProject." + packetName + simpleClassName;
    }

    private static String getClassFromCU(CompilationUnit compilationUnit) {
        List<TypeDeclaration> classes = new ArrayList<>();
        compilationUnit.accept(new ASTVisitor() {
            @Override
            public boolean visit(TypeDeclaration node) {
                classes.add(node);
                return super.visit(node);
            }
        });
        return classes.get(0).getName().toString();
    }

    private static double calculateFullTestSuiteCoverage(Coverage coverage) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Field field = Class.forName(fullyClonedClassName).getField(getTotalFunctionCoverageVariableName((MethodDeclaration) TestGeneration.testFunc, coverage));
        field.setAccessible(true);
        int totalFunctionStatement = (int) field.get(null);
        int totalCovered = 0;
        if (coverage == Coverage.STATEMENT) {
            totalCovered = MarkedPath.getFullTestSuiteTotalCoveredStatements();
        } else { // branch
            totalCovered = MarkedPath.getFullTestSuiteTotalCoveredBranch();
        }
        return (totalCovered * 100.0) / totalFunctionStatement;
    }

    private static double calculateRequiredCoverage(TestGeneration.Coverage coverage) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Field field = Class.forName(fullyClonedClassName).getField(getTotalFunctionCoverageVariableName((MethodDeclaration) TestGeneration.testFunc, coverage));
        field.setAccessible(true);
        int totalFunctionCoverage = (int) field.get(null);
        int totalCovered = 0;
        if (coverage == TestGeneration.Coverage.STATEMENT) {
            totalCovered = MarkedPath.getTotalCoveredStatement();
        } else if (coverage == TestGeneration.Coverage.BRANCH || coverage == TestGeneration.Coverage.MCDC) {
            totalCovered = MarkedPath.getTotalCoveredBranch();
            System.out.println(totalCovered);
        }
        return (totalCovered * 100.0) / totalFunctionCoverage;
    }

    private static double calculateFunctionCoverage() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Field field = Class.forName(fullyClonedClassName).getField(getTotalFunctionCoverageVariableName((MethodDeclaration) TestGeneration.testFunc, TestGeneration.Coverage.STATEMENT));
        field.setAccessible(true);
        int totalFunctionStatement = (int) field.get(null);
        int totalCoveredStatement = MarkedPath.getTotalCoveredStatement();
        return (totalCoveredStatement * 100.0) / (totalFunctionStatement * 1.0);
    }

    private static double calculateSourceCodeCoverage() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Field field = Class.forName(fullyClonedClassName).getField(getTotalClassCoverageVariableName());
        field.setAccessible(true);
        int totalClassStatement = (int) field.get(null);
        int totalCoveredStatement = MarkedPath.getTotalCoveredStatement();
        return (totalCoveredStatement * 100.0) / (totalClassStatement * 1.0);
    }

    private static void setUpTestFunc(String methodName) {
        for (ASTNode func : TestGeneration.funcAstNodeList) {
            if (((MethodDeclaration) func).getName().getIdentifier().equals(methodName)) {
                TestGeneration.testFunc = func;
            }
        }
        originalParameters = new ArrayList<>(((MethodDeclaration) testFunc).parameters());
    }

    private static void setupParameters(String methodName) {
        TestGeneration.parameters = ((MethodDeclaration) TestGeneration.testFunc).parameters();
        TestGeneration.parameterClasses = TestDriverUtils.getParameterClasses(TestGeneration.parameters);
        TestGeneration.parameterNames = TestDriverUtils.getParameterNames(TestGeneration.parameters);
    }

    private static void setupCfgTree(TestGeneration.Coverage coverage) {
        Block functionBlock = Utils.getFunctionBlock(TestGeneration.testFunc);

        TestGeneration.cfgBeginNode = new CfgNode();
        TestGeneration.cfgBeginNode.setIsBeginCfgNode(true);

        TestGeneration.cfgEndNode = new CfgEndBlockNode();
        TestGeneration.cfgEndNode.setIsEndCfgNode(true);

        CfgNode block = new CfgBlockNode();
        block.setAst(functionBlock);

        int firstLine = TestGeneration.compilationUnit.getLineNumber(functionBlock.getStartPosition());
        block.setLineNumber(1);

        block.setBeforeStatementNode(TestGeneration.cfgBeginNode);
        block.setAfterStatementNode(TestGeneration.cfgEndNode);

        ASTHelper.generateCFG(block, TestGeneration.compilationUnit, firstLine, TestGeneration.getCoverageType(coverage));
    }

    private static Map<String, CfgNode> buildContentToNodeMap(CfgNode root) {
        Map<String, CfgNode> map = new HashMap<>();
        if (root == null) return map;

        Queue<CfgNode> q = new LinkedList<>();
        Set<CfgNode> visited = new HashSet<>();
        q.add(root);

        while (!q.isEmpty()) {
            CfgNode curr = q.poll();
            if (curr == null || visited.contains(curr)) continue;
            visited.add(curr);

            if (curr.getContent() != null) {
                map.put(curr.getContent().trim(), curr);
            }

            if (curr instanceof core.cfg.CfgBoolExprNode) {
                q.add(((core.cfg.CfgBoolExprNode) curr).getTrueNode());
                q.add(((core.cfg.CfgBoolExprNode) curr).getFalseNode());
            }
            q.add(curr.getAfterStatementNode());
        }
        return map;
    }

    private static boolean solveAndRunTest(Path path, TestResult testResult, TestGeneration.Coverage coverage)
            throws IOException, InterruptedException, InvocationTargetException,
            IllegalAccessException, NoSuchMethodException, ClassNotFoundException, NoSuchFieldException {

        SymbolicExecutionRewrite solution = new SymbolicExecutionRewrite(path, TestGeneration.parameters);

        try {
            solution.execute();
        } catch (RuntimeException e) {
            System.out.println("UNSATISFIABLE");
            return false;
        }

        TestGeneration.parameterNames = TestDriverUtils.getParameterNames(TestGeneration.parameters);
        TestGeneration.parameterClasses = TestDriverUtils.getParameterClasses(TestGeneration.parameters);

        Object[] evaluatedValues = solution.getEvaluatedTestData(TestGeneration.parameterClasses);

        System.out.println("Z3 giải ra input: " + Arrays.toString(evaluatedValues));

        TestGeneration.writeDataToFile("", FilePath.concreteExecuteResultPath, false);

        String testDriver = TestDriverGenerator.generateTestDriverNew(
                (MethodDeclaration) TestGeneration.testFunc,
                evaluatedValues,
                TestGeneration.getCoverageType(coverage),
                originalFileLocation,
                simpleClassName
        );

        List<MarkedStatement> markedStatements = TestDriverRunner.newRunTestDriver(testDriver, originalFileLocation);

        MarkedPath.markPathToCFGV2(TestGeneration.cfgBeginNode, markedStatements);

        List<CoveredStatement> coveredStatements = CoveredStatement.switchToCoveredStatementList(markedStatements);

        testResult.addToFullTestData(new TestData(
                TestGeneration.parameterNames,
                TestGeneration.parameterClasses,
                evaluatedValues,
                coveredStatements,
                TestDriverRunner.getOutput(),
                TestDriverRunner.getRuntime(),
                calculateRequiredCoverage(coverage),
                calculateFunctionCoverage(),
                calculateSourceCodeCoverage()
        ));

        return true;
    }

    private static String getTotalClassCoverageVariableName() {
        StringBuilder result = new StringBuilder();
        result.append(simpleClassName).append("TotalStatement");
        return result.toString().replace(" ", "").replace(".", "");
    }

    protected static String getTotalFunctionCoverageVariableName(MethodDeclaration methodDeclaration, TestGeneration.Coverage coverage) {
        StringBuilder result = new StringBuilder();
        result.append(methodDeclaration.getReturnType2());
        result.append(methodDeclaration.getName());
        for (int i = 0; i < originalParameters.size(); i++) {
            result.append(originalParameters.get(i));
        }
        if (coverage == TestGeneration.Coverage.STATEMENT) {
            result.append("TotalStatement");
        } else if (coverage == TestGeneration.Coverage.BRANCH || coverage == TestGeneration.Coverage.MCDC) {
            result.append("TotalBranch");
        } else {
            throw new RuntimeException("Invalid Coverage");
        }

        return reformatVariableName(result.toString());
    }

    private static String reformatVariableName(String name) {
        return name.replace(" ", "").replace(".", "")
                .replace("[", "").replace("]", "")
                .replace("<", "").replace(">", "")
                .replace(",", "");
    }

    private static String getRootProjectPath(String path) {
        // Chuẩn hóa dấu gạch chéo
        String newPath = path.replace("\\", "/");

        // Tách các phần theo "/"
        String[] parts = newPath.split("/");

        // Nối lại từ đầu tới folder thứ 6
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            if (i > 0) sb.append("/");
            sb.append(parts[i]);
        }

        return sb.toString();
    }

    private static int runMutationTest(String originalClassName, List<String> errorClassNames,
                                       List<Object[]> testDataList, String methodName,
                                       Class<?>[] paramTypes, String rootFolderPath) {
        int errorsDetected = 0;
        System.out.println("\n>>> BẮT ĐẦU QUÁ TRÌNH KIỂM THỬ TỰ ĐỘNG (ERROR DETECTION)");

        try {
            //  BƯỚC 1: CHUẨN BỊ COMPILER (Giữ nguyên)
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            if (compiler == null) {
                System.err.println("LỖI: Không tìm thấy Java Compiler (JDK)!");
                return 0;
            }

            File root = new File(rootFolderPath);
            if (!root.exists()) return 0;

            // BƯỚC 2: COMPILE FILE GỐC (Giữ nguyên)
            String originalFilePath = rootFolderPath + File.separator + originalClassName.replace(".", File.separator) + ".java";
            File originalFile = new File(originalFilePath);

            if (originalFile.exists()) {
                compiler.run(null, null, null, originalFile.getAbsolutePath());
            } else {
                System.err.println("Không tìm thấy file source gốc: " + originalFilePath);
                return 0;
            }

            //  BƯỚC 3: COMPILE CÁC FILE LỖI (ERRORS/BUGS)
            for (String errorClassName : errorClassNames) {
                String errorFilePath = rootFolderPath + File.separator + errorClassName.replace(".", File.separator) + ".java";
                File errorFile = new File(errorFilePath);
                if (errorFile.exists()) {
                    compiler.run(null, null, null, errorFile.getAbsolutePath());
                }
            }

            //  BƯỚC 4: LOAD CLASS VÀ CHẠY TEST
            java.net.URL[] urls = {root.toURI().toURL()};
            java.net.URLClassLoader classLoader = new java.net.URLClassLoader(urls);

            Class<?> originalClass = classLoader.loadClass(originalClassName);

            for (String errorClassName : errorClassNames) {
                boolean isDetected = false;

                try {
                    Class<?> errorClass = classLoader.loadClass(errorClassName);
                    System.out.print("Checking Bug Case: " + errorClass.getSimpleName() + "... ");

                    for (Object[] args : testDataList) {
                        try {
                            Object originalObj = originalClass.getDeclaredConstructor().newInstance();
                            Object errorObj = errorClass.getDeclaredConstructor().newInstance(); // errorObj thay vì mutantObj

                            Method originalMethod = originalClass.getMethod(methodName, paramTypes);
                            Method errorMethod = errorClass.getMethod(methodName, paramTypes);

                            Object originalOutput = null;
                            Exception originalException = null;
                            try {
                                originalOutput = originalMethod.invoke(originalObj, args);
                            } catch (InvocationTargetException e) {
                                originalException = (Exception) e.getTargetException();
                            }

                            Object errorOutput = null;
                            Exception errorException = null;
                            try {
                                errorOutput = errorMethod.invoke(errorObj, args);
                            } catch (InvocationTargetException e) {
                                errorException = (Exception) e.getTargetException();
                            }
                            if ((originalException == null && errorException != null) ||
                                    (originalException == null && errorException == null && !originalOutput.equals(errorOutput))) {
                                isDetected = true;
                                break;
                            }
                        } catch (Exception e) {
                        }
                    }

                    if (isDetected) {
                        errorsDetected++;
                        System.out.println("DETECTED (Đã tìm ra)");
                    } else {
                        System.out.println("UNDETECTED (Không tìm thấy)");
                    }
                } catch (ClassNotFoundException e) {
                    System.err.println("Lỗi: Không tìm thấy class lỗi: " + errorClassName);
                }
            }

            classLoader.close();

            System.out.println("==========================================");
            System.out.println("ERROR DETECTION REPORT (BÁO CÁO PHÁT HIỆN LỖI)");
            System.out.println("Inserted Error Number (Tổng số lỗi cấy vào): " + errorClassNames.size());
            System.out.println("Detected Error Number (Số lỗi tìm thấy):     " + errorsDetected);
            double rate = errorClassNames.isEmpty() ? 0 : (double) errorsDetected / errorClassNames.size() * 100;
            DecimalFormat df = new DecimalFormat("#.##");
            System.out.println("Detection Rate (Tỷ lệ phát hiện):            " + df.format(rate) + "%");
            System.out.println("==========================================");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return errorsDetected;
    }

}