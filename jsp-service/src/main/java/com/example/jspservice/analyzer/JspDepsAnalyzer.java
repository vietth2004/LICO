package com.example.jspservice.analyzer;

import com.example.jspservice.ast.dependency.Dependency;
import com.example.jspservice.ast.node.JavaNode;
import com.example.jspservice.dom.Node;

import java.util.ArrayList;
import java.util.List;

public class JspDepsAnalyzer implements StrutAnalyzer{

    @Override
    public List<Dependency> analyze(List<JavaNode> javaNodes, List<Node> strutsNodes) {
        List<Dependency> strutJspDeps = new ArrayList<>();

        for(Node node : strutsNodes) {

        }

        return strutJspDeps;
    }
}
