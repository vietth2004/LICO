package com.example.unittesting.utils.testing;

import com.example.unittesting.model.coveredStatement.CoveredStatement;
import com.example.unittesting.model.result.Concolic.ConcolicTestData;
import com.example.unittesting.model.result.Concolic.ConcolicTestResult;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import core.algorithms.FindAllPath;
import core.algorithms.FindPath;
import core.algorithms.SymbolicExecution;
import core.cfg.CfgBlockNode;
import core.cfg.CfgEndBlockNode;
import core.cfg.CfgNode;
import core.dataStructure.MarkedPath;
import core.dataStructure.MarkedStatement;
import core.dataStructure.Path;
import core.parser.ASTHelper;
import core.parser.ProjectParser;
import core.testDriver.TestDriverGenerator;
import core.testDriver.TestDriverRunner;
import core.utils.Utils;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static core.testDriver.Utils.*;

@JsonAutoDetect
@Component
public class ConcolicTesting {

    public enum Coverage {
        STATEMENT,
        BRANCH,
        MCDC,
        PATH
    }

    private static StringBuilder report;
    private static CompilationUnit compilationUnit;
    private static String simpleClassName;
    private static String fullyClonedClassName;
    private static ArrayList<ASTNode> funcAstNodeList;
    private static CfgNode cfgBeginNode;
    private static CfgEndBlockNode cfgEndNode;
    private static List<ASTNode> parameters;
    private static Class<?>[] parameterClasses;
    private static List<String> parameterNames;
    private static Method method;
    private static ASTNode testFunc;

    private ConcolicTesting() {
    }

    private static long totalUsedMem = 0;
    private static long tickCount = 0;

    public static ConcolicTestResult runFullConcolic(String path, String methodName, String className, Coverage coverage) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException, InterruptedException {

        setup(path, className, methodName);
        setupCfgTree(coverage);
//        setupParameters(methodName);
        setUpParametersV2(methodName);

        if(coverage == Coverage.STATEMENT || coverage == Coverage.BRANCH || coverage == Coverage.MCDC) {

            totalUsedMem = 0;
            tickCount = 0;
            Timer T = new Timer(true);

            TimerTask memoryTask = new TimerTask(){
                @Override
                public void run(){
                    totalUsedMem += (Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory());
                    tickCount += 1;
                }
            };

            T.scheduleAtFixedRate(memoryTask, 0, 1); //0 delay and 5 ms tick

            long startRunTestTime = System.nanoTime();
            ConcolicTestResult result = testingWithStatementAndBranchCoverage(coverage);
            long endRunTestTime = System.nanoTime();

            double runTestDuration = (endRunTestTime - startRunTestTime) / 1000000.0;

            System.out.println("run time: " + runTestDuration);

            float usedMem = ((float)totalUsedMem)/tickCount/1024/1024;
            System.out.print("used mem = ");
            System.out.printf("%.2f", usedMem);
            System.out.println(" MB");
            return result;
        } else {
            return testingWithPathCoverage();
        }
    }

    private static ConcolicTestResult testingWithPathCoverage() throws InvocationTargetException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
        ConcolicTestResult testResult = new ConcolicTestResult();

        List<Path> paths = (new FindAllPath(cfgBeginNode)).getPaths();
        System.out.println(paths.size());

        for (int i = 0; i < paths.size(); i++) {
            SymbolicExecution solution = new SymbolicExecution(paths.get(i), parameters);

            if(solution.getModel() == null) continue;

            Object[] evaluatedValues = getParameterValue(parameterClasses);
            long startRunTestTime = System.nanoTime();
            Object output = method.invoke(parameterClasses, evaluatedValues);
            long endRunTestTime = System.nanoTime();
            double runTestDuration = (endRunTestTime - startRunTestTime) / 1000000.0;
            List<CoveredStatement> coveredStatements = CoveredStatement.switchToCoveredStatementList(MarkedPath.isPathActuallyCovered(paths.get(i)));

            testResult.addToFullTestData(new ConcolicTestData(parameterNames, parameterClasses, evaluatedValues, coveredStatements, output, runTestDuration, 100.0 / paths.size(), calculateFunctionCoverage(), calculateSourceCodeCoverage()));
        }

        testResult.setFullCoverage(calculateFullTestSuiteCoverage());

