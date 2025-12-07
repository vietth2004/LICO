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
import core.symbolicExecution.SymbolicExecution;
import core.symbolicExecution.SymbolicExecutionRewrite;
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

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.util.*;

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
        TestResult testResult = new TestResult();
        testResult.setId(id);

        Object[] evaluatedValues = SymbolicExecution.createRandomTestData(TestGeneration.parameterClasses);

        TestGeneration.writeDataToFile("", FilePath.concreteExecuteResultPath, false);

        String testDriver = TestDriverGenerator.generateTestDriverNew((MethodDeclaration) TestGeneration.testFunc, evaluatedValues, TestGeneration.getCoverageType(coverage), originalFileLocation, simpleClassName);
        List<MarkedStatement> markedStatements = TestDriverRunner.newRunTestDriver(testDriver, originalFileLocation);

        MarkedPath.markPathToCFGV2(TestGeneration.cfgBeginNode, markedStatements);

        List<CoveredStatement> coveredStatements = CoveredStatement.switchToCoveredStatementList(markedStatements);

        testResult.addToFullTestData(new TestData(TestGeneration.parameterNames, TestGeneration.parameterClasses, evaluatedValues, coveredStatements,
                TestDriverRunner.getOutput(), TestDriverRunner.getRuntime(), calculateRequiredCoverage(coverage), calculateFunctionCoverage(), calculateSourceCodeCoverage()));

//        // test Lico
//        List<Path> licoPaths = LoopPathGenerator.generateLicoPaths(TestGeneration.cfgBeginNode, TestGeneration.cfgEndNode);
//
//        for (Path path : licoPaths) {
//            System.out.println("LICO đang chạy path: " + path);
//            solveAndRunTest(path, testResult, coverage);
//        }

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

//            List<String> testDataNames = new ArrayList<>();
//            testDataNames.addAll(TestGeneration.parameterNames);
//            testDataNames.addAll(SymbolicExecution.getStubVariableNames());

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

        return testResult;
    }

    private static void setup(String path, String className, String methodName, TestGeneration.Coverage coverage) throws IOException, InterruptedException {
        TestGeneration.funcAstNodeList = ProjectParser.parseFile(path);
        TestGeneration.compilationUnit = ProjectParser.parseFileToCompilationUnit(path);
        classKey = (TestGeneration.compilationUnit.getPackage() != null ? TestGeneration.compilationUnit.getPackage().getName().toString() : "") + className.replace(".java", "") + "totalStatement";
        String newPath = getRootProjectPath(path);
        java.nio.file.Path rootPackage = CloneProject.findRootPackage(Paths.get(newPath));
        CloneProject.cloneProject(rootPackage.toString(), FilePath.clonedProjectPath, getCoverageType(coverage), className);
        setupFullyClonedClassName(className, path, rootPackage.toString());
        setUpTestFunc(methodName);
        MarkedPath.resetFullTestSuiteCoveredStatements();

        MethodInvocationNode.resetNumberOfFunctionsCall();
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

    private static void setupFullyClonedClassName(String className, String path, String rootPackage) throws IOException {
        className = className.replace(".java", "");
        simpleClassName = getClassFromCU(compilationUnit);

        // Xóa file .java (bỏ cả tên file)
        String relative = path.substring(rootPackage.length() + 1);
        int lastSlash = relative.lastIndexOf("\\");
        if (lastSlash != -1) {
            relative = relative.substring(0, lastSlash + 1);
        } else {
            relative = "";
        }

        // Đổi "/" thành "."
        String packetName = relative.replace("\\", ".");
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
            return false;
        }

        TestGeneration.parameterNames = TestDriverUtils.getParameterNames(TestGeneration.parameters);
        TestGeneration.parameterClasses = TestDriverUtils.getParameterClasses(TestGeneration.parameters);

        Object[] evaluatedValues = solution.getEvaluatedTestData(TestGeneration.parameterClasses);

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
}

