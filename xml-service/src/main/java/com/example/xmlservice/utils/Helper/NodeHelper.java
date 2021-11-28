package com.example.xmlservice.utils.Helper;

import com.example.xmlservice.dom.FileNode;
import com.example.xmlservice.dom.Node;
import com.example.xmlservice.dom.Xml.XmlTagNode;

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
