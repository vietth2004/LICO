package com.example.strutservice.utils;

import com.example.strutservice.ast.node.Node;
import com.example.strutservice.ast.node.StrutsNode;

import java.util.ArrayList;
import java.util.List;

public class Converter {

    public static List<Node> convertStrutsNodesToNodes (List<com.example.strutservice.dom.Node> strutsNodes) {
        List<Node> rootNode = new ArrayList<>();

        for(com.example.strutservice.dom.Node tempNode : strutsNodes) {
            rootNode.add(convertStrutsNodeToNode(tempNode));
        }

        return rootNode;
    }

    public static Node convertStrutsNodeToNode (com.example.strutservice.dom.Node strutsNode) {
        StrutsNode node = new StrutsNode();

        node.setId(strutsNode.getId());
        node.setIdClass("StrutsNode");
        node.setEntityClass("StrutsJspNode");
        node.setSimpleName(strutsNode.getName());
        node.setQualifiedName(strutsNode.getName());
        node.setUniqueName(strutsNode.getName());

        for(com.example.strutservice.dom.Node tempNode : strutsNode.getChildren()) {
            node.addChildren(convertStrutsNodeToNode(tempNode));
        }

        return node;
    }
}
