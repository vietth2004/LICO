package com.example.parserservice.model;

import com.example.parserservice.dom.Node;

import java.util.List;

public class FrameworkRequest {

    private List javaNodes;

    private List<Node> xmlNodes;

    private List<Node> jspNodes;

    public FrameworkRequest() {
    }

    public FrameworkRequest(List javaNodes, List<Node> xmlNodes, List<Node> jspNodes) {
        this.javaNodes = javaNodes;
        this.xmlNodes = xmlNodes;
        this.jspNodes = jspNodes;
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
