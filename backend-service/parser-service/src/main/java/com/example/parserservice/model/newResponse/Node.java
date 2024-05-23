package com.example.parserservice.model.newResponse;

import com.example.parserservice.model.Response;

import java.util.ArrayList;

public class Node {

    public static NewResponse convertToNewResponse(Response oldResponse) {
        Response tmpResponse = (Response) oldResponse.clone();

        JavaNode.convertJavaNodes(tmpResponse);
        XmlNode.convertXmlNodes(tmpResponse);
        JspNode.convertJspNodes(tmpResponse);
        PropertiesNode.convertPropertiesNode(tmpResponse);

        String address = tmpResponse.getAddress();
        ArrayList nodes = new ArrayList<>();
        nodes.addAll(tmpResponse.getJavaNodes());
        nodes.addAll(tmpResponse.getJspNodes());
        nodes.addAll(tmpResponse.getXmlNodes());
        nodes.addAll(tmpResponse.getPropertiesNodes());

        NewResponse newResponse = new NewResponse(address, nodes, oldResponse);

        return newResponse;
    }

}
