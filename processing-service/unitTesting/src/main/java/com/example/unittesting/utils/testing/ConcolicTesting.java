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
import core.dataStructure.MarkedPathV2;
import core.dataStructure.MarkedStatement;
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
    private static ConcolicTestResult testResult;
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

        ConcolicTestResult testResult = testingWithStatementAndBranchCoverage(coverage);

        return testResult;
    }

    private static void testingWithPathCoverage(Class<?>[] parameterClasses) throws InvocationTargetException, IllegalAccessException {
        List<Path> paths = (new FindAllPath(cfgBeginNode)).getPaths();
        for (int i = 0; i < paths.size(); i++) {

            SymbolicExecution execution = new SymbolicExecution(paths.get(i), parameters);

            method.invoke(parameterClasses, getParameterValue(parameterClasses));
            if (!MarkedPathV2.check(paths.get(i))) {
                System.out.println("Path is not covered");
            }
        }
    }

    private static ConcolicTestResult testingWithStatementAndBranchCoverage(Coverage coverage) throws InvocationTargetException, IllegalAccessException {
        Object[] evaluatedValues = createRandomTestData(parameterClasses);
        Object output = method.invoke(parameterClasses, evaluatedValues);
        List<CoveredStatement> pathStatements = CoveredStatement.switchToCoveredStatementList(MarkedPath.markPathToCFG(cfgBeginNode));
        report.append("STEP 3: Sinh dữ liệu ngẫu nhiên cho các parameter ").append(Arrays.toString(parameterClasses)).append(": ");
        report.append(Arrays.toString(evaluatedValues)).append("\n");
        report.append("STEP 4: Chạy dữ liệu ngẫu nhiên đấy, lưu những câu lệnh đã được chạy qua: ").append(pathStatements).append("\n");

        testResult.addToFullTestData(new ConcolicTestData(parameterNames, parameterClasses, evaluatedValues, pathStatements, output));

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

            output = method.invoke(parameterClasses, evaluatedValues);
            pathStatements = CoveredStatement.switchToCoveredStatementList(MarkedPath.markPathToCFG(cfgBeginNode));

            report.append("STEP ").append(i++).append(": Đánh dấu những câu lệnh (node) đã chạy qua sau khi thực hiện chạy hàm với dữ liệu vừa được sinh: ");
            report.append(pathStatements).append("\n");

            testResult.addToFullTestData(new ConcolicTestData(parameterNames, parameterClasses, evaluatedValues, pathStatements, output));

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
                return MarkedPath.findUncoverStatement(cfgNode, null);
            case BRANCH:
                return MarkedPath.findUncoveredBranch(cfgNode, null);
            default:
                throw new RuntimeException("Invalid coverage type");
        }
    }

    private static void setup(String path, String className, String methodName) throws IOException {
        testResult = new ConcolicTestResult();
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
