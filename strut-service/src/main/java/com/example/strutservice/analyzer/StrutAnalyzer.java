package com.example.strutservice.analyzer;

import com.example.strutservice.ast.dependency.Dependency;
import com.example.strutservice.ast.node.JavaNode;
import com.example.strutservice.ast.node.StrutsNode;
import com.example.strutservice.dom.Jsp.JspTagNode;
import com.example.strutservice.dom.Jsp.StrutsInterceptor;
import com.example.strutservice.dom.Node;

import java.util.ArrayList;
import java.util.List;

public interface StrutAnalyzer {
    List<Dependency> analyze(List<JavaNode> javaNodes, List<Node> strutsNodes);
}
