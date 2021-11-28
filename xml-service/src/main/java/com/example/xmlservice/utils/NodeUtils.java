package com.example.xmlservice.utils;

import com.example.xmlservice.ast.node.JavaNode;
import com.example.xmlservice.dom.Node;

import java.util.ArrayList;
import java.util.List;

public class NodeUtils {

    public static void reCalculateXmlNodesId(int javaTotalNodesId, List<Node> nodes){
        nodes.forEach(node -> {
            int id = node.getId();
            node.setId(id += javaTotalNodesId);
            if(node.getChildren().size() > 0) {
                reCalculateXmlNodesId(javaTotalNodesId, node.getChildren());
            }
        });
    }

    public static List<JavaNode> flatRootNode(JavaNode rootNode) {
        List<JavaNode> nodes = new ArrayList<>();
        nodes.add(rootNode);

        if(rootNode.getChildren().size() > 0) {
            for(Object child : rootNode.getChildren()) {
                if(child instanceof JavaNode) {
                    nodes.addAll(flatRootNode((JavaNode) child));
                }
            }
        }

        return nodes;
    }

}
