package core.testGeneration;

import core.path.*;
import core.testDriver.TestDriverUtils;
import core.testResult.coveredStatement.CoveredStatement;
import core.testResult.result.autoTestResult.TestData;
import core.testResult.result.autoTestResult.TestResult;
import core.FilePath;
import core.symbolicExecution.SymbolicExecution;
import core.cfg.CfgBlockNode;
import core.cfg.CfgEndBlockNode;
import core.cfg.CfgNode;
import core.parser.ASTHelper;
import core.parser.ProjectParser;
import core.utils.Utils;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class NTDTesting extends TestGeneration {
    private static String simpleClassName;
    private static String fullyClonedClassName;
    private static Method method;
    private NTDTesting() {
    }

    public static TestResult runFullNTD(String path, String methodName, String className, Coverage coverage) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException {

        setup(path, className, methodName);
        setupCfgTree(coverage);
        setupParameters(methodName);
        isSetup = true;

//        test();

        TestResult result = startGenerating(coverage);

        isSetup = false;

        return result;
    }

//    private static void test() {
//        List<Path> paths = (new FindAllPath(cfgBeginNode)).getPaths();
//
//        SymbolicExecution solution = new SymbolicExecution(paths.get(0), parameters);
//    }

    private static TestResult startGenerating(Coverage coverage) throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException {
        TestResult testResult = new TestResult();
        Object[] evaluatedValues = SymbolicExecution.createRandomTestData(parameterClasses);

        writeDataToFile("", FilePath.concreteExecuteResultPath, false);

        long startRunTestTime = System.nanoTime();
        Object output = method.invoke(parameterClasses, evaluatedValues);
        long endRunTestTime = System.nanoTime();
        double runTestDuration = (endRunTestTime - startRunTestTime) / 1000000.0;

        List<MarkedStatement> markedStatements = getMarkedStatement();
        MarkedPath.markPathToCFGV2(cfgBeginNode, markedStatements);

        List<CoveredStatement> coveredStatements = CoveredStatement.switchToCoveredStatementList(markedStatements);

        testResult.addToFullTestData(new TestData(parameterNames, parameterClasses, evaluatedValues, coveredStatements,
                output, runTestDuration, calculateRequiredCoverage(coverage), calculateFunctionCoverage(), calculateSourceCodeCoverage()));

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

            startRunTestTime = System.nanoTime();
            output = method.invoke(parameterClasses, evaluatedValues);
            endRunTestTime = System.nanoTime();
            runTestDuration = (endRunTestTime - startRunTestTime) / 1000000.0;

            markedStatements = getMarkedStatement();
            MarkedPath.markPathToCFGV2(cfgBeginNode, markedStatements);

            coveredStatements = CoveredStatement.switchToCoveredStatementList(markedStatements);

            testResult.addToFullTestData(new TestData(parameterNames, parameterClasses, evaluatedValues, coveredStatements, output, runTestDuration, calculateRequiredCoverage(coverage), calculateFunctionCoverage(), calculateSourceCodeCoverage()));

            uncoveredNode = findUncoverNode(cfgBeginNode, coverage);
            System.out.println("Uncovered Node: " + uncoveredNode);
        }

        if (isTestedSuccessfully) System.out.println("Tested successfully with 100% coverage");
        else System.out.println("Test fail due to UNSATISFIABLE constraint");

        testResult.setFullCoverage(calculateFullTestSuiteCoverage());

        return testResult;
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

    private static void writeDataToFile(String data, String path, boolean append) {
        try {
            FileWriter writer = new FileWriter(path, append);
            writer.write(data);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getDataFromFile(String path) {
        StringBuilder result = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            if ((line = br.readLine()) != null) {
                result.append(line);
            }
            while ((line = br.readLine()) != null) {
                result.append("\n").append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    private static CfgNode findUncoverNode(CfgNode cfgNode, Coverage coverage) {
        switch (coverage) {
            case STATEMENT:
                return MarkedPath.findUncoveredStatement(cfgNode);
            case BRANCH:
            case MCDC:
                return MarkedPath.findUncoveredBranch(cfgNode);
            default:
                throw new RuntimeException("Invalid coverage type");
        }
    }

    private static void setup(String path, String className, String methodName) throws IOException {
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
        if (coverage == Coverage.STATEMENT) {
            totalCovered = MarkedPath.getTotalCoveredStatement();
        } else if (coverage == Coverage.BRANCH || coverage == Coverage.MCDC) {
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
        for (ASTNode func : funcAstNodeList) {
            if (((MethodDeclaration) func).getName().getIdentifier().equals(methodName)) {
                testFunc = func;
            }
        }
    }

    private static void setupParameters(String methodName) throws ClassNotFoundException, NoSuchMethodException {
        parameters = ((MethodDeclaration) testFunc).parameters();
        parameterClasses = TestDriverUtils.getParameterClasses(parameters);
        parameterNames = TestDriverUtils.getParameterNames(parameters);
        method = Class.forName(fullyClonedClassName).getDeclaredMethod(methodName, parameterClasses);
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

        ASTHelper.generateCFG(block, compilationUnit, firstLine, getCoverageType(coverage));
    }

    private static ASTHelper.Coverage getCoverageType(Coverage coverage) {
        switch (coverage) {
            case STATEMENT:
                return ASTHelper.Coverage.STATEMENT;
            case BRANCH:
                return ASTHelper.Coverage.BRANCH;
            case MCDC:
                return ASTHelper.Coverage.MCDC;
            default:
                throw new RuntimeException("Invalid coverage");
        }
    }

    private static String getTotalFunctionCoverageVariableName(MethodDeclaration methodDeclaration, Coverage coverage) {
        StringBuilder result = new StringBuilder();
        result.append(methodDeclaration.getReturnType2());
        result.append(methodDeclaration.getName());
        for (int i = 0; i < methodDeclaration.parameters().size(); i++) {
            result.append(methodDeclaration.parameters().get(i));
        }
        if (coverage == Coverage.STATEMENT) {
            result.append("TotalStatement");
        } else if (coverage == Coverage.BRANCH || coverage == Coverage.MCDC) {
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

    private static String getTotalClassCoverageVariableName() {
        StringBuilder result = new StringBuilder();
        result.append(simpleClassName).append("TotalStatement");
        return result.toString().replace(" ", "").replace(".", "");
    }
}
