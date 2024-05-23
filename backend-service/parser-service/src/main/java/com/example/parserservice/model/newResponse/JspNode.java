package com.example.parserservice.model.newResponse;

import com.example.parserservice.dom.Node;
import com.example.parserservice.model.Response;

import java.util.LinkedHashMap;
import java.util.List;

public class JspNode {
    public static void convertJspNodes(Response newResponse) {
        List<Node> jspNodes = newResponse.getJspNodes();

        if (jspNodes != null) {
            for (Node jspNode : jspNodes) {
                if (jspNode != null) {
                    jspNode.setType("JspNode");
                }
            }
        }
    }
}
