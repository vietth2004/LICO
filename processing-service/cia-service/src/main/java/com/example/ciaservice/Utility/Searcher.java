package com.example.ciaservice.Utility;

import com.example.ciaservice.ast.JavaNode;
import com.example.ciaservice.ast.Node;

import java.util.List;

public class Searcher {

    public static JavaNode findJavaNode(List<JavaNode> javaNodes, Integer id) {
        JavaNode javaNode = new JavaNode();

        for (JavaNode obj : javaNodes) {
            if (obj.getId().equals(id)) {
                javaNode = obj;
            }
        }

        return javaNode;
    }

    public static Node findNode(List<Node> nodes, Integer id) {
        Node node = new Node();

        for (Node obj : nodes) {
            if (obj.getId().equals(id)) {
                node = obj;
            }
        }

        return node;
    }
}
