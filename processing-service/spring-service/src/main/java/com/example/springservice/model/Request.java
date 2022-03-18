package com.example.springservice.model;


import com.example.springservice.ast.node.JavaNode;
import com.example.springservice.ast.node.Node;
import com.example.springservice.dependency.Dependency;
import com.example.springservice.dom.Properties.PropertiesFileNode;

import java.util.List;

public class Request {

    private JavaNode rootNode;

    private List<Dependency> allDependencies;

    private List<com.example.springservice.ast.node.JavaNode> javaNodes;

    private List<Node> xmlNodes;

    private List<Node> jspNodes;

    private List<PropertiesFileNode> propertiesNodes;

    private String path;

    public Request() {
    }

    public Request(JavaNode rootNode, List<Dependency> allDependencies, List allNodes) {
        this.rootNode = rootNode;
        this.allDependencies = allDependencies;
        this.javaNodes = allNodes;
    }

    public Request(JavaNode rootNode, List<Dependency> allDependencies, List allNodes, List xmlNodes) {
        this.rootNode = rootNode;
        this.allDependencies = allDependencies;
        this.javaNodes = allNodes;
        this.xmlNodes = xmlNodes;
    }

    public Request(JavaNode rootNode, List<Dependency> allDependencies, List allNodes, List xmlNodes, List jspNodes) {
        this.rootNode = rootNode;
        this.allDependencies = allDependencies;
        this.javaNodes = allNodes;
        this.xmlNodes = xmlNodes;
        this.jspNodes = jspNodes;
    }

    public Request(JavaNode rootNode, List<Dependency> allDependencies, List javaNodes, List<Node> xmlNodes, List<Node> jspNodes, String path) {
        this.rootNode = rootNode;
        this.allDependencies = allDependencies;
        this.javaNodes = javaNodes;
        this.xmlNodes = xmlNodes;
        this.jspNodes = jspNodes;
        this.path = path;
    }

    public Request(JavaNode rootNode, List<Dependency> allDependencies, List javaNodes, List<Node> xmlNodes, List<Node> jspNodes, List<PropertiesFileNode> propertiesNodes, String path) {
        this.rootNode = rootNode;
        this.allDependencies = allDependencies;
        this.javaNodes = javaNodes;
        this.xmlNodes = xmlNodes;
        this.jspNodes = jspNodes;
        this.propertiesNodes = propertiesNodes;
        this.path = path;
    }

    public JavaNode getRootNode() {
        return rootNode;
    }

    public void setRootNode(JavaNode rootNode) {
        this.rootNode = rootNode;
    }

    public List getAllDependencies() {
        return allDependencies;
    }

    public void setAllDependencies(List<Dependency> allDependencies) {
        this.allDependencies = allDependencies;
    }

    public List<com.example.springservice.ast.node.JavaNode> getJavaNodes() {
        return javaNodes;
    }

    public void setJavaNodes(List<com.example.springservice.ast.node.JavaNode> javaNodes) {
        this.javaNodes = javaNodes;
    }

    public List getXmlNodes() {
        return xmlNodes;
    }

    public void setXmlNodes(List<Node> xmlNodes) {
        this.xmlNodes = xmlNodes;
    }

    public List getJspNodes() {
        return jspNodes;
    }

    public void setJspNodes(List<Node> jspNodes) {
        this.jspNodes = jspNodes;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<PropertiesFileNode> getPropertiesNodes() {
        return propertiesNodes;
    }

    public void setPropertiesNodes(List<PropertiesFileNode> propertiesNodes) {
        this.propertiesNodes = propertiesNodes;
    }
}
