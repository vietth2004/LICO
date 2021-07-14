package com.example.springservice.dependency;

import com.example.springservice.ast.annotation.JavaAnnotation;
import com.example.springservice.ast.dependency.DependencyCountTable;
import com.example.springservice.ast.dependency.JavaDependency;
import com.example.springservice.ast.node.Node;

public class Dependency {
    private Node callerNode = new Node();

    private Node calleeNode = new Node();

    private DependencyCountTable javaDependency;

    public Dependency(Node callerNode, Node calleeNode) {
        this.callerNode = callerNode;
        this.calleeNode = calleeNode;
    }

    public Dependency(Node callerNode, Node calleeNode, DependencyCountTable javaDependency) {
        this.callerNode = callerNode;
        this.calleeNode = calleeNode;
        this.javaDependency = javaDependency;
    }

    public Node getCallerNode() {
        return callerNode;
    }

    public void setCallerNode(Node callerNode) {
        this.callerNode = callerNode;
    }

    public Node getCalleeNode() {
        return calleeNode;
    }

    public void setCalleeNode(Node calleeNode) {
        this.calleeNode = calleeNode;
    }

    public DependencyCountTable getJavaDependency() {
        return javaDependency;
    }

    public void setJavaDependency(DependencyCountTable javaDependency) {
        this.javaDependency = javaDependency;
    }
}
