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
import core.dataStructure.Path;
import core.parser.ASTHelper;
import core.parser.ProjectParser;
import core.utils.Utils;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static core.testDriver.Utils.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

@JsonAutoDetect
@Component
public class ConcolicTesting {

    public enum Coverage {
        STATEMENT,
        BRANCH,
        PATH
    }

    private static StringBuilder report;
    private static CompilationUnit compilationUnit;
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

    public static ConcolicTestResult runFullConcolic(String path, String methodName, String className, Coverage coverage) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {

        setup(path, className, methodName);
        setupCfgTree();
        setupParameters(methodName);

        if(coverage == Coverage.STATEMENT || coverage == Coverage.BRANCH) {
            return testingWithStatementAndBranchCoverage(coverage);
        } else {
            return testingWithPathCoverage();
        }
    }

    private static ConcolicTestResult testingWithPathCoverage() throws InvocationTargetException, IllegalAccessException {
        ConcolicTestResult testResult = new ConcolicTestResult();

        List<Path> paths = (new FindAllPath(cfgBeginNode)).getPaths();
        System.out.println(paths.size());

        for (int i = 0; i < paths.size(); i++) {
            new SymbolicExecution(paths.get(i), parameters);

            Object[] evaluatedValues = getParameterValue(parameterClasses);
            long startRunTestTime = System.nanoTime();
            Object output = method.invoke(parameterClasses, evaluatedValues);
            long endRunTestTime = System.nanoTime();
            double runTestDuration = (endRunTestTime - startRunTestTime) / 1000000.0;
            List<CoveredStatement> coveredStatements = CoveredStatement.switchToCoveredStatementList(MarkedPath.isPathActuallyCovered(paths.get(i)));

            testResult.addToFullTestData(new ConcolicTestData(parameterNames, parameterClasses, evaluatedValues, coveredStatements, output, runTestDuration));
        }

        return testResult;
    }

    private static ConcolicTestResult testingWithStatementAndBranchCoverage(Coverage coverage) throws InvocationTargetException, IllegalAccessException {
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

        testResult.addToFullTestData(new ConcolicTestData(parameterNames, parameterClasses, evaluatedValues, coveredStatements, output, runTestDuration));


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

            testResult.addToFullTestData(new ConcolicTestData(parameterNames, parameterClasses, evaluatedValues, coveredStatements, output, runTestDuration));

            uncoveredNode = findUncoverNode(cfgBeginNode, coverage);
            System.out.println("Uncovered Node: " + uncoveredNode);
        }

        report.append("STEP ").append(i).append("Kết thúc việc kiểm thử");

        writeDataToFile(report.toString(), "core-engine/cfg/src/main/java/data/report.txt");

        if (isTestedSuccessfully) System.out.println("Tested successfully with 100% coverage");
        else System.out.println("Test fail due to UNSATISFIABLE constraint");

        return testResult;
    }

    private static CfgNode findUncoverNode(CfgNode cfgNode, Coverage coverage) {
        switch (coverage) {
            case STATEMENT:
                return MarkedPath.findUncoveredStatement(cfgNode, null);
            case BRANCH:
                return MarkedPath.findUncoveredBranch(cfgNode, null);
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

    private static void setupFullyClonedClassName(String className) {
        className = className.replace(".java", "");
        String packetName = "";
        if (compilationUnit.getPackage() != null) {
            packetName = compilationUnit.getPackage().getName() + ".";
        }
        fullyClonedClassName = "data.clonedProject." + packetName + className;
    }

    private static void setupCfgTree() {
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

        ASTHelper.generateCFG(block, compilationUnit, firstLine);
    }

}
