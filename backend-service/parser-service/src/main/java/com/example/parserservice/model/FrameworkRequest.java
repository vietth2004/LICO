package com.example.parserservice.model;

import java.util.List;

public class FrameworkRequest {

    private List javaNodes;

    private List xmlNodes;

    private List jspNodes;

    private List propertiesNodes;

    private String path;

    public FrameworkRequest() {
    }

    public FrameworkRequest(List javaNodes) {
        this.javaNodes = javaNodes;
    }

//    public FrameworkRequest(List xmlNodes) {
//        this.xmlNodes = xmlNodes;
//    }

//    public FrameworkRequest(List jspNodes) {
//        this.jspNodes = jspNodes;
//    }


    public FrameworkRequest(List xmlNodes, List jspNodes) {
        this.xmlNodes = xmlNodes;
        this.jspNodes = jspNodes;
    }

    public FrameworkRequest(List javaNodes, List xmlNodes, List jspNodes) {
        this.javaNodes = javaNodes;
        this.xmlNodes = xmlNodes;
        this.jspNodes = jspNodes;
    }

    public FrameworkRequest(List javaNodes, List xmlNodes, List jspNodes, List propertiesFileNodes) {
        this.javaNodes = javaNodes;
        this.xmlNodes = xmlNodes;
        this.jspNodes = jspNodes;
        this.propertiesNodes = propertiesFileNodes;
    }

    public List getJavaNodes() {
        return javaNodes;
    }

    public void setJavaNodes(List javaNodes) {
        this.javaNodes = javaNodes;
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

    public List getPropertiesNodes() {
        return propertiesNodes;
    }

    public void setPropertiesNodes(List propertiesNodes) {
        this.propertiesNodes = propertiesNodes;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
