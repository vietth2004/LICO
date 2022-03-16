package com.example.jsfservice.service;

import com.example.jsfservice.dom.Node;
import com.example.jsfservice.ast.dependency.Dependency;
import com.example.jsfservice.ast.node.JavaNode;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface XmlService {
    List<Node> parseProjectWithPath(String path) throws IOException, ExecutionException, InterruptedException;
    List<Node> parseProjectWithFile(MultipartFile file) throws IOException;
    List<Dependency> analyzeDependency(List<JavaNode> javaNode, List<Node> xmlNodes) throws ExecutionException, InterruptedException;
}
