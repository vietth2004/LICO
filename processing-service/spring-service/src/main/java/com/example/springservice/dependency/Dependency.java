package com.example.springservice.dependency;

import com.example.springservice.ast.dependency.DependencyCountTable;

public class Dependency {

    private Integer callerNode;

    private Integer calleeNode;

    private DependencyCountTable type;

    public Dependency(Integer callerNode, Integer calleeNode) {
        this.callerNode = callerNode;
        this.calleeNode = calleeNode;
    }

    public Dependency(Integer callerNode, Integer calleeNode, DependencyCountTable type) {
        this.callerNode = callerNode;
        this.calleeNode = calleeNode;
        this.type = type;
    }

    public Integer getCallerNode() {
        return callerNode;
    }

    public void setCallerNode(Integer callerNode) {
        this.callerNode = callerNode;
    }

    public Integer getCalleeNode() {
        return calleeNode;
    }

    public void setCalleeNode(Integer calleeNode) {
        this.calleeNode = calleeNode;
    }

    public DependencyCountTable getType() {
        return type;
    }

    public void setType(DependencyCountTable type) {
        this.type = type;
    }
}
