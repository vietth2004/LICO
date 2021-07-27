package com.example.parserservice.model;

import com.example.parserservice.ast.node.NodeWeight;

import java.util.ArrayList;
import java.util.List;

public class CiaResponse {

    private List<NodeWeight> nodes = new ArrayList<>();

    public CiaResponse() {
    }

    public List<NodeWeight> getNodes() {
        return nodes;
    }

    public void setNodes(List<NodeWeight> nodes) {
        this.nodes = nodes;
    }
}
