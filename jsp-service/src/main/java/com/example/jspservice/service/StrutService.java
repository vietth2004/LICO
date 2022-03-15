package com.example.jspservice.service;

import com.example.jspservice.ast.dependency.Dependency;
import com.example.jspservice.ast.node.JavaNode;
import com.example.jspservice.dom.Node;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface StrutService {
    List<Node> parseProjectWithPath(String path) throws IOException, ExecutionException, InterruptedException;
    List<Node> parseProjectWithFile(MultipartFile file) throws IOException;
    List<Dependency> analyzeDependency(List<JavaNode> javaNode, List<Node> xmlNodes) throws ExecutionException, InterruptedException;
    List<Dependency> analyzeDependency(List<JavaNode> javaNode, List<Node> xmlNodes, List<Node> strutsNode) throws ExecutionException, InterruptedException;
}
