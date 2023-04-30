package com.example.unittesting.controller;

import core.algorithms.FindAllPath;
import core.cfg.CfgBlock;
import core.cfg.CfgEndBlockNode;
import core.cfg.CfgNode;
import core.dataStructure.Path;
import core.parser.ASTHelper;
import core.parser.ProjectParser;
import core.utils.Utils;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
@RestController
@RequestMapping("/api/unit-testing-service/")
public class UTestController {
    private static long totalUsedMem = 0;
    private static long tickCount = 0;
    @GetMapping
    public String getTest() throws IOException {
        StringBuilder rt = new StringBuilder();
        String path = "core-engine/cfg/data/child/CFG4J_Test.java";
        rt.append("Start parsing..."+"\\\n");
        ArrayList<ASTNode> funcAstNodeList = ProjectParser.parseFile(path);

        rt.append("count = " + funcAstNodeList.size() + "\n");

        for (ASTNode func : funcAstNodeList) {
            if (((MethodDeclaration)func).getName().getIdentifier().equals("testIf"))
            {

                Timer T = new Timer(true);

                TimerTask memoryTask = new TimerTask(){
                    @Override
                    public void run(){
                        totalUsedMem += (Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory());
//                        System.out.println("totalUsedMem = " + totalUsedMem);
                        tickCount += 1;
//                        System.out.println("tickCount = " + tickCount);
                        //callback.accept(totalUsedMem);
                    }
                };

                T.scheduleAtFixedRate(memoryTask, 0, 1); //0 delay and 5 ms tick

                LocalDateTime beforeTime = LocalDateTime.now();


                Block functionBlock = Utils.getFunctionBlock(func);

                CfgNode cfgBeginCfgNode = new CfgNode();
                cfgBeginCfgNode.setIsBeginCfgNode(true);

                CfgEndBlockNode cfgEndCfgNode = new CfgEndBlockNode();
                cfgEndCfgNode.setIsEndCfgNode(true);

                CfgNode block = new CfgBlock();
                block.setAst(functionBlock);

                block.setBeforeStatementNode(cfgBeginCfgNode);
                block.setAfterStatementNode(cfgEndCfgNode);

                FindAllPath paths = new FindAllPath(ASTHelper.generateCFGFromASTBlockNode(block));

                for(Path pathI : paths.getPaths()) {
                    rt.append(pathI + "\n");
                }

                LocalDateTime afterTime = LocalDateTime.now();
                Duration duration = Duration.between(beforeTime, afterTime);
                float diff = Math.abs((float) duration.toMillis());
                T.cancel();
            }
        }
        return "Hello World";
    }
    @RequestMapping("/is-running")
    public String running(){
        return "Hi there, I am still alive";
    }

}
