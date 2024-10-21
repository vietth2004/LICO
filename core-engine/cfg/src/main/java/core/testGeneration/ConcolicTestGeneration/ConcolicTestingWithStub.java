package core.testGeneration.ConcolicTestGeneration;

import core.Z3Vars.Z3VariableWrapper;
import core.testDriver.TestDriverUtils;
import core.testGeneration.TestGeneration;
import core.testResult.coveredStatement.CoveredStatement;
import core.testResult.result.autoTestResult.TestData;
import core.testResult.result.autoTestResult.TestResult;
import core.FilePath;
import core.path.FindPath;
import core.symbolicExecution.SymbolicExecution;
import core.cfg.CfgBlockNode;
import core.cfg.CfgEndBlockNode;
import core.cfg.CfgNode;
import core.path.MarkedPath;
import core.path.MarkedStatement;
import core.path.Path;
import core.parser.ASTHelper;
import core.parser.ProjectParser;
import core.testDriver.TestDriverGenerator;
import core.testDriver.TestDriverRunner;
import core.uploadProjectUtils.ConcolicUploadUtil;
import core.utils.Utils;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class ConcolicTestingWithStub extends ConcolicTestGeneration {
    private static String simpleClassName;
    private static String fullyClonedClassName;
    private static Method method;

    private ConcolicTestingWithStub() {
    }

    public static TestResult runFullConcolic(String path, String methodName, String className, TestGeneration.Coverage coverage) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException, InterruptedException {
        setup(path, className, methodName);
        setupCfgTree(coverage);
        setupParameters(methodName);
        TestGeneration.isSetup = true;

        TestResult result = startGenerating(coverage);

        TestGeneration.isSetup = false;

        return result;
    }

    private static TestResult startGenerating(TestGeneration.Coverage coverage) throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException, IOException, InterruptedException {
        TestResult testResult = new TestResult();
        Object[] evaluatedValues = SymbolicExecution.createRandomTestData(TestGeneration.parameterClasses);

        TestGeneration.writeDataToFile("", FilePath.concreteExecuteResultPath, false);

        long startRunTestTime = System.nanoTime();
        Object output = method.invoke(TestGeneration.parameterClasses, evaluatedValues);
        long endRunTestTime = System.nanoTime();
        double runTestDuration = (endRunTestTime - startRunTestTime) / 1000000.0;

        List<MarkedStatement> markedStatements = getMarkedStatement();
        MarkedPath.markPathToCFGV2(TestGeneration.cfgBeginNode, markedStatements);

        List<CoveredStatement> coveredStatements = CoveredStatement.switchToCoveredStatementList(markedStatements);

        testResult.addToFullTestData(new TestData(TestGeneration.parameterNames, TestGeneration.parameterClasses, evaluatedValues, coveredStatements,
                output, runTestDuration, calculateRequiredCoverage(coverage), calculateFunctionCoverage(), calculateSourceCodeCoverage()));

        boolean isTestedSuccessfully = true;

        for (CfgNode uncoveredNode = TestGeneration.findUncoverNode(TestGeneration.cfgBeginNode, coverage); uncoveredNode != null; ) {

            Path newPath = (new FindPath(TestGeneration.cfgBeginNode, uncoveredNode, TestGeneration.cfgEndNode)).getPath();

            SymbolicExecution solution = new SymbolicExecution(newPath, TestGeneration.parameters);
            List<Z3VariableWrapper> Z3Vars = solution.execute();

            if (solution.getModel() == null) {
                isTestedSuccessfully = false;
                break;
            }

            evaluatedValues = SymbolicExecution.getEvaluatedTestData(TestGeneration.parameterClasses);

            TestGeneration.writeDataToFile("", FilePath.concreteExecuteResultPath, false);

            String testDriver = TestDriverGenerator.generateTestDriver((MethodDeclaration) TestGeneration.testFunc, evaluatedValues, TestGeneration.getCoverageType(coverage));
            markedStatements = TestDriverRunner.runTestDriver(testDriver);

            MarkedPath.markPathToCFGV2(TestGeneration.cfgBeginNode, markedStatements);
            coveredStatements = CoveredStatement.switchToCoveredStatementList(markedStatements);

            testResult.addToFullTestData(new TestData(TestGeneration.parameterNames, TestGeneration.parameterClasses, evaluatedValues, coveredStatements, TestDriverRunner.getOutput(), TestDriverRunner.getRuntime(), calculateRequiredCoverage(coverage), calculateFunctionCoverage(), calculateSourceCodeCoverage()));

            uncoveredNode = TestGeneration.findUncoverNode(TestGeneration.cfgBeginNode, coverage);
            System.out.println("Uncovered Node: " + uncoveredNode);
        }

        if (isTestedSuccessfully) System.out.println("Tested successfully with 100% coverage");
        else System.out.println("Test fail due to UNSATISFIABLE constraint");

        testResult.setFullCoverage(calculateFullTestSuiteCoverage());

        return testResult;
    }

    private static void setup(String path, String className, String methodName) throws IOException {
        TestGeneration.funcAstNodeList = ProjectParser.parseFile(path);
        TestGeneration.compilationUnit = ProjectParser.parseFileToCompilationUnit(path);
        classKey = (TestGeneration.compilationUnit.getPackage() != null ? TestGeneration.compilationUnit.getPackage().getName().toString() : "") + className.replace(".java", "") + "totalStatement";
        setupFullyClonedClassName(className);
        setUpTestFunc(methodName);
        MarkedPath.resetFullTestSuiteCoveredStatements();
    }

    private static void setupFullyClonedClassName(String className) {
        className = className.replace(".java", "");
        simpleClassName = className;
        String packetName = "";
        if (TestGeneration.compilationUnit.getPackage() != null) {
            packetName = TestGeneration.compilationUnit.getPackage().getName() + ".";
        }
        fullyClonedClassName = "data.clonedProject." + packetName + className;
    }

    private static double calculateFullTestSuiteCoverage() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
//        String key = getTotalFunctionCoverageVariableName((MethodDeclaration) TestGeneration.testFunc, TestGeneration.Coverage.STATEMENT);
//        int totalFunctionStatement = ConcolicUploadUtil.totalStatementsInUnits.get(key);
//        int totalCovered = MarkedPath.getFullTestSuiteTotalCoveredStatements();
//        return (totalCovered * 100.0) / totalFunctionStatement;
        return 100;
    }

    private static double calculateRequiredCoverage(TestGeneration.Coverage coverage) {
//        String key = getTotalFunctionCoverageVariableName((MethodDeclaration) TestGeneration.testFunc, coverage);
//        int totalFunctionCoverage = 1;
//        int totalCovered = 0;
//        if (coverage == TestGeneration.Coverage.STATEMENT) {
//            totalCovered = MarkedPath.getTotalCoveredStatement();
//            totalFunctionCoverage = ConcolicUploadUtil.totalStatementsInUnits.get(key);
//        } else if (coverage == TestGeneration.Coverage.BRANCH) {
//            totalCovered = MarkedPath.getTotalCoveredBranch();
//            Map<String, Integer> hashMap = ConcolicUploadUtil.totalBranchesInUnits;
//            totalFunctionCoverage = hashMap.get(key);
//        }
//        return (totalCovered * 100.0) / totalFunctionCoverage;

        return 50;
    }

    private static double calculateFunctionCoverage() {
//        String key = getTotalFunctionCoverageVariableName((MethodDeclaration) TestGeneration.testFunc, TestGeneration.Coverage.STATEMENT);
//        int totalFunctionStatement = ConcolicUploadUtil.totalStatementsInUnits.get(key);
//        int totalCoveredStatement = MarkedPath.getTotalCoveredStatement();
//        return (totalCoveredStatement * 100.0) / (totalFunctionStatement * 1.0);
        return 40;
    }

    private static double calculateSourceCodeCoverage() {
//        int totalClassStatement = ConcolicUploadUtil.totalStatementsInJavaFile.get(classKey);
//        int totalCoveredStatement = MarkedPath.getTotalCoveredStatement();
//        return (totalCoveredStatement * 100.0) / (totalClassStatement * 1.0);
        return 8;
    }

    private static void setUpTestFunc(String methodName) {
        for (ASTNode func : TestGeneration.funcAstNodeList) {
            if (((MethodDeclaration) func).getName().getIdentifier().equals(methodName)) {
                TestGeneration.testFunc = func;
            }
        }
    }

    private static void setupParameters(String methodName) throws ClassNotFoundException, NoSuchMethodException {
        TestGeneration.parameters = ((MethodDeclaration) TestGeneration.testFunc).parameters();
        TestGeneration.parameterClasses = TestDriverUtils.getParameterClasses(TestGeneration.parameters);
        TestGeneration.parameterNames = TestDriverUtils.getParameterNames(TestGeneration.parameters);
        method = Class.forName(fullyClonedClassName).getDeclaredMethod(methodName + "_clone", TestGeneration.parameterClasses);
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

    private static List<MarkedStatement> getMarkedStatement() {
        List<MarkedStatement> result = new ArrayList<>();

        String markedData = getDataFromFile(FilePath.concreteExecuteResultPath);
        String[] markedStatements = markedData.split("---end---");
        for (int i = 0; i < markedStatements.length; i++) {
            String[] markedStatementData = markedStatements[i].split("===");
            String statement = markedStatementData[0];
            boolean isTrueConditionalStatement = Boolean.parseBoolean(markedStatementData[1]);
            boolean isFalseConditionalStatement = Boolean.parseBoolean(markedStatementData[2]);
            result.add(new MarkedStatement(statement, isTrueConditionalStatement, isFalseConditionalStatement));
        }
        return result;
    }
}

