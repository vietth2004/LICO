package com.example.parserservice.model;

import com.example.parserservice.ast.dependency.Dependency;
import com.example.parserservice.ast.node.JavaNode;
import com.example.parserservice.ast.node.Node;

import java.util.List;


public class Response {

    private JavaNode rootNode;

    private List<Dependency> allDependencies;

    private List allNodes;

    private List nodesWeight;

    private Integer totalNodes;

    public Response() {
    }

    public Response(JavaNode rootNode, List<Dependency> allDependencies) {
        this.rootNode = rootNode;
        this.allDependencies = allDependencies;
    }

    public Response(JavaNode rootNode, List<Dependency> allDependencies, List allNodes) {
        this.rootNode = rootNode;
        this.allDependencies = allDependencies;
        this.allNodes = allNodes;
        this.totalNodes = allNodes.size();
    }

    public Response(JavaNode rootNode, List<Dependency> allDependencies, List allNodes, List nodesWeight) {
        this.rootNode = rootNode;
        this.allDependencies = allDependencies;
        this.allNodes = allNodes;
        this.nodesWeight = nodesWeight;
        this.totalNodes = allNodes.size();
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

    public Integer getTotalNodes() {
        return totalNodes;
    }

    public void setTotalNodes(Integer totalNodes) {
        this.totalNodes = totalNodes;
    }

    public List getNodesWeight() {
        return nodesWeight;
    }

    public void setNodesWeight(List nodesWeight) {
        this.nodesWeight = nodesWeight;
    }
}
