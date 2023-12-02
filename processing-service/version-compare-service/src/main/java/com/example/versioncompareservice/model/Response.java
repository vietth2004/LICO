package com.example.versioncompareservice.model;

import com.example.versioncompareservice.ast.node.JavaNode;

import java.util.List;
import java.util.Set;

public class Response {

    private List changedNodes;

    private List xmlChangedNodes;

    private List deletedNodes;

    private List xmlDeletedNodes;

    private List addedNodes;

    private List xmlAddedNodes;

    private List dependencies;

    private Set impactedNodes;

    private JavaNode rootNode;

    public Response() {
    }

    public Response(List changedNodes, List deletedNodes, List addedNodes, List xmlChangedNodes, List xmlDeletedNodes, List xmlAddedNodes) {
        this.changedNodes = changedNodes;
        this.deletedNodes = deletedNodes;
        this.addedNodes = addedNodes;
        this.xmlChangedNodes = xmlChangedNodes;
        this.xmlDeletedNodes = xmlDeletedNodes;
        this.xmlAddedNodes = xmlAddedNodes;
    }

    public Response(List changedNodes, List deletedNodes, List addedNodes, List xmlChangedNodes, List xmlDeletedNodes, List xmlAddedNodes, JavaNode rootNode) {
        this.changedNodes = changedNodes;
        this.deletedNodes = deletedNodes;
        this.addedNodes = addedNodes;
        this.rootNode = rootNode;
        this.xmlChangedNodes = xmlChangedNodes;
        this.xmlDeletedNodes = xmlDeletedNodes;
        this.xmlAddedNodes = xmlAddedNodes;
    }

    public Response(List changedNodes, List deletedNodes, List addedNodes, List xmlChangedNodes, List xmlDeletedNodes, List xmlAddedNodes, List dependencies, JavaNode rootNode) {
        this.changedNodes = changedNodes;
        this.deletedNodes = deletedNodes;
        this.addedNodes = addedNodes;
        this.dependencies = dependencies;
        this.rootNode = rootNode;
        this.xmlChangedNodes = xmlChangedNodes;
        this.xmlDeletedNodes = xmlDeletedNodes;
        this.xmlAddedNodes = xmlAddedNodes;
    }

    public Response(List changedNodes, List deletedNodes, List addedNodes, List xmlChangedNodes, List xmlDeletedNodes, List xmlAddedNodes, List dependencies, Set impactedNodes, JavaNode rootNode) {
        this.changedNodes = changedNodes;
        this.deletedNodes = deletedNodes;
        this.addedNodes = addedNodes;
        this.dependencies = dependencies;
        this.impactedNodes = impactedNodes;
        this.rootNode = rootNode;
        this.xmlChangedNodes = xmlChangedNodes;
        this.xmlDeletedNodes = xmlDeletedNodes;
        this.xmlAddedNodes = xmlAddedNodes;

    }

    public List getChangedNodes() {
        return changedNodes;
    }

    public void setChangedNodes(List changedNodes) {
        this.changedNodes = changedNodes;
    }

    public List getDeletedNodes() {
        return deletedNodes;
    }

    public void setDeletedNodes(List deletedNodes) {
        this.deletedNodes = deletedNodes;
    }

    public List getAddedNodes() {
        return addedNodes;
    }

    public void setAddedNodes(List addedNodes) {
        this.addedNodes = addedNodes;
    }

    public JavaNode getRootNode() {
        return rootNode;
    }

    public void setRootNode(JavaNode rootNode) {
        this.rootNode = rootNode;
    }

    public List getDependencies() {
        return dependencies;
    }

    public void setDependencies(List dependencies) {
        this.dependencies = dependencies;
    }

    public Set getImpactedNodes() {
        return impactedNodes;
    }

    public void setImpactedNodes(Set impactedNodes) {
        this.impactedNodes = impactedNodes;
    }
}
