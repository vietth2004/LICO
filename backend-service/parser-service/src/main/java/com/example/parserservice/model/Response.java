package com.example.parserservice.model;

import com.example.parserservice.ast.dependency.Dependency;
import com.example.parserservice.ast.dependency.OrientedDependency;
import com.example.parserservice.ast.node.JavaNode;
import com.example.parserservice.dom.Node;

import java.util.List;


public class Response {

    private JavaNode rootNode;

    private List<Dependency> dependencies;

    private List<OrientedDependency> orientedDependencies;

    private List javaNodes;

    private List nodesWeight;

    private List jspNodes;

    private List propertiesNodes;

    private Integer totalNodes;

    private String address = null;

    private List xmlNodes;

    public Response() {
    }

    public Response(JavaNode rootNode, Integer totalNodes) {
        this.rootNode = rootNode;
        this.totalNodes = totalNodes;
    }

    public Response(JavaNode rootNode, Integer totalNodes, List allNodes) {
        this.rootNode = rootNode;
        this.totalNodes = totalNodes;
        this.javaNodes = allNodes;
    }

    public Response(JavaNode rootNode, Integer totalNodes, List allNodes, List<Dependency> allDependencies) {
        this.rootNode = rootNode;
        this.dependencies = allDependencies;
        this.javaNodes = allNodes;
        this.totalNodes = totalNodes;
    }

    public Response(JavaNode rootNode, Integer totalNodes, List allNodes, List<Dependency> allDependencies, String address) {
        this.rootNode = rootNode;
        this.dependencies = allDependencies;
        this.javaNodes = allNodes;
        this.totalNodes = totalNodes;
        this.address = address;
    }

    public Response(JavaNode rootNode
            , Integer totalNodes
            , List allNodes
            , List<Dependency> allDependencies
            , String address
            , List xmlNodes) {
        this.rootNode = rootNode;
        this.dependencies = allDependencies;
        this.javaNodes = allNodes;
        this.totalNodes = totalNodes;
        this.address = address;
        this.xmlNodes = xmlNodes;
    }

    public Response(JavaNode rootNode
            , Integer totalNodes
            , List allNodes
            , List<Dependency> allDependencies
            , String address
            , List xmlNodes
            , List jspNodes) {
        this.rootNode = rootNode;
        this.dependencies = allDependencies;
        this.javaNodes = allNodes;
        this.totalNodes = totalNodes;
        this.address = address;
        this.xmlNodes = xmlNodes;
        this.jspNodes = jspNodes;
    }

    public Response(JavaNode rootNode
            , List<Dependency> allDependencies
            , List allNodes
            , List nodesWeight
            , Integer totalNodes
            , String address
            , List xmlNodes) {
        this.rootNode = rootNode;
        this.dependencies = allDependencies;
        this.javaNodes = allNodes;
        this.nodesWeight = nodesWeight;
        this.totalNodes = totalNodes;
        this.address = address;
        this.xmlNodes = xmlNodes;
    }

    public Response(JavaNode rootNode, List<Dependency> allDependencies) {
        this.rootNode = rootNode;
        this.dependencies = allDependencies;
    }

    public Response(JavaNode rootNode, List<Dependency> allDependencies, List allNodes) {
        this.rootNode = rootNode;
        this.dependencies = allDependencies;
        this.javaNodes = allNodes;
        this.totalNodes = allNodes.size();
    }

    public Response(JavaNode rootNode, List<Dependency> allDependencies, List allNodes, List nodesWeight) {
        this.rootNode = rootNode;
        this.dependencies = allDependencies;
        this.javaNodes = allNodes;
        this.nodesWeight = nodesWeight;
        this.totalNodes = allNodes.size();
    }

    public Response(JavaNode rootNode,
                    List<Dependency> allDependencies,
                    List javaNodes,
                    List nodesWeight,
                    List jspNodes,
                    List propertiesNodes,
                    Integer totalNodes,
                    String address,
                    List<Node> xmlNodes) {
        this.rootNode = rootNode;
        this.dependencies = allDependencies;
        this.javaNodes = javaNodes;
        this.nodesWeight = nodesWeight;
        this.jspNodes = jspNodes;
        this.propertiesNodes = propertiesNodes;
        this.totalNodes = totalNodes;
        this.address = address;
        this.xmlNodes = xmlNodes;
    }

    public JavaNode getRootNode() {
        return rootNode;
    }

    public void setRootNode(JavaNode rootNode) {
        this.rootNode = rootNode;
    }

    public void setDependencies(List<Dependency> dependencies) {
        this.dependencies = dependencies;
    }

    public List<Dependency> getDependencies() {
        return dependencies;
    }

    public List getJavaNodes() {
        return javaNodes;
    }

    public List getXmlNodes() {
        return xmlNodes;
    }

    public void setXmlNodes(List xmlNodes) {
        this.xmlNodes = xmlNodes;
    }

    public void setJavaNodes(List javaNodes) {
        this.javaNodes = javaNodes;
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

    public List<OrientedDependency> getOrientedDependencies() {
        return orientedDependencies;
    }

    public void setOrientedDependencies(List<OrientedDependency> orientedDependencies) {
        this.orientedDependencies = orientedDependencies;
    }
}
