package com.example.unittesting.util.start;

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
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static core.testDriver.Utils.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

@JsonAutoDetect
@Component
public class AppStart {
    //    private static final Logger LOGGER = LoggerFactory.getLogger(CppApi.class);
    private static long totalUsedMem = 0;
    private static long tickCount = 0;
    public AppStart(){

    }

    public StringBuilder runFullConcolic(String path, String methodName, String className) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, InterruptedException {
//        String path = "src\\main\\java\\data\\CFG4J_Test.java";
        StringBuilder result = new StringBuilder();
        System.out.println("Start parsing...\n");
        result.append("Start parsing...\n");
        ArrayList<ASTNode> funcAstNodeList = ProjectParser.parseFile(path);

        System.out.println("count = " + funcAstNodeList.size());
        result.append("count = " + funcAstNodeList.size() +"\n");
        int openingParenthesisIndex = className.indexOf(".");
        String name = className.substring(0, openingParenthesisIndex).trim();
        className ="data." + name;
        System.out.println(className);

//
//        String methodName = "function";
//        String className = "data.CFG4J_Test";

        for (ASTNode func : funcAstNodeList) {
            if (((MethodDeclaration) func).getName().getIdentifier().equals(methodName)) {
                createCloneMethod((MethodDeclaration) func);
                System.out.println("func = " + ((MethodDeclaration) func).getName() +"\n");
                result.append("func = " + ((MethodDeclaration) func).getName() +"\n");
                List<ASTNode> parameters = ((MethodDeclaration) func).parameters();
                System.out.println("parameters.size() = " + ((MethodDeclaration) func).parameters().size());
                result.append("parameters.size() = " + ((MethodDeclaration) func).parameters().size() +"\n");

                Timer T = new Timer(true);

                TimerTask memoryTask = new TimerTask() {
                    @Override
                    public void run() {
                        totalUsedMem += (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
//                        System.out.println("totalUsedMem = " + totalUsedMem);
                        tickCount += 1;
//                        System.out.println("tickCount = " + tickCount);
                        //callback.accept(totalUsedMem);
                    }
                };

                T.scheduleAtFixedRate(memoryTask, 0, 1); //0 delay and 5 ms tick

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

                LocalDateTime beforeTime;
                LocalDateTime afterTime;
                double usedTime = 0;

                //=======================FULL CONCOLIC VERSION 1===========================
                methodName = methodName + "CloneV1";

                Class<?>[] parameterClasses = getParameterClasses(parameters);
                Method method = Class.forName(className).getDeclaredMethod(methodName, parameterClasses);

                beforeTime = LocalDateTime.now();

                method.invoke(parameterClasses, createRandomTestData(parameterClasses));
                MarkedPath.markPathToCFG(cfgNode);

                afterTime = LocalDateTime.now();
                usedTime += Math.abs((float) Duration.between(beforeTime, afterTime).toMillis());

                boolean isTestedSuccessfully = true;

                beforeTime = LocalDateTime.now();

                for (CfgNode uncoveredNode = MarkedPath.findUncoveredNode(cfgNode, null); uncoveredNode != null; ) {

                    afterTime = LocalDateTime.now();
                    usedTime += Math.abs((float) Duration.between(beforeTime, afterTime).toMillis());

                    beforeTime = LocalDateTime.now();

                    Path newPath = (new FindPath(cfgNode, uncoveredNode, cfgEndCfgNode)).getPath();

                    afterTime = LocalDateTime.now();
                    usedTime += Math.abs((float) Duration.between(beforeTime, afterTime).toMillis());

                    beforeTime = LocalDateTime.now();

                    SymbolicExecution solution = new SymbolicExecution(newPath, parameters);

                    if(solution.getModel() == null) {
                        isTestedSuccessfully = false;
                        break;
                    }

                    afterTime = LocalDateTime.now();
                    usedTime += Math.abs((float) Duration.between(beforeTime, afterTime).toMillis());

                    beforeTime = LocalDateTime.now();

                    method.invoke(parameterClasses, getParameterValue(parameterClasses));
                    MarkedPath.markPathToCFG(cfgNode);

                    afterTime = LocalDateTime.now();
                    usedTime += Math.abs((float) Duration.between(beforeTime, afterTime).toMillis());

                    beforeTime = LocalDateTime.now();

                    uncoveredNode = MarkedPath.findUncoveredNode(cfgNode, null);
                    System.out.println("Uncovered Node: " + uncoveredNode);

                    result.append("Uncovered Node: " + uncoveredNode+ "\n");
                }

                if(isTestedSuccessfully) {
                    System.out.println("Tested successfully with 100% coverage");
                    result.append("Tested successfully with 100% coverage\n");
                }
                else{
                    System.out.println("Test fail due to UNSATISFIABLE constraint");
                    result.append("Test fail due to UNSATISFIABLE constraint\n");
                }

                System.out.println("Total Concolic time: " + usedTime);
                result.append("Total Concolic time: " + usedTime+"\n");
                //========================================


                //=======================FULL CONCOLIC VERSION 2===========================
//                methodName = methodName + "CloneV2";
//
//                Class<?>[] parameterClassesV2 = getParameterClasses(parameters);
//                Method methodV2 = Class.forName(className).getDeclaredMethod(methodName, parameterClassesV2);
//
//                beforeTime = LocalDateTime.now();
//
//                List<Path> paths = (new FindAllPath(cfgNode)).getPaths();
//                System.out.println(paths.size());
//
//                afterTime = LocalDateTime.now();
//                usedTime += Math.abs((float) Duration.between(beforeTime, afterTime).toMillis());
//
//
//                for (int i = 0; i < paths.size(); i += 1) {
//                    beforeTime = LocalDateTime.now();
//
//                    SymbolicExecution execution = new SymbolicExecution(paths.get(i), parameters);
//
//                    afterTime = LocalDateTime.now();
//                    usedTime += Math.abs((float) Duration.between(beforeTime, afterTime).toMillis());
//
//                    beforeTime = LocalDateTime.now();
//
//                    methodV2.invoke(parameterClassesV2, getParameterValue(parameterClassesV2));
//                    if (!MarkedPathV2.check(paths.get(i))) {
//                        System.out.println("Path is not covered");
//                    }
//
//                    afterTime = LocalDateTime.now();
//                    usedTime += Math.abs((float) Duration.between(beforeTime, afterTime).toMillis());
//                }
//
//                System.out.println("Total Concolic time: " + usedTime);
                //===================================

                T.cancel();

                System.out.println("func = " + ((MethodDeclaration) func).getName());
                result.append("func = " + ((MethodDeclaration) func).getName()+"\n");
//                System.out.println("tickCount = " + tickCount);
                float usedMem = ((float) totalUsedMem) / tickCount / 1024 / 1024;
                System.out.print("used mem = ");
                result.append("used mem = ");
                System.out.printf("%.2f", usedMem);
                result.append(usedMem + "\n");
                System.out.println(" MB");
                result.append(" MB");

                break;
            }
        }

        //print the template of test report
//        try {
//            Utils.printReport(start.AppStart.class.getResource(Exporter._TEMPLATE_REPORT_PATH).toURI().getPath());
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
        return result;
    }

}
