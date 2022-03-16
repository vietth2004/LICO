package com.example.xmlservice.analyzer;

import com.example.xmlservice.ast.dependency.Dependency;
import com.example.xmlservice.ast.node.JavaNode;
import com.example.xmlservice.dom.Node;

import java.util.List;

public interface StrutAnalyzer {
    List<Dependency> analyze(List<JavaNode> javaNodes, List<Node> strutsNodes);
}
