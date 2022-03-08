package com.example.strutservice.service;

import com.example.strutservice.ast.dependency.Dependency;
import com.example.strutservice.ast.node.JavaNode;
import com.example.strutservice.dom.Node;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface StrutService {
    List<Node> parseProjectWithPath(String path) throws IOException, ExecutionException, InterruptedException;
    List<Node> parseProjectWithFile(MultipartFile file) throws IOException;
    List<Dependency> analyzeDependency(List<JavaNode> javaNode, List<Node> xmlNodes) throws ExecutionException, InterruptedException;
    List<com.example.strutservice.ast.node.Node> parseProject(String folderPath) throws IOException, ExecutionException, InterruptedException;
}
