package com.example.parserservice.ast.utility;

import com.example.parserservice.ast.node.JavaNode;
import mrmathami.cia.java.jdt.tree.node.AbstractNode;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Utils {


    public static void printList (List list) {

        for(Object obj : list) {
            if(obj instanceof JavaNode) {
                System.out.println(((JavaNode) obj).getQualifiedName());
            }
        }
    }


    public static void findDependency(mrmathami.cia.java.tree.node.JavaNode javaRootNode) {

        printDependency(javaRootNode.getDependencyTo());

        for(mrmathami.cia.java.tree.node.JavaNode javaNode : javaRootNode.getChildren())
        {
            findDependency(javaNode);
        }
    }

    private static void printDependency(Map Dependencies) {
        Set<AbstractNode> nodes = Dependencies.keySet();


        for(AbstractNode node : nodes) {
            mrmathami.cia.java.jdt.tree.dependency.DependencyCountTable dependencyCountTable = (mrmathami.cia.java.jdt.tree.dependency.DependencyCountTable) Dependencies.get(node);
//            System.out.println(dependencyCountTable.getCount(JavaDependency.USE));
        }
    }
}
