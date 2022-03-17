package com.example.parserservice.util.helper;

import com.example.parserservice.dom.FileNode;
import com.example.parserservice.dom.Node;
import com.example.parserservice.dom.Xml.XmlTagNode;

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
}
