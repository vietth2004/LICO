package com.example.unittesting.utils.testing;

import core.testDriver.TestDriverUtils;
import core.testResult.coveredStatement.CoveredStatement;
import core.testResult.result.autoTestResult.TestData;
import core.testResult.result.autoTestResult.TestResult;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
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
import core.utils.Utils;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@JsonAutoDetect
@Component
public class ConcolicTesting {
    public enum Coverage {
        STATEMENT,
        BRANCH,
        MCDC,
        PATH
    }

    private static CompilationUnit compilationUnit;
    private static ArrayList<ASTNode> funcAstNodeList;
    private static CfgNode cfgBeginNode;
    private static CfgEndBlockNode cfgEndNode;
    private static List<ASTNode> parameters;
    private static Class<?>[] parameterClasses;
    private static List<String> parameterNames;
    private static ASTNode testFunc;
    private static String classKey;

    private ConcolicTesting() {
    }

    public static TestResult runFullConcolic(String path, String methodName, String className, Coverage coverage) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException, InterruptedException {

        setup(path, className, methodName);
        setupCfgTree(coverage);
        setupParameters();

        TestResult result = startGenerating(coverage);

        return result;
    }

    private static TestResult startGenerating(Coverage coverage) throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException, IOException, InterruptedException {
        TestResult testResult = new TestResult();
        Object[] evaluatedValues = SymbolicExecution.createRandomTestData(parameterClasses);

        writeDataToFile("", FilePath.concreteExecuteResultPath, false);

        String testDriver = TestDriverGenerator.generateTestDriver((MethodDeclaration) testFunc, evaluatedValues, getCoverageType(coverage));
        List<MarkedStatement> markedStatements = TestDriverRunner.runTestDriver(testDriver);

        MarkedPath.markPathToCFGV2(cfgBeginNode, markedStatements);

        List<CoveredStatement> coveredStatements = CoveredStatement.switchToCoveredStatementList(markedStatements);

        testResult.addToFullTestData(new TestData(parameterNames, parameterClasses, evaluatedValues, coveredStatements,
                TestDriverRunner.getOutput(), TestDriverRunner.getRuntime(), calculateRequiredCoverage(coverage), calculateFunctionCoverage(), calculateSourceCodeCoverage()));

        boolean isTestedSuccessfully = true;

        for (CfgNode uncoveredNode = findUncoverNode(cfgBeginNode, coverage); uncoveredNode != null; ) {

            Path newPath = (new FindPath(cfgBeginNode, uncoveredNode, cfgEndNode)).getPath();

            SymbolicExecution solution = new SymbolicExecution(newPath, parameters);

            if (solution.getModel() == null) {
                isTestedSuccessfully = false;
                break;
            }

            evaluatedValues = SymbolicExecution.getEvaluatedTestData(parameterClasses);

            writeDataToFile("", FilePath.concreteExecuteResultPath, false);

            testDriver = TestDriverGenerator.generateTestDriver((MethodDeclaration) testFunc, evaluatedValues, getCoverageType(coverage));
            markedStatements = TestDriverRunner.runTestDriver(testDriver);

            MarkedPath.markPathToCFGV2(cfgBeginNode, markedStatements);
            coveredStatements = CoveredStatement.switchToCoveredStatementList(markedStatements);

            testResult.addToFullTestData(new TestData(parameterNames, parameterClasses, evaluatedValues, coveredStatements, TestDriverRunner.getOutput(), TestDriverRunner.getRuntime(), calculateRequiredCoverage(coverage), calculateFunctionCoverage(), calculateSourceCodeCoverage()));

            uncoveredNode = findUncoverNode(cfgBeginNode, coverage);
            System.out.println("Uncovered Node: " + uncoveredNode);
        }

        if (isTestedSuccessfully) System.out.println("Tested successfully with 100% coverage");
        else System.out.println("Test fail due to UNSATISFIABLE constraint");

        testResult.setFullCoverage(calculateFullTestSuiteCoverage());

        return testResult;
    }

