package com.example.gitservice.dto.parser;

import com.example.gitservice.ast.dependency.Dependency;
import com.example.gitservice.ast.node.JavaNode;

import java.util.List;

public class Request {

    private JavaNode rootNode;

    private List<Dependency> allDependencies;

    private List allNodes;

    public Request() {
    }

    public Request(JavaNode rootNode, List<Dependency> allDependencies, List allNodes) {
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

    public void setAllDependencies(List<Dependency> allDependencies) {
        this.allDependencies = allDependencies;
    }

    public List getAllNodes() {
        return allNodes;
    }

    public void setAllNodes(List allNodes) {
        this.allNodes = allNodes;
    }

}
