package com.example.versioncompareservice.utils.communicator;

import com.example.versioncompareservice.dom.Node;

import java.util.ArrayList;
import java.util.List;

public class Response {

    private List<Node> xmlNodes = new ArrayList<>();

    public Response() {
    }

    public Response(List<Node> nodes) {
        this.xmlNodes = nodes;
    }

    public List<Node> getXmlNodes() {
        return xmlNodes;
    }

    public void setXmlNodes(List<Node> xmlNodes) {
        this.xmlNodes = xmlNodes;
    }
}
