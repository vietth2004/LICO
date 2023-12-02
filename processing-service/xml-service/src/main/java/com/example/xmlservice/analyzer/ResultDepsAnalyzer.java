package com.example.xmlservice.analyzer;

import com.example.xmlservice.ast.dependency.Dependency;
import com.example.xmlservice.ast.node.JavaNode;
import com.example.xmlservice.dom.Node;

import java.util.ArrayList;
import java.util.List;

public class ResultDepsAnalyzer implements StrutAnalyzer{

    @Override
    public List<Dependency> analyze(List<JavaNode> javaNodes, List<Node> strutsNodes) {
        List<Dependency> strutResultDeps = new ArrayList<>();

        for(Node node : strutsNodes) {

        }

        return strutResultDeps;
    }
}
