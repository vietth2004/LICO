package com.example.javaservice.controller;


import com.example.javaservice.service.JavaService;
import com.example.javaservice.service.JavaServiceImpl;
import mrmathami.cia.java.JavaCiaException;
import mrmathami.cia.java.jdt.project.builder.SnapshotBuilder;
import mrmathami.cia.java.tree.node.JavaRootNode;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class JavaController {

    private JavaServiceImpl javaService = new JavaServiceImpl();

    @GetMapping("/api/parse")
    public String parseProject(@RequestParam(name = "path") String path) throws JavaCiaException, IOException{
        return javaService.parseProject(path).toJson();
    }
}
