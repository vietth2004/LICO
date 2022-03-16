package com.example.strutsservice.utils.Helper;

import com.example.strutsservice.dom.FileNode;
import com.example.strutsservice.dom.Node;
import com.example.strutsservice.dom.Xml.XmlTagNode;

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