        return testResult;
    }

    private static ConcolicTestResult testingWithStatementAndBranchCoverage(Coverage coverage) throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException {
        ConcolicTestResult testResult = new ConcolicTestResult();
        Object[] evaluatedValues = createRandomTestData(parameterClasses);

        long startRunTestTime = System.nanoTime();
        Object output = method.invoke(parameterClasses, evaluatedValues);
        long endRunTestTime = System.nanoTime();
        double runTestDuration = (endRunTestTime - startRunTestTime) / 1000000.0;

        List<CoveredStatement> coveredStatements = CoveredStatement.switchToCoveredStatementList(MarkedPath.markPathToCFG(cfgBeginNode));
        report.append("STEP 3: Sinh dữ liệu ngẫu nhiên cho các parameter ").append(Arrays.toString(parameterClasses)).append(": ");
        report.append(Arrays.toString(evaluatedValues)).append("\n");
        report.append("STEP 4: Chạy dữ liệu ngẫu nhiên đấy, lưu những câu lệnh đã được chạy qua: ").append(coveredStatements).append("\n");

        testResult.addToFullTestData(new ConcolicTestData(parameterNames, parameterClasses, evaluatedValues, coveredStatements,
                output, runTestDuration, calculateRequiredCoverage(coverage), calculateFunctionCoverage(), calculateSourceCodeCoverage()));

        boolean isTestedSuccessfully = true;
        int i = 5;

        for (CfgNode uncoveredNode = findUncoverNode(cfgBeginNode, coverage); uncoveredNode != null; ) {
            report.append("STEP ").append(i++).append(": Tìm node chưa được phủ: ").append(uncoveredNode).append("\n");

            Path newPath = (new FindPath(cfgBeginNode, uncoveredNode, cfgEndNode)).getPath();
            report.append("STEP ").append(i++).append(": Sinh đường thi hành từ node chưa được phủ đấy\n");

            SymbolicExecution solution = new SymbolicExecution(newPath, parameters);

            if (solution.getModel() == null) {
                isTestedSuccessfully = false;
                break;
            }

            evaluatedValues = getParameterValue(parameterClasses);
            report.append("STEP ").append(i++).append(": Thực thi tượng trưng đường thi hành và sinh test data tương ứng: ");
            report.append(Arrays.toString(evaluatedValues)).append("\n");


            startRunTestTime = System.nanoTime();
            output = method.invoke(parameterClasses, evaluatedValues);
            endRunTestTime = System.nanoTime();
            runTestDuration = (endRunTestTime - startRunTestTime) / 1000000.0;

            coveredStatements = CoveredStatement.switchToCoveredStatementList(MarkedPath.markPathToCFG(cfgBeginNode));

            report.append("STEP ").append(i++).append(": Đánh dấu những câu lệnh (node) đã chạy qua sau khi thực hiện chạy hàm với dữ liệu vừa được sinh: ");
            report.append(coveredStatements).append("\n");

            testResult.addToFullTestData(new ConcolicTestData(parameterNames, parameterClasses, evaluatedValues, coveredStatements, output, runTestDuration, calculateRequiredCoverage(coverage), calculateFunctionCoverage(), calculateSourceCodeCoverage()));

            uncoveredNode = findUncoverNode(cfgBeginNode, coverage);
            System.out.println("Uncovered Node: " + uncoveredNode);
        }

        report.append("STEP ").append(i).append("Kết thúc việc kiểm thử");

        writeDataToFile(report.toString(), "core-engine/cfg/src/main/java/data/report.txt");

        if (isTestedSuccessfully) System.out.println("Tested successfully with 100% coverage");
        else System.out.println("Test fail due to UNSATISFIABLE constraint");

        testResult.setFullCoverage(calculateFullTestSuiteCoverage());

        return testResult;
    }

    private static ConcolicTestResult testingWithStatement_Branch_MCDCCoverageV2(Coverage coverage) throws IllegalAccessException, ClassNotFoundException, NoSuchFieldException, IOException, InterruptedException {
        ConcolicTestResult testResult = new ConcolicTestResult();
        Object[] evaluatedValues = createRandomTestData(parameterClasses);

        String testDriver = TestDriverGenerator.generateTestDriver((MethodDeclaration) testFunc, evaluatedValues);
        List<MarkedStatement> markedStatements = TestDriverRunner.runTestDriver(testDriver);
        MarkedPath.markPathToCFGV2(cfgBeginNode, markedStatements);

        List<CoveredStatement> coveredStatements = CoveredStatement.switchToCoveredStatementList(markedStatements);
        report.append("STEP 3: Sinh dữ liệu ngẫu nhiên cho các parameter ").append(Arrays.toString(parameterClasses)).append(": ");
        report.append(Arrays.toString(evaluatedValues)).append("\n");
        report.append("STEP 4: Chạy dữ liệu ngẫu nhiên đấy, lưu những câu lệnh đã được chạy qua: ").append(coveredStatements).append("\n");

        testResult.addToFullTestData(new ConcolicTestData(parameterNames, parameterClasses, evaluatedValues, coveredStatements,
                TestDriverRunner.getOutput(), TestDriverRunner.getRuntime(), calculateRequiredCoverage(coverage), calculateFunctionCoverage(), calculateSourceCodeCoverage()));

        boolean isTestedSuccessfully = true;
        int i = 5;

        for (CfgNode uncoveredNode = findUncoverNode(cfgBeginNode, coverage); uncoveredNode != null; ) {
            report.append("STEP ").append(i++).append(": Tìm node chưa được phủ: ").append(uncoveredNode).append("\n");

            Path newPath = (new FindPath(cfgBeginNode, uncoveredNode, cfgEndNode)).getPath();
            report.append("STEP ").append(i++).append(": Sinh đường thi hành từ node chưa được phủ đấy\n");

            SymbolicExecution solution = new SymbolicExecution(newPath, parameters);

            if (solution.getModel() == null) {
                isTestedSuccessfully = false;
                break;
            }

            evaluatedValues = getParameterValue(parameterClasses);
            report.append("STEP ").append(i++).append(": Thực thi tượng trưng đường thi hành và sinh test data tương ứng: ");
            report.append(Arrays.toString(evaluatedValues)).append("\n");

            testDriver = TestDriverGenerator.generateTestDriver((MethodDeclaration) testFunc, evaluatedValues);
            markedStatements = TestDriverRunner.runTestDriver(testDriver);
            MarkedPath.markPathToCFGV2(cfgBeginNode, markedStatements);

            coveredStatements = CoveredStatement.switchToCoveredStatementList(markedStatements);

            report.append("STEP ").append(i++).append(": Đánh dấu những câu lệnh (node) đã chạy qua sau khi thực hiện chạy hàm với dữ liệu vừa được sinh: ");
            report.append(coveredStatements).append("\n");

            testResult.addToFullTestData(new ConcolicTestData(parameterNames, parameterClasses, evaluatedValues, coveredStatements, TestDriverRunner.getOutput(), TestDriverRunner.getRuntime(), calculateRequiredCoverage(coverage), calculateFunctionCoverage(), calculateSourceCodeCoverage()));

            uncoveredNode = findUncoverNode(cfgBeginNode, coverage);
            System.out.println("Uncovered Node: " + uncoveredNode);
        }

        report.append("STEP ").append(i).append("Kết thúc việc kiểm thử");

        writeDataToFile(report.toString(), "core-engine/cfg/src/main/java/data/report.txt");

        if (isTestedSuccessfully) System.out.println("Tested successfully with 100% coverage");
        else System.out.println("Test fail due to UNSATISFIABLE constraint");

        testResult.setFullCoverage(calculateFullTestSuiteCoverage());

        return testResult;
    }

    private static CfgNode findUncoverNode(CfgNode cfgNode, Coverage coverage) {
        switch (coverage) {
            case STATEMENT:
                return MarkedPath.findUncoveredStatement(cfgNode);
            case BRANCH:
                return MarkedPath.findUncoveredBranch(cfgNode);
            default:
                throw new RuntimeException("Invalid coverage type");
        }
    }

    private static void setup(String path, String className, String methodName) throws IOException {
        report = new StringBuilder();

        // Parse File
        report.append("Parse toàn bộ các hàm trong class cần kiểm thử thành 1 danh sách ASTNode\n");
        funcAstNodeList = ProjectParser.parseFile(path);
        compilationUnit = ProjectParser.parseFileToCompilationUnit(path);
        setupFullyClonedClassName(className);
        setUpTestFunc(methodName);
        MarkedPath.resetFullTestSuiteCoveredStatements();
    }

    private static double calculateFullTestSuiteCoverage() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        int totalFunctionStatement = (int) Class.forName(fullyClonedClassName).getField(getTotalFunctionCoverageVariableName((MethodDeclaration) testFunc, Coverage.STATEMENT)).get(null);
        int totalCovered = MarkedPath.getFullTestSuiteTotalCoveredStatements();
        return (totalCovered * 100.0) / totalFunctionStatement;
    }

    private static double calculateRequiredCoverage(Coverage coverage) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        int totalFunctionCoverage = (int) Class.forName(fullyClonedClassName).getField(getTotalFunctionCoverageVariableName((MethodDeclaration) testFunc, coverage)).get(null);
        int totalCovered = 0;
        if(coverage == Coverage.STATEMENT) {
            totalCovered = MarkedPath.getTotalCoveredStatement();
        } else if(coverage == Coverage.BRANCH) {
            totalCovered = MarkedPath.getTotalCoveredBranch();
            System.out.println(totalCovered);
        }
        return (totalCovered * 100.0) / totalFunctionCoverage;
    }

    private static double calculateFunctionCoverage() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        int totalFunctionStatement = (int) Class.forName(fullyClonedClassName).getField(getTotalFunctionCoverageVariableName((MethodDeclaration) testFunc, Coverage.STATEMENT)).get(null);
        int totalCoveredStatement = MarkedPath.getTotalCoveredStatement();
        return (totalCoveredStatement * 100.0) / (totalFunctionStatement * 1.0);
    }

    private static double calculateSourceCodeCoverage() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        int totalClassStatement = (int) Class.forName(fullyClonedClassName).getField(getTotalClassCoverageVariableName()).get(null);
        int totalCoveredStatement = MarkedPath.getTotalCoveredStatement();
        return (totalCoveredStatement * 100.0) / (totalClassStatement * 1.0);
    }

    private static void setUpTestFunc(String methodName) {
        report.append("Duyệt danh sách ASTNode để tìm hàm cần kiểm thử\n");
        for (ASTNode func : funcAstNodeList) {
            if (((MethodDeclaration) func).getName().getIdentifier().equals(methodName)) {
                testFunc = func;
            }
        }
        report.append("CONCOLIC TEST REPORT\n");
        report.append("Hàm thực hiện:\n").append(testFunc).append("\n");
    }

    private static void setupParameters(String methodName) throws ClassNotFoundException, NoSuchMethodException {
        parameters = ((MethodDeclaration) testFunc).parameters();
        parameterClasses = getParameterClasses(parameters);
        parameterNames = getParameterNames(parameters);
        method = Class.forName(fullyClonedClassName).getDeclaredMethod(methodName, parameterClasses);
    }

    private static void setUpParametersV2(String methodName) throws ClassNotFoundException, NoSuchMethodException {
        parameters = ((MethodDeclaration) testFunc).parameters();
        parameterClasses = getParameterClasses(parameters);
        parameterNames = getParameterNames(parameters);
        createCloneMethod((MethodDeclaration) testFunc, compilationUnit);
        method = Class.forName("data.CloneFile").getDeclaredMethod(methodName, parameterClasses);
    }

    private static void setupFullyClonedClassName(String className) {
        className = className.replace(".java", "");
        simpleClassName = className;
        String packetName = "";
        if (compilationUnit.getPackage() != null) {
            packetName = compilationUnit.getPackage().getName() + ".";
        }
        fullyClonedClassName = "data.clonedProject." + packetName + className;
    }

    private static void setupCfgTree(Coverage coverage) {
        Block functionBlock = Utils.getFunctionBlock(testFunc);

        cfgBeginNode = new CfgNode();
        cfgBeginNode.setIsBeginCfgNode(true);

        cfgEndNode = new CfgEndBlockNode();
        cfgEndNode.setIsEndCfgNode(true);

        CfgNode block = new CfgBlockNode();
        block.setAst(functionBlock);

        int firstLine = compilationUnit.getLineNumber(functionBlock.getStartPosition());
        block.setLineNumber(1);

        block.setBeforeStatementNode(cfgBeginNode);
        block.setAfterStatementNode(cfgEndNode);

        ASTHelper.generateCFG(block, compilationUnit, firstLine, setupCoverage(coverage));
    }

    private static ASTHelper.Coverage setupCoverage(Coverage coverage) {
        switch (coverage) {
            case STATEMENT: return ASTHelper.Coverage.STATEMENT;
            case BRANCH: return ASTHelper.Coverage.BRANCH;
            case PATH: return ASTHelper.Coverage.PATH;
            case MCDC: return ASTHelper.Coverage.MCDC;
            default: throw new RuntimeException("Invalid coverage");
        }
    }

    private static String getTotalFunctionCoverageVariableName(MethodDeclaration methodDeclaration, Coverage coverage) {
        StringBuilder result = new StringBuilder();
        result.append(methodDeclaration.getReturnType2());
        result.append(methodDeclaration.getName());
        for (int i = 0; i < methodDeclaration.parameters().size(); i++) {
            result.append(methodDeclaration.parameters().get(i));
        }
        if(coverage == Coverage.STATEMENT) {
            result.append("TotalStatement");
        } else if (coverage == Coverage.BRANCH){
            result.append("TotalBranch");
        } else {
            throw new RuntimeException("Invalid Coverage");
        }

        return result.toString().replace(" ", "").replace(".", "");
    }

    private static String getTotalClassCoverageVariableName() {
        StringBuilder result = new StringBuilder();
        result.append(simpleClassName).append("TotalStatement");
        return result.toString().replace(" ", "").replace(".", "");
    }
}
