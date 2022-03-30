package com.example.ciaservice.Utility;

import com.example.ciaservice.ast.JavaNode;
import com.example.ciaservice.ast.Node;
import com.example.ciaservice.ast.Pair;

import java.util.List;
import java.util.Set;

public class Getter {

    public static Set<Node> gatherImpactFromDependencies(List<Node> nodes, List<JavaNode> javaNodes, Integer totalNodes, JavaNode changedNode, Set<Node> affectedNodes) {

        for(Pair dependency : changedNode.getDependencyFrom()) {
            if(dependency.getDependency().getMEMBER() > -1) {
                gatherImpact(nodes, javaNodes, totalNodes, affectedNodes, dependency);
            }
        }

        for(Pair dependency : changedNode.getDependencyTo()) {
            if(dependency.getDependency().getOVERRIDE() > 0) {
                gatherImpact(nodes, javaNodes, totalNodes, affectedNodes, dependency);
            }
        }

        return affectedNodes;
    }

    private static void gatherImpact(List<Node> nodes,
                                     List<JavaNode> javaNodes,
                                     Integer totalNodes,
                                     Set<Node> affectedNodes,
                                     Pair dependency) {
        JavaNode javaNode = Searcher.findJavaNode(javaNodes, dependency.getNode().getId());
        Node node = Searcher.findNode(nodes, dependency.getNode().getId());
        if(!affectedNodes.contains(node)){
            System.out.println(node.getId());
            affectedNodes.add(Searcher.findNode(nodes, dependency.getNode().getId()));
            affectedNodes.addAll(gatherImpactFromDependencies(nodes, javaNodes, totalNodes, javaNode, affectedNodes));
        }
    }

    private static void gatherFather(List<Node> nodes,
                                     List<JavaNode> javaNodes,
                                     Integer totalNodes,
                                     Set<Node> affectedNodes,
                                     Pair dependency) {

    }
}
