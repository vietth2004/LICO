package com.example.springservice.ast.node;

import com.example.springservice.ast.annotation.JavaAnnotation;
import com.example.springservice.ast.dependency.Pair;
import com.example.springservice.ast.type.JavaType;
import com.example.springservice.ast.utility.Utility;
import mrmathami.annotations.Nonnull;
import mrmathami.cia.java.jdt.tree.node.AbstractNode;
import mrmathami.cia.java.jdt.tree.node.InterfaceNode;
import mrmathami.cia.java.jdt.tree.node.RootNode;
import mrmathami.cia.java.jdt.tree.node.attribute.AbstractAnnotatedNode;

import java.util.ArrayList;
import java.util.List;

public class JavaNode extends Node {


    @Nonnull
    private transient List<Pair> dependencyTo;

    @Nonnull
    private transient List<Integer> children = new ArrayList<>();

    private transient Integer parent;

    private transient List<JavaAnnotation> annotates = new ArrayList<>();

    private transient List<JavaType> extendInterfaces = new ArrayList<>();

    @Nonnull
    public JavaNode(AbstractNode abstractNode) {
        super(abstractNode);
        this.children = Utility.convertChildren(abstractNode.getChildren());
        this.dependencyTo = Utility.convertMap(abstractNode.getDependencyTo());
        this.setupProperties(abstractNode);
    }

    public JavaNode(RootNode rootNode) {
        super(rootNode);
        this.dependencyTo = Utility.convertMap(rootNode.getDependencyTo());
    }

    public JavaNode() {
    }

    public List<Pair> getDependencyTo() {
        return dependencyTo;
    }

    public void setDependencyTo(List<Pair> dependencyTo) {
        this.dependencyTo = dependencyTo;
    }

    public void setChildren(List<Integer> children) {
        this.children = children;
    }

    @Nonnull
    public List<Integer> getChildren() {
        return children;
    }

    public List<JavaAnnotation> getAnnotates() {
        return annotates;
    }

    public void setAnnotates(List<JavaAnnotation> annotates) {
        this.annotates = annotates;
    }

    public List<JavaType> getExtendInterfaces() {
        return extendInterfaces;
    }

    public void setExtendInterfaces(List<JavaType> extendInterfaces) {
        this.extendInterfaces = extendInterfaces;
    }

    private void setupProperties(AbstractNode abstractNode) {
        if (abstractNode instanceof AbstractAnnotatedNode) {
            this.annotates = Utility.convertAnnotates(((AbstractAnnotatedNode) abstractNode).getAnnotates());
        }

        if (abstractNode instanceof InterfaceNode) {
            this.extendInterfaces = Utility.convertParameters(((InterfaceNode) abstractNode).getExtendsInterfaces());
        }
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }
}
