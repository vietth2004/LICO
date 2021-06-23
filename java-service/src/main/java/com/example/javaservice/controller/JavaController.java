package com.example.javaservice.controller;

//import com.example.javaservice.javacia.java.JavaCiaException;
//import com.example.javaservice.javacia.jdt.tree.node.RootNode;
import com.example.javaservice.ast.node.JavaNode;
import com.example.javaservice.ast.node.Node;
import com.example.javaservice.model.Request;
import com.example.javaservice.service.JavaService;
import mrmathami.cia.java.JavaCiaException;
import mrmathami.cia.java.jdt.tree.node.RootNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class JavaController {

    @Autowired
    private JavaService javaService;

    @PostMapping("/api/parse")
    public Node parseProject(@RequestBody Request path) throws JavaCiaException, IOException{
        RootNode javaRoot = (RootNode) javaService.parseProject(path.getPath());
        JavaNode node = new JavaNode(javaRoot.getChildren().get(0));
        return node;
    }
}
