package com.example.parserservice.model.parser;

import com.example.parserservice.ast.dependency.Dependency;
import com.example.parserservice.ast.dependency.OrientedDependency;
import com.example.parserservice.ast.node.JavaNode;
import com.example.parserservice.dom.Jsp.JspTagNode;
import com.example.parserservice.dom.Node;
import com.example.parserservice.dom.Properties.PropertiesFileNode;
import com.example.parserservice.dom.Xml.XmlTagNode;

import java.io.Serializable;
import java.util.List;

public class Request implements Serializable {

    private JavaNode rootNode;

    private List<Dependency> allDependencies;

    private List javaNodes;

    private List<Node> xmlNodes;

    private List<Node> jspNodes;

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

    public Request(JavaNode rootNode
                    , List<Dependency> allDependencies
                    , List javaNodes
                    , List xmlNodes
                    , List jspNodes
                    , String path) {
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
