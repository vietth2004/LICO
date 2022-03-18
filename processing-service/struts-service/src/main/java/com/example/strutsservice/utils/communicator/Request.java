package com.example.strutsservice.utils.communicator;

import com.example.strutsservice.ast.dependency.Dependency;
import com.example.strutsservice.ast.node.JavaNode;
import com.example.strutsservice.dom.Node;
import com.example.strutsservice.dom.Properties.PropertiesFileNode;

import java.util.List;

public class Request {

    private JavaNode rootNode;

    private List<Dependency> allDependencies;

    private List<JavaNode> javaNodes;

    private List xmlNodes;

    private List jspNodes;

    private List<PropertiesFileNode> propertiesNodes;

    private String path;

    public Request() {
    }

    public Request(List<JavaNode> javaNodes) {
        this.javaNodes = javaNodes;
    }

    public Request(JavaNode rootNode,
                   List<Dependency> allDependencies,
                   List javaNodes,
                   List xmlNodes,
                   List jspNodes) {
        this.rootNode = rootNode;
        this.allDependencies = allDependencies;
        this.javaNodes = javaNodes;
        this.xmlNodes = xmlNodes;
        this.jspNodes = jspNodes;
    }

    public Request(JavaNode rootNode, List<Dependency> allDependencies, List javaNodes, List xmlNodes, List jspNodes, String path) {
        this.rootNode = rootNode;
        this.allDependencies = allDependencies;
        this.javaNodes = javaNodes;
        this.xmlNodes = xmlNodes;
        this.jspNodes = jspNodes;
        this.path = path;
    }

    public Request(JavaNode rootNode, List<Dependency> allDependencies, List javaNodes, List xmlNodes, List jspNodes, List propertiesNodes, String path) {
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

    public List<Dependency> getAllDependencies() {
        return allDependencies;
    }

    public void setAllDependencies(List<Dependency> allDependencies) {
        this.allDependencies = allDependencies;
    }

    public List<JavaNode> getJavaNodes() {
        return javaNodes;
    }

    public void setJavaNodes(List<JavaNode> javaNodes) {
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
