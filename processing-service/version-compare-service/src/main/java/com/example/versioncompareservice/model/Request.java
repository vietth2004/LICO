package com.example.versioncompareservice.model;

import com.example.versioncompareservice.ast.dependency.Dependency;
import com.example.versioncompareservice.ast.node.JavaNode;

import java.util.List;

public class Request {

    private JavaNode rootNode;

    private List<Dependency> allDependencies;

    private List allNodes;

    private List nodesWeight;

    private Integer totalNodes;

    public Request() {
    }

    public Request(JavaNode rootNode, List<Dependency> allDependencies) {
        this.rootNode = rootNode;
        this.allDependencies = allDependencies;
    }

    public Request(JavaNode rootNode, List<Dependency> allDependencies, List allNodes) {
        this.rootNode = rootNode;
        this.allDependencies = allDependencies;
        this.allNodes = allNodes;
        this.totalNodes = allNodes.size();
    }

    public Request(JavaNode rootNode, List<Dependency> allDependencies, List allNodes, List nodesWeight) {
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
