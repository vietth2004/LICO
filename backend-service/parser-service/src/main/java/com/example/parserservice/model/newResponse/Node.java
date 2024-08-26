package com.example.parserservice.model.newResponse;

import com.example.parserservice.dom.Properties.PropertiesFileNode;
import com.example.parserservice.model.Response;
import java.util.ArrayList;
import java.util.List;

public class Node {

    public static NewResponse convertToNewResponse(Response oldResponse) {
        Response tmpResponse = (Response) oldResponse.clone();

        ArrayList annotations = JavaNode.convertJavaNodes(tmpResponse);
        XmlNode.convertXmlNodes(tmpResponse);
        JspNode.convertJspNodes(tmpResponse);
        PropertiesNode.convertPropertiesNode(tmpResponse);

        String address = tmpResponse.getAddress();
        ArrayList nodes = new ArrayList<>();
        nodes.addAll(tmpResponse.getJavaNodes());
        nodes.addAll(tmpResponse.getJspNodes());
        nodes.addAll(tmpResponse.getXmlNodes());
        nodes.addAll(tmpResponse.getPropertiesNodes());

        // ADD PROJECT NODE
        ProjectNode projectNode = new ProjectNode();
        projectNode.addChildren(tmpResponse.getRootNode().getId());

        for(Object xmlNode : tmpResponse.getXmlNodes()) {
            if (xmlNode instanceof com.example.parserservice.dom.Node) {
                com.example.parserservice.dom.Node node = (com.example.parserservice.dom.Node) xmlNode;
                projectNode.addChildren(node.getId());
                break;
            }
        }

        for(Object jspNode : tmpResponse.getJspNodes()) {
            if (jspNode instanceof com.example.parserservice.dom.Node) {
                com.example.parserservice.dom.Node node = (com.example.parserservice.dom.Node) jspNode;
                projectNode.addChildren(node.getId());
                break;
            }
        }

        List propNodes = tmpResponse.getPropertiesNodes();
        for (int i = 0; i < propNodes.size(); i++) {
            if (i == 0) {
                if (propNodes.get(i) instanceof com.example.parserservice.dom.Node) {
                    com.example.parserservice.dom.Node node = (com.example.parserservice.dom.Node) propNodes.get(i);
                    projectNode.addChildren(node.getId());
                }
            }
            if (i == propNodes.size() - 1) {
                if (propNodes.get(i) instanceof PropertiesFileNode) {
                    PropertiesFileNode node = (PropertiesFileNode) propNodes.get(i);
                    List<com.example.parserservice.dom.Properties.PropertiesNode> propertiesNodes = node.getProperties();
                    com.example.parserservice.dom.Properties.PropertiesNode propertiesNode = propertiesNodes.get(propertiesNodes.size() - 1);
                    projectNode.setId(propertiesNode.getId() + 1);
                }
            }
        }

        nodes.add(projectNode);
        //================================================

        NewResponse newResponse = new NewResponse(address, nodes, oldResponse, projectNode.getId(), annotations);

        return newResponse;
    }

}
