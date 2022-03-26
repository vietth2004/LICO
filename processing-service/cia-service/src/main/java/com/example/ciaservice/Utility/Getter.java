package com.example.ciaservice.Utility;

import com.example.ciaservice.ast.JavaNode;
import com.example.ciaservice.ast.Node;
import com.example.ciaservice.ast.Pair;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Getter {

    public static Set<Node> gatherImpactFromDependencies(List<Node> nodes, List<JavaNode> javaNodes, Integer totalNodes, JavaNode changedNode) {
        Set<Node> changedSet = new HashSet<>();

        for(Pair dependency : changedNode.getDependencyFrom()) {
            JavaNode javaNode = Searcher.findJavaNode(javaNodes, dependency.getNode().getId());
            changedSet.add(Searcher.findNode(nodes, dependency.getNode().getId()));

            changedSet.addAll(gatherImpactFromDependencies(nodes, javaNodes, totalNodes, javaNode));
        }

        return changedSet;
    }
}
