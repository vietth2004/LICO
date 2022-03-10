package com.example.strutservice.analyzer;

import com.example.strutservice.ast.dependency.Dependency;
import com.example.strutservice.ast.node.JavaNode;
import com.example.strutservice.dom.Node;

import java.util.ArrayList;
import java.util.List;

public class InterceptorDepsAnalyzer implements StrutAnalyzer{

    @Override
    public List<Dependency> analyze(List<JavaNode> javaNodes, List<Node> strutsNodes) {
        List<Dependency> strutInterceptorDeps = new ArrayList<>();

        for(Node node : strutsNodes) {

        }

        return strutInterceptorDeps;
    }
}
