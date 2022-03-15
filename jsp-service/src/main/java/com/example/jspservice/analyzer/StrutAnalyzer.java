package com.example.jspservice.analyzer;

import com.example.jspservice.ast.dependency.Dependency;
import com.example.jspservice.ast.node.JavaNode;
import com.example.jspservice.dom.Node;

import java.util.List;

public interface StrutAnalyzer {
    List<Dependency> analyze(List<JavaNode> javaNodes, List<Node> strutsNodes);
}
