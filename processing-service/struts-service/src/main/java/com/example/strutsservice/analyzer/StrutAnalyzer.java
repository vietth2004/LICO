package com.example.strutsservice.analyzer;

import com.example.strutsservice.ast.dependency.Dependency;
import com.example.strutsservice.ast.node.JavaNode;
import com.example.strutsservice.dom.Node;

import java.util.List;

public interface StrutAnalyzer {
    List<Dependency> analyze(List<JavaNode> javaNodes, List<Node> strutsNodes);
}
