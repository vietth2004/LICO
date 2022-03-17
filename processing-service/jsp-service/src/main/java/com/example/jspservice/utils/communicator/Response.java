package com.example.jspservice.utils.communicator;

import com.example.jspservice.dom.Node;

import java.util.ArrayList;
import java.util.List;

public class Response {

    private List<Node> jspNodes = new ArrayList<>();

    public Response() {
    }

    public Response(List<Node> nodes) {
        this.jspNodes = nodes;
    }

    public List<Node> getJspNodes() {
        return jspNodes;
    }

    public void setJspNodes(List<Node> jspNodes) {
        this.jspNodes = jspNodes;
    }
}
