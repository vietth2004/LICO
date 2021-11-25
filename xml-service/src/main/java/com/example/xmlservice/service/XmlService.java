package com.example.xmlservice.service;

import com.example.xmlservice.dom.Node;
import com.example.xmlservice.ast.dependency.Dependency;
import com.example.xmlservice.ast.node.JavaNode;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface XmlService {
    List<Node> parseProjectWithPath(String path) throws IOException;
    List<Node> parseProjectWithFile(MultipartFile file) throws IOException;
    List<Dependency> analyzeDependency(JavaNode javaNode, List<Node> xmlNodes);
}
