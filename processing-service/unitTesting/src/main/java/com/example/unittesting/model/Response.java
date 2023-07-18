package com.example.unittesting.model;

import com.example.unittesting.Node.Node;

public class Response {
    private Node rootNode;

    public Response() {
    }

    public Response(Node rootNode) {
        this.rootNode = rootNode;
    }

    public Node getRootNode() {
        return rootNode;
    }

    public void setRootNode(Node rootNode) {
        this.rootNode = rootNode;
    }

}
