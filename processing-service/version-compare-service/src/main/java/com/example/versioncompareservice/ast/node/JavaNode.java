package com.example.versioncompareservice.ast.node;

import com.example.versioncompareservice.ast.annotation.JavaAnnotation;
import com.example.versioncompareservice.ast.dependency.Pair;
import com.example.versioncompareservice.ast.type.JavaType;
import com.example.versioncompareservice.ast.utility.Utility;
import mrmathami.cia.java.jdt.tree.dependency.DependencyCountTable;
import mrmathami.cia.java.jdt.tree.node.AbstractNode;
import mrmathami.cia.java.jdt.tree.node.InterfaceNode;
import mrmathami.cia.java.jdt.tree.node.MethodNode;
import mrmathami.cia.java.jdt.tree.node.RootNode;
import mrmathami.cia.java.jdt.tree.node.attribute.AbstractAnnotatedNode;
import mrmathami.cia.java.tree.node.attribute.JavaModifiedNode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JavaNode extends Node implements Serializable {

    private List modifiers = null;

    private transient List<Pair> dependencyFrom = new ArrayList<>();

    private transient List<Pair> dependencyTo = new ArrayList<>();

    private transient List children = new ArrayList<>();

    private transient List annotates = null;

    private transient List parameters = null;

    private transient List extendInterfaces = null;

    private String status = "unchanged";

    public JavaNode() {
    }

    public JavaNode(AbstractNode abstractNode, Boolean status) {
        super(abstractNode);
        this.dependencyFrom = Utility.convertMap(abstractNode.getDependencyFrom());
        this.dependencyTo = Utility.convertMap(abstractNode.getDependencyTo());
        this.children = this.returnChildren(abstractNode, status, true);
        this.setupProperties(abstractNode);
    }

    public JavaNode(AbstractNode abstractNode, Boolean status, Boolean getDependency) {
        super(abstractNode);
//        if(getDependency){
//            this.dependencyFrom = Utility.convertMap(abstractNode.getDependencyFrom());
//            this.dependencyTo = Utility.convertMap(abstractNode.getDependencyTo());
//        }
        this.children = this.returnChildren(abstractNode, status, getDependency);
        this.setupProperties(abstractNode);
        this.setDependency(abstractNode, getDependency);
    }

    public JavaNode(RootNode rootNode) {
        super(rootNode);
        this.children = Utility.convertAbstractNode(rootNode.getChildren(), true);
        this.dependencyFrom = Utility.convertMap(rootNode.getDependencyFrom());
        this.dependencyTo = Utility.convertMap(rootNode.getDependencyTo());
    }

    public JavaNode(mrmathami.cia.java.tree.node.JavaNode javaNode) {
        super(javaNode);
        this.dependencyFrom = Utility.convertMap((Map<AbstractNode, DependencyCountTable>) javaNode.getDependencyFrom());
        this.dependencyTo = Utility.convertMap((Map<AbstractNode, DependencyCountTable>) javaNode.getDependencyTo());
    }

    public JavaNode(mrmathami.cia.java.tree.node.JavaNode javaNode, String status) {
        super(javaNode);
        this.setupProperties((AbstractNode) javaNode);
        this.status = status;

        if(status.equals("deleted")) {
//            this.dependencyFrom = Utility.convertMap((Map<AbstractNode, DependencyCountTable>) javaNode.getDependencyFrom());
//            this.dependencyTo = Utility.convertMap((Map<AbstractNode, DependencyCountTable>) javaNode.getDependencyTo());
            this.children = this.returnChildren((AbstractNode) javaNode, true, false);
        }
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void addChildren(JavaNode javaNode) {
        this.children.add(javaNode);
    }

    private void setupProperties (AbstractNode abstractNode) {
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

    private void setDependency(AbstractNode abstractNode, Boolean getDependency) {
        if(getDependency){
            this.dependencyFrom = Utility.convertMap(abstractNode.getDependencyFrom());
            this.dependencyTo = Utility.convertMap(abstractNode.getDependencyTo());
        }
    }

    private List returnChildren(AbstractNode abstractNode, Boolean nodes, Boolean getDependency) {
        if (nodes == true) {
            return Utility.convertAbstractNode(abstractNode.getChildren(), getDependency);
        } else {
            return Utility.convertChildren(abstractNode.getChildren());
        }
    }
}
