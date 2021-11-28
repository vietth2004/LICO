package com.example.xmlservice.ast.dependency;

import com.example.xmlservice.ast.node.Node;

public class Pair{
    private Node node;
    private DependencyCountTable dependency;

    public Pair(Node first, DependencyCountTable second) {
        this.node = first;
        this.dependency = second;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public DependencyCountTable getDependency() {
        return dependency;
    }

    public void setDependency(DependencyCountTable dependency) {
        this.dependency = dependency;
    }
}