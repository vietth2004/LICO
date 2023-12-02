package com.example.strutsservice.utils;

import com.example.strutsservice.ast.node.Node;
import com.example.strutsservice.ast.node.StrutsNode;

import java.util.ArrayList;
import java.util.List;

public class Converter {

    public static List<Node> convertStrutsNodesToNodes(List<com.example.strutsservice.dom.Node> strutsNodes) {
        List<Node> rootNode = new ArrayList<>();

        for (com.example.strutsservice.dom.Node tempNode : strutsNodes) {
            rootNode.add(convertStrutsNodeToNode(tempNode));
        }

        return rootNode;
    }

    public static Node convertStrutsNodeToNode(com.example.strutsservice.dom.Node strutsNode) {
        StrutsNode node = new StrutsNode();

        node.setId(strutsNode.getId());
        node.setIdClass("StrutsNode");
        node.setEntityClass("StrutsJspNode");
        node.setSimpleName(strutsNode.getName());
        node.setQualifiedName(strutsNode.getName());
        node.setUniqueName(strutsNode.getName());

        for (com.example.strutsservice.dom.Node tempNode : strutsNode.getNodeChildren()) {
            node.addChildren(convertStrutsNodeToNode(tempNode));
        }

        return node;
    }
}
