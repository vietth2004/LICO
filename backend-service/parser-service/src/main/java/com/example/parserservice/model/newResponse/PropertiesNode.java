package com.example.parserservice.model.newResponse;

import com.example.parserservice.model.Response;

import com.example.parserservice.dom.Node;
import java.util.List;

public class PropertiesNode {
    public static void convertPropertiesNode(Response newResponse) {
        List<Node> propertiesNodes = newResponse.getPropertiesNodes();

        if (propertiesNodes != null) {
            for (Node propertiesNode : propertiesNodes) {
                if (propertiesNode != null) {
                    propertiesNode.setType("PropertiesNode");
                }
            }
        }
    }
}
