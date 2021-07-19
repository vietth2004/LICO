package com.example.javaservice.model;

import com.example.javaservice.ast.node.JavaNode;

import java.util.ArrayList;
import java.util.List;

public class Response {

    private JavaNode RootNode;

    private List<JavaNode> allNodes = new ArrayList<>();

    public Response() {
    }

    public Response(JavaNode javaNode, List<JavaNode> javaNodeList) {
        this.RootNode = javaNode;
        this.allNodes = javaNodeList;
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
}
