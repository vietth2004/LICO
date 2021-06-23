package com.example.javaservice.ast.node;

import com.example.javaservice.ast.annotation.JavaAnnotation;
import com.example.javaservice.ast.type.JavaType;
import com.example.javaservice.ast.utility.Utility;
import mrmathami.annotations.Nonnull;
import mrmathami.cia.java.jdt.tree.annotate.Annotate;
import mrmathami.cia.java.jdt.tree.node.AbstractNode;
import mrmathami.cia.java.jdt.tree.node.MethodNode;
import mrmathami.cia.java.jdt.tree.node.attribute.AbstractAnnotatedNode;
import mrmathami.cia.java.jdt.tree.node.attribute.AbstractModifiedAnnotatedNode;
import mrmathami.cia.java.jdt.tree.type.AbstractType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JavaNode extends Node {

    private List<String> modifiers = new ArrayList<>();

    @Nonnull
    private transient List<Node> dependencyFrom = new ArrayList<>();

    @Nonnull
    private transient List<Node> dependencyTo = new ArrayList<>();

    @Nonnull
    private transient List<JavaNode> children = new ArrayList<>();

    private transient List<JavaAnnotation> annotates = new ArrayList<>();

    private transient List<JavaType> parameters = new ArrayList<>();

    @Nonnull
    public JavaNode(AbstractNode abstractNode) {
        super(abstractNode);
        this.children = Utility.convertAbstractNode(abstractNode.getChildren());
        this.dependencyFrom = Utility.convertMap(abstractNode.getDependencyFrom().keySet());
        this.dependencyTo = Utility.convertMap(abstractNode.getDependencyTo().keySet());

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

    @Nonnull
    public List<Node> getDependencyFrom() {
        return dependencyFrom;
    }

    public void setDependencyFrom(List<Node> dependencyFrom) {
        this.dependencyFrom = dependencyFrom;
    }

    @Nonnull
    public List<Node> getDependencyTo() {
        return dependencyTo;
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
}
