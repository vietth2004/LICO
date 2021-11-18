package com.jcia.xmlservice.Utils.Helper;

import com.jcia.xmlservice.Dom.FileNode;
import com.jcia.xmlservice.Dom.Node;
import com.jcia.xmlservice.Dom.Xml.XmlTagNode;

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
