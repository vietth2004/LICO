package com.example.parserservice.ast.dependency;

import com.example.parserservice.ast.node.Node;

public class OrientedPair {

    private Integer node;

    private DependencyCountTable dependency;

    public OrientedPair() {
    }

    public OrientedPair(Integer node, DependencyCountTable dependency) {
        this.node = node;
        this.dependency = dependency;
    }

    public Integer getNode() {
        return node;
    }

    public void setNode(Integer node) {
        this.node = node;
    }

    public DependencyCountTable getDependency() {
        return dependency;
    }

    public void setDependency(DependencyCountTable dependency) {
        this.dependency = dependency;
    }
}
