package com.example.xmlservice.utils.Helper;

import com.example.xmlservice.dom.FileNode;
import com.example.xmlservice.dom.Node;
import com.example.xmlservice.dom.Xml.XmlTagNode;

import java.util.List;

public class NodeHelper {
    public static Node getFileAndTableScopedNode(Node node) {
        if (node instanceof XmlTagNode) {
            node = getFileNode(node);
        }
        return node;
    }

    public static Node getFileNode(Node node) {
        if (node == null || node instanceof FileNode)
            return node;
        else return getFileNode(node.getParent());
    }

    public static Node replaceNode(Node oldNode, Node newNode) {
        Node parent = oldNode.getParent();
        if (parent == null) return oldNode;
        List<Node> children = parent.getChildren();
        int pos = children.indexOf(oldNode);
        children.set(pos, newNode);
        return newNode;
    }
}
