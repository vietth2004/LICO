package com.example.parserservice.model;

import com.example.parserservice.ast.dependency.Dependency;
import com.example.parserservice.ast.dependency.Pair;
import com.example.parserservice.ast.node.JavaNode;

import java.util.List;

public class Request {

    private JavaNode rootNode;

    private List allDependencies;

    private List allNodes;

    public Request() {
    }

    public Request(JavaNode rootNode, List allDependencies, List allNodes) {
        this.rootNode = rootNode;
        this.allDependencies = allDependencies;
        this.allNodes = allNodes;
    }

    public JavaNode getRootNode() {
        return rootNode;
    }

    public void setRootNode(JavaNode rootNode) {
        this.rootNode = rootNode;
    }

    public List getAllDependencies() {
        return allDependencies;
    }

    public void setAllDependencies(List allDependencies) {
        this.allDependencies = allDependencies;
    }

    public List getAllNodes() {
        return allNodes;
    }

    public void setAllNodes(List allNodes) {
        this.allNodes = allNodes;
    }
}