    private static void writeDataToFile(String data, String path, boolean append) {
        try {
            FileWriter writer = new FileWriter(path, append);
            writer.write(data);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        funcAstNodeList = ProjectParser.parseFile(path);
        compilationUnit = ProjectParser.parseFileToCompilationUnit(path);
        classKey = (compilationUnit.getPackage() != null ? compilationUnit.getPackage().getName().toString() : "") + className.replace(".java", "") + "totalStatement";
        setUpTestFunc(methodName);
        MarkedPath.resetFullTestSuiteCoveredStatements();
    }

    private static double calculateFullTestSuiteCoverage() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
//        String key = getTotalFunctionCoverageVariableName((MethodDeclaration) testFunc, Coverage.STATEMENT);
//        int totalFunctionStatement = ConcolicUploadUtil.totalStatementsInUnits.get(key);
//        int totalCovered = MarkedPath.getFullTestSuiteTotalCoveredStatements();
//        return (totalCovered * 100.0) / totalFunctionStatement;

        return 0;
    }

    private static double calculateRequiredCoverage(Coverage coverage) {
//        String key = getTotalFunctionCoverageVariableName((MethodDeclaration) testFunc, coverage);
//        int totalFunctionCoverage = 1;
//        int totalCovered = 0;
//        if (coverage == Coverage.STATEMENT) {
//            totalCovered = MarkedPath.getTotalCoveredStatement();
//            totalFunctionCoverage = ConcolicUploadUtil.totalStatementsInUnits.get(key);
//        } else if (coverage == Coverage.BRANCH) {
//            totalCovered = MarkedPath.getTotalCoveredBranch();
//            totalFunctionCoverage = ConcolicUploadUtil.totalBranchesInUnits.get(key);
//        }
//        return (totalCovered * 100.0) / totalFunctionCoverage;

        return 0;
    }

    private static double calculateFunctionCoverage() {
//        String key = getTotalFunctionCoverageVariableName((MethodDeclaration) testFunc, Coverage.STATEMENT);
//        int totalFunctionStatement = ConcolicUploadUtil.totalStatementsInUnits.get(key);
//        int totalCoveredStatement = MarkedPath.getTotalCoveredStatement();
//        return (totalCoveredStatement * 100.0) / (totalFunctionStatement * 1.0);
        return 0;
    }

    private static double calculateSourceCodeCoverage() {
//        int totalClassStatement = ConcolicUploadUtil.totalStatementsInJavaFile.get(classKey);
//        int totalCoveredStatement = MarkedPath.getTotalCoveredStatement();
//        return (totalCoveredStatement * 100.0) / (totalClassStatement * 1.0);
        return 0;
    }

    private static void setUpTestFunc(String methodName) {
        for (ASTNode func : funcAstNodeList) {
            if (((MethodDeclaration) func).getName().getIdentifier().equals(methodName)) {
                testFunc = func;
            }
        }
    }

    private static void setupParameters() {
        parameters = ((MethodDeclaration) testFunc).parameters();
        parameterClasses = TestDriverUtils.getParameterClasses(parameters);
        parameterNames = TestDriverUtils.getParameterNames(parameters);
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

        ASTHelper.generateCFG(block, compilationUnit, firstLine, getCoverageType(coverage));
    }

    private static ASTHelper.Coverage getCoverageType(Coverage coverage) {
        switch (coverage) {
            case STATEMENT:
                return ASTHelper.Coverage.STATEMENT;
            case BRANCH:
                return ASTHelper.Coverage.BRANCH;
            default:
                throw new RuntimeException("Invalid coverage");
        }
    }

    private static String getTotalFunctionCoverageVariableName(MethodDeclaration methodDeclaration, Coverage coverage) {
        StringBuilder result = new StringBuilder(classKey);
        result.append(methodDeclaration.getReturnType2());
        result.append(methodDeclaration.getName());
        for (int i = 0; i < methodDeclaration.parameters().size(); i++) {
            result.append(methodDeclaration.parameters().get(i));
        }
        if (coverage == Coverage.STATEMENT) {
            result.append("TotalStatement");
        } else if (coverage == Coverage.BRANCH) {
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

