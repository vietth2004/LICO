package com.example.javaservice.ast.node;

import com.example.javaservice.ast.annotation.JavaAnnotation;
import com.example.javaservice.ast.dependency.DependencyCountTable;
import com.example.javaservice.ast.dependency.Pair;
import com.example.javaservice.ast.type.JavaType;
import com.example.javaservice.ast.utility.Utility;
import mrmathami.annotations.Nonnull;
import mrmathami.cia.java.jdt.tree.node.AbstractNode;
import mrmathami.cia.java.jdt.tree.node.MethodNode;
import mrmathami.cia.java.jdt.tree.node.RootNode;
import mrmathami.cia.java.jdt.tree.node.attribute.AbstractAnnotatedNode;
import mrmathami.cia.java.jdt.tree.node.attribute.AbstractModifiedAnnotatedNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class JavaNode extends Node {

    private List<String> modifiers = new ArrayList<>();

    @Nonnull
    private transient List<Pair> dependencyFrom;

    @Nonnull
    private transient List<Pair> dependencyTo;


    @Nonnull
    private transient List<Integer> children = new ArrayList<>();

    private transient List<JavaAnnotation> annotates = new ArrayList<>();

    private transient List<JavaType> parameters = new ArrayList<>();

    @Nonnull
    public JavaNode(AbstractNode abstractNode) {
        super(abstractNode);
        this.children = Utility.convertChildren(abstractNode.getChildren());
        this.dependencyFrom = Utility.convertMap(abstractNode.getDependencyFrom());
        this.dependencyTo = Utility.convertMap(abstractNode.getDependencyTo());

        if(abstractNode instanceof MethodNode) {
            this.parameters = Utility.convertParameters(((MethodNode) abstractNode).getParameters());
        }

        if(abstractNode instanceof AbstractAnnotatedNode) {
            this.annotates = Utility.convertAnnotates(((AbstractAnnotatedNode) abstractNode).getAnnotates());
        }

        if(abstractNode instanceof AbstractModifiedAnnotatedNode) {
            this.modifiers = Utility.convertModifiers(((AbstractModifiedAnnotatedNode) abstractNode).getModifiers());
        }
    }

    public JavaNode(RootNode rootNode) {
        super(rootNode);
//        this.children = Utility.convertAbstractNode(rootNode.getChildren());
        this.dependencyFrom = Utility.convertMap(rootNode.getDependencyFrom());
        this.dependencyTo = Utility.convertMap(rootNode.getDependencyTo());
    }

    public List<Pair> getDependencyFrom() {
        return dependencyFrom;
    }

    public void setDependencyFrom(List<Pair> dependencyFrom) {
        this.dependencyFrom = dependencyFrom;
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

    public List<JavaType> getParameters() {
        return parameters;
    }

    public void setParameters(List<JavaType> parameters) {
        this.parameters = parameters;
    }
}
