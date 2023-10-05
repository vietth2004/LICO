package com.example.unittesting.utils.testing;

import com.example.unittesting.model.result.Concolic.ConcolicTestData;
import com.example.unittesting.model.result.Concolic.ConcolicTestResult;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
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
    private ConcolicTesting(){}

    public static ConcolicTestResult runFullConcolic(String path, String methodName, String className) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        System.out.println(path);
        ConcolicTestResult testResult = new ConcolicTestResult();
        StringBuilder report = new StringBuilder();

        // Parse File
        ArrayList<ASTNode> funcAstNodeList = ProjectParser.parseFile(path);
        CompilationUnit compilationUnit = ProjectParser.parseFileToCompilationUnit(path);
        report.append("Parse toàn bộ các hàm trong class cần kiểm thử thành 1 danh sách ASTNode\n");

        report.append("Duyệt danh sách ASTNode để tìm hàm cần kiểm thử\n");
        for (ASTNode func : funcAstNodeList) {
            if (((MethodDeclaration) func).getName().getIdentifier().equals(methodName)) {
                report.append("CONCOLIC TEST REPORT\n");
                report.append("Hàm thực hiện:\n").append(func).append("\n");

                // Clone a runnable java file
                createCloneMethod((MethodDeclaration) func, compilationUnit);
                report.append("STEP 1: Clone hàm cần kiểm thử vào một file java có thể chạy được: core-engine\\cfg\\src\\main\\java\\data\\CloneFile.java\n");
                // =========================

                // Generate CFG
                Block functionBlock = Utils.getFunctionBlock(func);

                CfgNode cfgBeginCfgNode = new CfgNode();
                cfgBeginCfgNode.setIsBeginCfgNode(true);

                CfgEndBlockNode cfgEndCfgNode = new CfgEndBlockNode();
                cfgEndCfgNode.setIsEndCfgNode(true);

                CfgNode block = new CfgBlockNode();
                block.setAst(functionBlock);

                block.setBeforeStatementNode(cfgBeginCfgNode);
                block.setAfterStatementNode(cfgEndCfgNode);

                ASTHelper.generateCFG(block);
                CfgNode cfgNode = cfgBeginCfgNode;
                report.append("STEP 2: Sinh cây CFG dựa trên hàm được truyền vào\n");
                //===========================

                //=======================FULL CONCOLIC VERSION 1===========================
                List<ASTNode> parameters = ((MethodDeclaration) func).parameters();
                Class<?>[] parameterClasses = getParameterClasses(parameters);
                List<String> parameterNames = getParameterNames(parameters);
                Method method = Class.forName("data.CloneFile").getDeclaredMethod(methodName, parameterClasses);

                Object[] evaluatedValues = createRandomTestData(parameterClasses);
                method.invoke(parameterClasses, evaluatedValues);
                List<String> pathStatements = MarkedPath.markPathToCFG(cfgNode);
                report.append("STEP 3: Sinh dữ liệu ngẫu nhiên cho các parameter ").append(Arrays.toString(parameterClasses)).append(": ");
                report.append(Arrays.toString(evaluatedValues)).append("\n");
                report.append("STEP 4: Chạy dữ liệu ngẫu nhiên đấy, lưu những câu lệnh đã được chạy qua: ").append(pathStatements).append("\n");

                testResult.addToFullTestData(new ConcolicTestData(parameterNames, parameterClasses, evaluatedValues, pathStatements));

                boolean isTestedSuccessfully = true;
                int i = 5;

                for (CfgNode uncoveredNode = MarkedPath.findUncoveredNode(cfgNode, null); uncoveredNode != null; ) {
                    report.append("STEP ").append(i++).append(": Tìm node chưa được phủ: ").append(uncoveredNode).append("\n");

                    Path newPath = (new FindPath(cfgNode, uncoveredNode, cfgEndCfgNode)).getPath();
                    report.append("STEP ").append(i++).append(": Sinh đường thi hành từ node chưa được phủ đấy\n");

                    SymbolicExecution solution = new SymbolicExecution(newPath, parameters);


                    if(solution.getModel() == null) {
                        isTestedSuccessfully = false;
                        break;
                    }

                    evaluatedValues = getParameterValue(parameterClasses);
                    report.append("STEP ").append(i++).append(": Thực thi tượng trưng đường thi hành và sinh test data tương ứng: ");
                    report.append(Arrays.toString(evaluatedValues)).append("\n");

                    method.invoke(parameterClasses, evaluatedValues);
                    pathStatements = MarkedPath.markPathToCFG(cfgNode);

                    report.append("STEP ").append(i++).append(": Đánh dấu những câu lệnh (node) đã chạy qua sau khi thực hiện chạy hàm với dữ liệu vừa được sinh: ");
                    report.append(pathStatements).append("\n");

                    testResult.addToFullTestData(new ConcolicTestData(parameterNames, parameterClasses, evaluatedValues, pathStatements));

                    uncoveredNode = MarkedPath.findUncoveredNode(cfgNode, null);
                    System.out.println("Uncovered Node: " + uncoveredNode);
                }

                report.append("STEP ").append(i).append("Kết thúc việc kiểm thử");

                writeDataToFile(report.toString(), "core-engine/cfg/src/main/java/data/report.txt");

                if(isTestedSuccessfully) System.out.println("Tested successfully with 100% coverage");
                else System.out.println("Test fail due to UNSATISFIABLE constraint");
//                //========================================

                break;
            }
        }
        return testResult;
    }
}
