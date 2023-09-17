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
import java.util.List;

import static core.testDriver.Utils.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

@JsonAutoDetect
@Component
public class ConcolicTesting {
    private ConcolicTesting(){}

    public static ConcolicTestResult runFullConcolic(String path, String methodName, String className) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        ConcolicTestResult testResult = new ConcolicTestResult();

        // Parse File
        ArrayList<ASTNode> funcAstNodeList = ProjectParser.parseFile(path);
        CompilationUnit compilationUnit = ProjectParser.parseFileToCompilationUnit(path);

        for (ASTNode func : funcAstNodeList) {
            if (((MethodDeclaration) func).getName().getIdentifier().equals(methodName)) {

                // Clone a runnable java file
                createCloneMethod((MethodDeclaration) func, compilationUnit);

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
                //===========================

                //=======================FULL CONCOLIC VERSION 1===========================
                List<ASTNode> parameters = ((MethodDeclaration) func).parameters();
                Class<?>[] parameterClasses = getParameterClasses(parameters);
                List<String> parameterNames = getParameterNames(parameters);
                Method method = Class.forName("data.CloneFile").getDeclaredMethod(methodName, parameterClasses);

                Object[] evaluatedValues = createRandomTestData(parameterClasses);
                method.invoke(parameterClasses, evaluatedValues);
                List<String> pathStatements = MarkedPath.markPathToCFG(cfgNode);

                testResult.addToFullTestData(new ConcolicTestData(parameterNames, parameterClasses, evaluatedValues, pathStatements));

                boolean isTestedSuccessfully = true;

                for (CfgNode uncoveredNode = MarkedPath.findUncoveredNode(cfgNode, null); uncoveredNode != null; ) {

                    Path newPath = (new FindPath(cfgNode, uncoveredNode, cfgEndCfgNode)).getPath();

                    SymbolicExecution solution = new SymbolicExecution(newPath, parameters);

                    if(solution.getModel() == null) {
                        isTestedSuccessfully = false;
                        break;
                    }

                    evaluatedValues = getParameterValue(parameterClasses);
                    method.invoke(parameterClasses, evaluatedValues);
                    pathStatements = MarkedPath.markPathToCFG(cfgNode);

                    testResult.addToFullTestData(new ConcolicTestData(parameterNames, parameterClasses, evaluatedValues, pathStatements));

                    uncoveredNode = MarkedPath.findUncoveredNode(cfgNode, null);
                    System.out.println("Uncovered Node: " + uncoveredNode);
                }

                if(isTestedSuccessfully) System.out.println("Tested successfully with 100% coverage");
                else System.out.println("Test fail due to UNSATISFIABLE constraint");
//                //========================================

                break;
            }
        }
        return testResult;
    }

}
