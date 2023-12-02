package com.example.versioncompareservice.utils.Helper;

import com.example.versioncompareservice.dom.FileNode;
import com.example.versioncompareservice.dom.Node;
import com.example.versioncompareservice.dom.Xml.XmlTagNode;

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
