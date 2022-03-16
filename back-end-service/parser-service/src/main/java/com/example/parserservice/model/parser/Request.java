package com.example.parserservice.model.parser;

import com.example.parserservice.ast.dependency.Dependency;
import com.example.parserservice.ast.node.JavaNode;

import java.util.List;

public class Request {

    private JavaNode rootNode;

    private List<Dependency> allDependencies;

    private List allNodes;

    private List xmlNodes;

    private List jspNodes;

    public Request() {
    }

    public Request(JavaNode rootNode, List<Dependency> allDependencies, List allNodes) {
        this.rootNode = rootNode;
        this.allDependencies = allDependencies;
        this.allNodes = allNodes;
    }

    public Request(JavaNode rootNode, List<Dependency> allDependencies, List allNodes, List xmlNodes) {
        this.rootNode = rootNode;
        this.allDependencies = allDependencies;
        this.allNodes = allNodes;
        this.xmlNodes = xmlNodes;
    }

    public Request(JavaNode rootNode, List<Dependency> allDependencies, List allNodes, List xmlNodes, List jspNodes) {
        this.rootNode = rootNode;
        this.allDependencies = allDependencies;
        this.allNodes = allNodes;
        this.xmlNodes = xmlNodes;
        this.jspNodes = jspNodes;
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

    public List getAllNodes() {
        return allNodes;
    }

    public void setAllNodes(List allNodes) {
        this.allNodes = allNodes;
    }

    public List getXmlNodes() {
        return xmlNodes;
    }

    public void setXmlNodes(List xmlNodes) {
        this.xmlNodes = xmlNodes;
    }

    public List getJspNodes() {
        return jspNodes;
    }

    public void setJspNodes(List jspNodes) {
        this.jspNodes = jspNodes;
    }
}
