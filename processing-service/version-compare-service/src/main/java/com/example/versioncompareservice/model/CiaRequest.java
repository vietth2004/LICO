package com.example.versioncompareservice.model;

import com.example.versioncompareservice.ast.dependency.Dependency;
import com.example.versioncompareservice.ast.node.JavaNode;

import java.util.ArrayList;
import java.util.List;

public class CiaRequest {

    private List<JavaNode> javaNodes;

    private List<Dependency> dependencies = new ArrayList<>();

    private Integer totalNodes;

    private List<Integer> changedNodes;

    public CiaRequest() {
    }

    public CiaRequest(List<Dependency> allDependencies, Integer totalNodes) {
        this.dependencies = allDependencies;
        this.totalNodes = totalNodes;
    }

    public CiaRequest(List<JavaNode> javaNodes, List<Dependency> dependencies, Integer totalNodes, List<Integer> changedNodes) {
        this.javaNodes = javaNodes;
        this.dependencies = dependencies;
        this.totalNodes = totalNodes;
        this.changedNodes = changedNodes;
    }

    public List<Dependency> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<Dependency> dependencies) {
        this.dependencies = dependencies;
    }

    public Integer getTotalNodes() {
        return totalNodes;
    }

    public void setTotalNodes(Integer totalNodes) {
        this.totalNodes = totalNodes;
    }

    public List<JavaNode> getJavaNodes() {
        return javaNodes;
    }

    public void setJavaNodes(List<JavaNode> javaNodes) {
        this.javaNodes = javaNodes;
    }

    public List<Integer> getChangedNodes() {
        return changedNodes;
    }

    public void setChangedNodes(List<Integer> changedNodes) {
        this.changedNodes = changedNodes;
    }
}
