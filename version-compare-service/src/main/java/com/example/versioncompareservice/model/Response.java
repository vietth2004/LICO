package com.example.versioncompareservice.model;

import java.util.List;

public class Response {

    private List changedNodes;

    private List deletedNodes;

    private List addedNodes;

    public Response() {
    }

    public Response(List changedNodes, List deletedNodes, List addedNodes) {
        this.changedNodes = changedNodes;
        this.deletedNodes = deletedNodes;
        this.addedNodes = addedNodes;
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
}
