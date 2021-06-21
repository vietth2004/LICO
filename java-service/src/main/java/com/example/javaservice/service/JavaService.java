package com.example.javaservice.service;

import mrmathami.cia.java.JavaCiaException;
import mrmathami.cia.java.tree.node.JavaRootNode;

import java.io.IOException;

public interface JavaService {
    public JavaRootNode parseProject(String path) throws JavaCiaException, IOException;
}
