package com.example.ciaservice.model;

import com.example.ciaservice.ast.Node;

import java.util.List;

public class Response {

    List<Node> nodes;

    public Response() {
    }

    public Response(List<Node> nodes) {
        this.nodes = nodes;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }
}
