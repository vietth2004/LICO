package com.example.strutsservice.analyzer;

import com.example.strutsservice.ast.dependency.Dependency;
import com.example.strutsservice.ast.node.JavaNode;
import com.example.strutsservice.dom.Node;

import java.util.ArrayList;
import java.util.List;

public class ActionDependencyAnalyzer implements StrutAnalyzer{

    @Override
    public List<Dependency> analyze(List<JavaNode> javaNodes, List<Node> strutsNodes) {
        List<Dependency> strutActionDeps = new ArrayList<>();

        for(Node node : strutsNodes) {

        }

        return strutActionDeps;
    }

}
