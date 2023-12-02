package com.example.javaservice.service;

//import com.example.javaservice.javacia.java.JavaCiaException;
//import com.example.javaservice.javacia.java.tree.node.JavaRootNode;

import mrmathami.cia.java.JavaCiaException;
import mrmathami.cia.java.tree.node.JavaRootNode;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface JavaService {
    public JavaRootNode parseProject(String path) throws JavaCiaException, IOException;

    public JavaRootNode parseProjectWithFile(MultipartFile file) throws JavaCiaException, IOException;
}
