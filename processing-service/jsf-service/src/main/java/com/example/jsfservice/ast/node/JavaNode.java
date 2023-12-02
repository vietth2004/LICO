package com.example.jsfservice.ast.node;

import com.example.jsfservice.ast.annotation.JavaAnnotation;
import com.example.jsfservice.ast.dependency.Pair;
import com.example.jsfservice.ast.type.JavaType;
import com.example.jsfservice.ast.utility.Utility;
import mrmathami.cia.java.jdt.tree.node.AbstractNode;
import mrmathami.cia.java.jdt.tree.node.InterfaceNode;
import mrmathami.cia.java.jdt.tree.node.MethodNode;
import mrmathami.cia.java.jdt.tree.node.RootNode;
import mrmathami.cia.java.jdt.tree.node.attribute.AbstractAnnotatedNode;
import mrmathami.cia.java.tree.node.attribute.JavaModifiedNode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class JavaNode extends Node implements Serializable {

    private transient List<Pair> dependencyFrom;

    private transient List<Pair> dependencyTo;

    private transient List<JavaNode> children = new ArrayList<>();

    private transient List annotates = null;

    private transient List parameters = null;

    private transient List extendInterfaces = null;

    private transient List<JavaAnnotation> annotatesWithValue = null;

    public List getAnnotatesWithValue() {
        return annotatesWithValue;
    }

    public void setAnnotatesWithValue(List<JavaAnnotation> annotatesWithValue) {
        this.annotatesWithValue = annotatesWithValue;
    }

    private List modifiers = null;

    private transient Integer parent;

    private transient String path = null;

    public JavaNode() {
    }

    public JavaNode(Integer id) {
        super(id);
    }

    public JavaNode(AbstractNode abstractNode, Boolean nodes, Integer parent) {
        super(abstractNode);

        this.dependencyFrom = Utility.convertMap(abstractNode.getDependencyFrom());
        this.dependencyTo = Utility.convertMap(abstractNode.getDependencyTo());
        this.parent = parent;
        this.children = this.returnChildren(abstractNode, nodes);
        this.setupProperties(abstractNode);
    }

    public JavaNode(AbstractNode abstractNode, Boolean nodes, Integer parent, String path) {
        super(abstractNode);

        this.dependencyFrom = Utility.convertMap(abstractNode.getDependencyFrom());
        this.dependencyTo = Utility.convertMap(abstractNode.getDependencyTo());
        this.parent = parent;
        this.children = this.returnChildren(abstractNode, nodes);
        this.path = path;
        this.setupProperties(abstractNode);
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

    public List getChildren() {
        return children;
    }

    public void setChildren(List children) {
        this.children = children;
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

    public List getModifiers() {
        return modifiers;
    }

    public void setModifiers(List modifiers) {
        this.modifiers = modifiers;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private void setupProperties(AbstractNode abstractNode) {
        if (abstractNode instanceof MethodNode) {
            this.parameters = Utility.convertParameters(((MethodNode) abstractNode).getParameters());
        }

        if (abstractNode instanceof AbstractAnnotatedNode) {
            this.annotates = Utility.convertAnnotates(((AbstractAnnotatedNode) abstractNode).getAnnotates());
        }

        if (abstractNode instanceof JavaModifiedNode) {
            this.modifiers = Utility.convertModifiers(((JavaModifiedNode) abstractNode).getModifierSet());
        }

        if (abstractNode instanceof InterfaceNode) {
            this.extendInterfaces = Utility.convertParameters(((InterfaceNode) abstractNode).getExtendsInterfaces());
        }
    }

    private List returnChildren(AbstractNode abstractNode, Boolean nodes) {
        if (nodes == true) {
            return Utility.convertAbstractNode(abstractNode.getChildren());
        } else {
            return Utility.convertChildren(abstractNode.getChildren());
        }
    }
}
