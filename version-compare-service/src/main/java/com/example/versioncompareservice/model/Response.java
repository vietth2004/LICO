package com.example.versioncompareservice.model;

import com.example.versioncompareservice.ast.node.JavaNode;

import java.util.List;

public class Response {

    private List changedNodes;

    private List deletedNodes;

    private List addedNodes;

    private JavaNode rootNode;

    public Response() {
    }

    public Response(List changedNodes, List deletedNodes, List addedNodes) {
        this.changedNodes = changedNodes;
        this.deletedNodes = deletedNodes;
        this.addedNodes = addedNodes;
    }

    public Response(List changedNodes, List deletedNodes, List addedNodes, JavaNode rootNode) {
        this.changedNodes = changedNodes;
        this.deletedNodes = deletedNodes;
        this.addedNodes = addedNodes;
        this.rootNode = rootNode;
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
}
