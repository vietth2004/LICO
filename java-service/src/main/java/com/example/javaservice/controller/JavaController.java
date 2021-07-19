package com.example.javaservice.controller;

//import com.example.javaservice.javacia.java.JavaCiaException;
import com.example.javaservice.ast.node.JavaNode;
import com.example.javaservice.ast.node.Node;
import com.example.javaservice.ast.utility.Utility;
import com.example.javaservice.model.Request;
import com.example.javaservice.model.Response;
import com.example.javaservice.service.JavaService;
import mrmathami.cia.java.JavaCiaException;
import mrmathami.cia.java.jdt.tree.node.RootNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class JavaController {

    @Autowired
    private JavaService javaService;

    @PostMapping("/api/pathParse/toRoot")
    public Node parseProjectByPathToRootNode(@RequestBody Request path) throws JavaCiaException, IOException{
        RootNode javaRoot = (RootNode) javaService.parseProject(path.getPath());
        JavaNode node = new JavaNode(javaRoot);
        return node;
    }

    @PostMapping("/api/fileParse/toRoot")
    public Node parseProjectByFileToRootNode(@RequestParam(name ="file") MultipartFile file) throws JavaCiaException, IOException{
        RootNode javaRoot = (RootNode) javaService.parseProjectWithFile(file);
        JavaNode node = new JavaNode(javaRoot);
        return node;
    }

    @PostMapping("/api/fileParse/toNodes")
    public List<JavaNode> parseProjectByFileToNodes(@RequestParam(name ="file") MultipartFile file) throws JavaCiaException, IOException {
        RootNode javaRoot = (RootNode) javaService.parseProjectWithFile(file);
        List<JavaNode> nodeList = Utility.convertToAllNodes(javaRoot.getAllNodes());
        return nodeList;
    }

    @PostMapping("/api/pathParse/toNodes")
    public List<JavaNode> parseProjectByPathToNodes(@RequestBody Request path) throws JavaCiaException, IOException{
        RootNode javaRoot = (RootNode) javaService.parseProject(path.getPath());
        List<JavaNode> nodeList = Utility.convertToAllNodes(javaRoot.getAllNodes());
        return nodeList;
    }

    @PostMapping("/api/pathParse")
    public Response parseProjectByPath(@RequestBody Request path) throws JavaCiaException, IOException{
        RootNode javaRoot = (RootNode) javaService.parseProject(path.getPath());
        JavaNode node = new JavaNode(javaRoot);
        List<JavaNode> nodeList = Utility.convertToAllNodes(javaRoot.getAllNodes());
        return new Response(node, nodeList);
    }

    @PostMapping("/api/fileParse")
    public Response parseProjectByFile(@RequestParam(name ="file") MultipartFile file) throws JavaCiaException, IOException{
        RootNode javaRoot = (RootNode) javaService.parseProjectWithFile(file);
        JavaNode node = new JavaNode(javaRoot);
        List<JavaNode> nodeList = Utility.convertToAllNodes(javaRoot.getAllNodes());
        return new Response(node, nodeList);
    }
}
