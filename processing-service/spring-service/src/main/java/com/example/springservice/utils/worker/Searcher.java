package com.example.springservice.utils.worker;

import com.example.springservice.ast.node.JavaNode;
import com.example.springservice.dom.Node;

import java.util.List;

public class Searcher {

    public static JavaNode searchJavaNode(Integer id, List<JavaNode> javaNodes) {
        JavaNode javaNode = new JavaNode();


        for (JavaNode obj : javaNodes) {
            if(obj.getId().equals(id)) {
                javaNode = obj;
            }
        }


        return javaNode;
    }

    public static JavaNode searchJavaNode(String name, List<JavaNode> javaNodes) {
        JavaNode javaNode = new JavaNode();

        for (JavaNode obj : javaNodes) {
            if(obj.getQualifiedName().equals(name)) {
                javaNode = obj;
            }
        }

        return javaNode;
    }

    public static Node searchXmlNode(String name, List<Node> xmlNodes) {
        Node xmlNode = new Node();


        for (Node obj : xmlNodes) {
            if(obj.getName().equals(name)) {
                xmlNode = obj;
            }
        }


        return xmlNode;
    }
}
