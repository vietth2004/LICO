package com.example.parserservice.model.newResponse;

import com.example.parserservice.dom.Node;
import com.example.parserservice.model.Response;

import java.util.List;

public class XmlNode {

    public static void convertXmlNodes(Response newResponse) {
        List<Node> xmlNodes = newResponse.getXmlNodes();
        if (xmlNodes != null) {
            for (Node xmlNode : xmlNodes) {
                if (xmlNode != null) {
                    xmlNode.setType("XmlNode");
                }
            }
        }
    }
}
