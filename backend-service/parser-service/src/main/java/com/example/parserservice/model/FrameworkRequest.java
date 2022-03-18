package com.example.parserservice.model;

import com.example.parserservice.dom.Node;
import com.example.parserservice.dom.Properties.PropertiesFileNode;

import java.util.List;

public class FrameworkRequest {

    private List javaNodes;

    private List<Node> xmlNodes;

    private List<Node> jspNodes;

    private List<PropertiesFileNode> propertiesFileNodes;

    public FrameworkRequest() {
    }

    public FrameworkRequest(List javaNodes, List<Node> xmlNodes, List<Node> jspNodes) {
        this.javaNodes = javaNodes;
        this.xmlNodes = xmlNodes;
        this.jspNodes = jspNodes;
    }

    public FrameworkRequest(List javaNodes, List<Node> xmlNodes, List<Node> jspNodes, List<PropertiesFileNode> propertiesFileNodes) {
        this.javaNodes = javaNodes;
        this.xmlNodes = xmlNodes;
        this.jspNodes = jspNodes;
        this.propertiesFileNodes = propertiesFileNodes;
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

    public List<PropertiesFileNode> getPropertiesFileNodes() {
        return propertiesFileNodes;
    }

    public void setPropertiesFileNodes(List<PropertiesFileNode> propertiesFileNodes) {
        this.propertiesFileNodes = propertiesFileNodes;
    }
}
