package com.example.javaservice.model;

import com.example.javaservice.ast.dependency.Dependency;
import com.example.javaservice.ast.dependency.JavaDependency;
import com.example.javaservice.ast.node.JavaNode;
import com.example.javaservice.ast.utility.Utility;

import java.util.ArrayList;
import java.util.List;

public class Response {

    private JavaNode RootNode;

    private List<JavaNode> allNodes = new ArrayList<>();

    private List allDependencies = new ArrayList<>();

    public Response() {
    }

    public Response(JavaNode javaNode, List<JavaNode> javaNodeList) {
        this.RootNode = javaNode;
        this.allNodes = javaNodeList;
        this.allDependencies = Utility.getDependency(javaNode);
    }

    public JavaNode getRootNode() {
        return RootNode;
    }

    public void setRootNode(JavaNode rootNode) {
        this.RootNode = rootNode;
    }

    public List<JavaNode> getAllNodes() {
        return allNodes;
    }

    public void setAllNodes(List<JavaNode> allNodes) {
        this.allNodes = allNodes;
    }

    public List getAllDependencies() {
        return allDependencies;
    }

    public void setAllDependencies(List allDependencies) {
        this.allDependencies = allDependencies;
    }
}
