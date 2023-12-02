package com.example.springservice.utils.worker;

import com.example.springservice.ast.dependency.Pair;
import com.example.springservice.ast.node.JavaNode;

import java.util.ArrayList;
import java.util.List;

public class Getter {

    public static Integer getInheritanceNode(List<Pair> javaNodes) {
        Integer id = -1;

        for (Pair obj : javaNodes) {
            if (obj.getDependency().getINHERITANCE() > 0) {
                return obj.getNode().getId();
            }
        }

        return id;
    }


    public static List<JavaNode> getSpringChildren(List<Integer> childNodes, List<JavaNode> javaNodes) {
        List springNodes = new ArrayList();

        for (JavaNode javaNode : javaNodes) {
            if (childNodes.contains(javaNode.getId())) {
                springNodes.add(javaNode);
            }
        }

        return springNodes;
    }
}
