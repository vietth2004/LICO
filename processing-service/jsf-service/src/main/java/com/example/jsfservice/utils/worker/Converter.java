package com.example.jsfservice.utils.worker;

import com.example.jsfservice.dom.Node;
import com.example.jsfservice.dom.Xml.XmlFileNode;
import com.example.jsfservice.dom.Xml.XmlTagNode;

import java.util.ArrayList;
import java.util.List;

public class Converter {

    public static List<Node> convertMapToList(List xmlMapNodes) {
        List<Node> xmlNodes = new ArrayList<>();

        for(Object obj : xmlMapNodes) {
            if(obj instanceof Node) {
                Node xmlNode = (Node) obj;
                xmlNode.setNodeChildren(convertChildren(xmlNode.getNodeChildren()));
            }
        }

        return xmlNodes;
    }

    public static List<Node> convertChildren(List children) {
        List<Node> xmlTagNodes = new ArrayList<>();
        for(Object obj : children) {
            if(obj instanceof XmlTagNode) {
                xmlTagNodes.add((XmlTagNode) obj);
                ((XmlTagNode) obj).setNodeChildren(convertChildren(((XmlTagNode) obj).getNodeChildren()));
            }
        }

        return xmlTagNodes;
    }
}
