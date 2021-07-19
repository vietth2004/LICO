package com.example.javaservice.ast.node;

import com.example.javaservice.ast.annotation.JavaAnnotation;
import com.example.javaservice.ast.dependency.DependencyCountTable;
import com.example.javaservice.ast.dependency.Pair;
import com.example.javaservice.ast.type.JavaType;
import com.example.javaservice.ast.utility.Utility;
import mrmathami.annotations.Nonnull;
import mrmathami.cia.java.jdt.tree.node.AbstractNode;
import mrmathami.cia.java.jdt.tree.node.InterfaceNode;
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
    private transient List<JavaNode> children = new ArrayList<>();

    @Nonnull
    private transient List<Integer> child = new ArrayList<>();

    private transient List<JavaAnnotation> annotates = new ArrayList<>();

    private transient List<JavaType> parameters = new ArrayList<>();

    private transient List<JavaType> extendInterfaces = new ArrayList<>();

    public JavaNode(Integer id) {
        super(id);
    }

    @Nonnull
    public JavaNode(AbstractNode abstractNode, Boolean nodes) {
        super(abstractNode);

        if(nodes == true) {
            this.children = Utility.convertAbstractNode(abstractNode.getChildren());
        } else {
            this.child = Utility.convertChildren(abstractNode.getChildren());
        }

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

        if(abstractNode instanceof InterfaceNode) {
            this.extendInterfaces = Utility.convertParameters(((InterfaceNode) abstractNode).getExtendsInterfaces());
        }
    }

    public JavaNode(RootNode rootNode) {
        super(rootNode);
        this.children = Utility.convertAbstractNode(rootNode.getChildren());
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

    public void setChildren(List<JavaNode> children) {
        this.children = children;
    }

    @Nonnull
    public List<JavaNode> getChildren() {
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

    public List<JavaType> getExtendInterfaces() {
        return extendInterfaces;
    }

    public void setExtendInterfaces(List<JavaType> extendInterfaces) {
        this.extendInterfaces = extendInterfaces;
    }

    public List<Integer> getChild() {
        return child;
    }

    public void setChild(List<Integer> child) {
        this.child = child;
    }
}
