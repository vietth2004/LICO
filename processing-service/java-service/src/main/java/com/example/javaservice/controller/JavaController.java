package com.example.javaservice.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import com.example.javaservice.javacia.java.JavaCiaException;
import com.example.javaservice.ast.node.JavaNode;
import com.example.javaservice.ast.node.Node;
import com.example.javaservice.ast.utility.Utility;
import com.example.javaservice.model.Request;
import com.example.javaservice.model.Response;
import com.example.javaservice.service.JavaService;

import com.example.javaservice.utility.Checker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import mrmathami.cia.java.JavaCiaException;
import mrmathami.cia.java.jdt.tree.node.RootNode;

@RestController
@RequestMapping("/api/java-service/")
public class JavaController {

    @Autowired
    private JavaService javaService;

    @PostMapping("/pathParse/toRoot")
    public Node parseProjectByPathToRootNode(@RequestBody Request path) throws JavaCiaException, IOException{
        RootNode javaRoot = (RootNode) javaService.parseProject(path.getPath());
        JavaNode node = new JavaNode(javaRoot);
        return node;
    }

    @PostMapping("/fileParse/toRoot")
    public Node parseProjectByFileToRootNode(@RequestParam(name ="file") MultipartFile file) throws JavaCiaException, IOException{
        RootNode javaRoot = (RootNode) javaService.parseProjectWithFile(file);
        JavaNode node = new JavaNode(javaRoot);
        return node;
    }

    @PostMapping("/fileParse/toNodes")
    public List<JavaNode> parseProjectByFileToNodes(@RequestParam(name ="file") MultipartFile file) throws JavaCiaException, IOException {
        RootNode javaRoot = (RootNode) javaService.parseProjectWithFile(file);
        List<JavaNode> nodeList = Utility.convertToAllNodes(javaRoot.getAllNodes());
        return nodeList;
    }

    @PostMapping("/pathParse/toNodes")
    public List<JavaNode> parseProjectByPathToNodes(@RequestBody Request path) throws JavaCiaException, IOException{
        RootNode javaRoot = (RootNode) javaService.parseProject(path.getPath());
        List<JavaNode> nodeList = Utility.convertToAllNodes(javaRoot.getAllNodes());
        return nodeList;
    }

    @PostMapping("/pathParse/dependencies")
    public List parseProjectByPathToDependencies(@RequestBody Request path) throws JavaCiaException, IOException{
        RootNode javaRoot = (RootNode) javaService.parseProject(path.getPath());
        JavaNode node = new JavaNode(javaRoot);
        List dependencies = Utility.getDependency(node);
        return dependencies;
    }

    @PostMapping("/fileParse/dependencies")
    public List parseProjectByFileToToDependencies(@RequestParam(name ="file") MultipartFile file) throws JavaCiaException, IOException{
        RootNode javaRoot = (RootNode) javaService.parseProjectWithFile(file);
        JavaNode node = new JavaNode(javaRoot);
        List dependencies = Utility.getDependency(node);
        return dependencies;
    }

    @PostMapping("/fileParse")
    public Response parseProjectByFile(@RequestParam(name ="file") MultipartFile file) throws JavaCiaException, IOException{
        RootNode javaRoot = (RootNode) javaService.parseProjectWithFile(file);
        String path = "./project/" + "anonymous/" + file.getName() + ".project";
        JavaNode node = new JavaNode(javaRoot, path);
        Checker.changeDependencyType(node);
        List<JavaNode> nodeList = Utility.convertToAllNodes(javaRoot.getAllNodes());
        return new Response(node, nodeList);
    }

    @PostMapping("/pathParse")
    public Response parseProjectByPath(@RequestBody Request path) throws JavaCiaException, IOException{
        RootNode javaRoot = (RootNode) javaService.parseProject(path.getPath());
        JavaNode node = new JavaNode(javaRoot, path.getPath());
        Checker.changeDependencyType(node);
        List<JavaNode> nodes = Utility.convertToAllNodes(javaRoot.getAllNodes());
        nodes.forEach(
                elem -> {
                    JavaNode tmp = Utility.search(node, elem.getId());
                    if(tmp.getAnnotatesWithValue() == null) {
                        elem.setAnnotatesWithValue(new ArrayList());
                    } else {
                        elem.setAnnotatesWithValue(tmp.getAnnotatesWithValue());
                    }
                }
        );
        return new Response(node, nodes);
    }
    
}
