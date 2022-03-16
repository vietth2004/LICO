package com.example.parserservice.model;

import com.example.parserservice.ast.dependency.Dependency;
import com.example.parserservice.ast.node.JavaNode;

import java.util.List;


public class Response {

    private JavaNode rootNode;

    private List<Dependency> allDependencies;

    private List allNodes;

    private List nodesWeight;

    private Integer totalNodes;

    private String address = null;

    private List<com.example.parserservice.dom.Node> xmlNodes;

    public Response() {
    }

    public Response(JavaNode rootNode, Integer totalNodes) {
        this.rootNode = rootNode;
        this.totalNodes = totalNodes;
    }

    public Response(JavaNode rootNode, Integer totalNodes, List allNodes) {
        this.rootNode = rootNode;
        this.totalNodes = totalNodes;
        this.allNodes = allNodes;
    }

    public Response(JavaNode rootNode, Integer totalNodes , List allNodes, List<Dependency> allDependencies) {
        this.rootNode = rootNode;
        this.allDependencies = allDependencies;
        this.allNodes = allNodes;
        this.totalNodes = totalNodes;
    }

    public Response(JavaNode rootNode, Integer totalNodes , List allNodes, List<Dependency> allDependencies, String address) {
        this.rootNode = rootNode;
        this.allDependencies = allDependencies;
        this.allNodes = allNodes;
        this.totalNodes = totalNodes;
        this.address = address;
    }

    public Response(JavaNode rootNode, Integer totalNodes , List allNodes, List<Dependency> allDependencies, String address, List<com.example.parserservice.dom.Node> xmlNodes) {
        this.rootNode = rootNode;
        this.allDependencies = allDependencies;
        this.allNodes = allNodes;
        this.totalNodes = totalNodes;
        this.address = address;
        this.xmlNodes = xmlNodes;
    }

    public Response(JavaNode rootNode, List<Dependency> allDependencies, List allNodes, List nodesWeight, Integer totalNodes, String address, List<com.example.parserservice.dom.Node> xmlNodes) {
        this.rootNode = rootNode;
        this.allDependencies = allDependencies;
        this.allNodes = allNodes;
        this.nodesWeight = nodesWeight;
        this.totalNodes = totalNodes;
        this.address = address;
        this.xmlNodes = xmlNodes;
    }

    public List<Dependency> getAllDependencies() {
        return allDependencies;
    }

    public List getAllNodes() {
        return allNodes;
    }

    public List<com.example.parserservice.dom.Node> getXmlNodes() {
        return xmlNodes;
    }

    public void setXmlNodes(List<com.example.parserservice.dom.Node> xmlNodes) {
        this.xmlNodes = xmlNodes;
    }

    public Response(JavaNode rootNode, List<Dependency> allDependencies) {
        this.rootNode = rootNode;
        this.allDependencies = allDependencies;
    }

    public Response(JavaNode rootNode, List<Dependency> allDependencies, List allNodes) {
        this.rootNode = rootNode;
        this.allDependencies = allDependencies;
        this.allNodes = allNodes;
        this.totalNodes = allNodes.size();
    }

    public Response(JavaNode rootNode, List<Dependency> allDependencies, List allNodes, List nodesWeight) {
        this.rootNode = rootNode;
        this.allDependencies = allDependencies;
        this.allNodes = allNodes;
        this.nodesWeight = nodesWeight;
        this.totalNodes = allNodes.size();
    }

    public JavaNode getRootNode() {
        return rootNode;
    }

    public void setRootNode(JavaNode rootNode) {
        this.rootNode = rootNode;
    }

//    public List getAllDependencies() {
//        return allDependencies;
//    }

    public void setAllDependencies(List<Dependency> allDependencies) {
        this.allDependencies = allDependencies;
    }

//    public List getAllNodes() {
//        return allNodes;
//    }

    public void setAllNodes(List allNodes) {
        this.allNodes = allNodes;
    }

    public Integer getTotalNodes() {
        return totalNodes;
    }

    public void setTotalNodes(Integer totalNodes) {
        this.totalNodes = totalNodes;
    }

    public List getNodesWeight() {
        return nodesWeight;
    }

    public void setNodesWeight(List nodesWeight) {
        this.nodesWeight = nodesWeight;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
