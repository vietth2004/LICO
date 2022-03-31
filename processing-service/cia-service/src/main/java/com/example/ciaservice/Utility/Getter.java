package com.example.ciaservice.Utility;

import com.example.ciaservice.ast.JavaNode;
import com.example.ciaservice.ast.Node;
import com.example.ciaservice.ast.Pair;

import java.util.List;
import java.util.Set;

public class Getter {

    public static Set<Node> gatherImpactFromDependencies(List<Node> nodes,
                                                         List<JavaNode> javaNodes,
                                                         Integer totalNodes,
                                                         JavaNode changedNode,
                                                         Set<Node> affectedNodes,
                                                         Integer depth) {
//        System.out.println(depth);
        System.out.println();

        if(depth > 0) {

            for(Pair dependency : changedNode.getDependencyFrom()) {
                if(dependency.getDependency().getMEMBER() > -1) {
                    System.out.println("Caused impact: " + changedNode.getId());
                    System.out.println("-> " + dependency.getNode().getId() + " - depth: " + depth);
                    gatherImpact(nodes, javaNodes, totalNodes, affectedNodes, dependency, depth - 1);
                }
            }

            for(Pair dependency : changedNode.getDependencyTo()) {
                if(dependency.getDependency().getOVERRIDE() > 0) {
                    System.out.println("Caused impact: " + changedNode.getId());
                    System.out.println("-> " + dependency.getNode().getId() + " - depth: " + depth);
                    gatherImpact(nodes, javaNodes, totalNodes, affectedNodes, dependency, depth - 1);
                }
            }
        }

        return affectedNodes;
    }

    private static void gatherImpact(List<Node> nodes,
                                     List<JavaNode> javaNodes,
                                     Integer totalNodes,
                                     Set<Node> affectedNodes,
                                     Pair dependency,
                                     Integer depth) {
        Integer nodeId = dependency.getNode().getId();
        JavaNode javaNode = Searcher.findJavaNode(javaNodes, nodeId);
        Node node = Searcher.findNode(nodes, nodeId);
        if(!affectedNodes.contains(node) || depth > 0){
            affectedNodes.add(Searcher.findNode(nodes, dependency.getNode().getId()));
            affectedNodes.addAll(gatherImpactFromDependencies(nodes, javaNodes, totalNodes, javaNode, affectedNodes, depth));
        }
    }

    private static void gatherFather(List<Node> nodes,
                                     List<JavaNode> javaNodes,
                                     Integer totalNodes,
                                     Set<Node> affectedNodes,
                                     Pair dependency) {

    }
}
