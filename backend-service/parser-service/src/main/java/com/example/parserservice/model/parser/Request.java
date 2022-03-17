package com.example.parserservice.model.parser;

import com.example.parserservice.ast.dependency.Dependency;
import com.example.parserservice.ast.node.JavaNode;
import com.example.parserservice.dom.Node;

import java.io.Serializable;
import java.util.List;

public class Request implements Serializable {

    private JavaNode rootNode;

    private List<Dependency> allDependencies;

    private List javaNodes;

    private List<Node> xmlNodes;

    private List<Node> jspNodes;

    public Request() {
    }

    public Request(List<JavaNode> javaNodes) {
        this.javaNodes = javaNodes;
    }

    public Request(JavaNode rootNode,
                   List<Dependency> allDependencies,
                   List javaNodes,
                   List<Node> xmlNodes,
                   List<Node> jspNodes) {
        this.rootNode = rootNode;
        this.allDependencies = allDependencies;
        this.javaNodes = javaNodes;
        this.xmlNodes = xmlNodes;
        this.jspNodes = jspNodes;
    }

    public JavaNode getRootNode() {
        return rootNode;
    }

    public void setRootNode(JavaNode rootNode) {
        this.rootNode = rootNode;
    }

    public List<Dependency> getAllDependencies() {
        return allDependencies;
    }

    public void setAllDependencies(List<Dependency> allDependencies) {
        this.allDependencies = allDependencies;
    }

    public List getJavaNodes() {
        return javaNodes;
    }

    public void setJavaNodes(List javaNodes) {
        this.javaNodes = javaNodes;
    }

    public List<Node> getXmlNodes() {
        return xmlNodes;
    }

    public void setXmlNodes(List<Node> xmlNodes) {
        this.xmlNodes = xmlNodes;
    }

    public List<Node> getJspNodes() {
        return jspNodes;
    }

    public void setJspNodes(List<Node> jspNodes) {
        this.jspNodes = jspNodes;
    }
}
