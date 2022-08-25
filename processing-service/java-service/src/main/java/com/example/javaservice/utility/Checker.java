package com.example.javaservice.utility;

import com.example.javaservice.ast.dependency.DependencyCountTable;
import com.example.javaservice.ast.dependency.Pair;
import com.example.javaservice.ast.node.JavaNode;

public class Checker {

    public static void changeDependencyType(JavaNode javaNode) {
        if(javaNode.getEntityClass().equals("JavaInterfaceNode")) {
            for(Pair dependencies : javaNode.getDependencyFrom()) {
                if(dependencies.getDependency().getINHERITANCE() > 0 &&
                        !(dependencies.getNode().getEntityClass().equals("JavaClassNode") ||
                                dependencies.getNode().getEntityClass().equals("JavaInterfaceNode"))) {
//                    System.out.println(javaNode.getQualifiedName());
                    dependencies.setDependency(new DependencyCountTable(1,0,0,0,0));
                }
            }
        } else {
            for(Pair dependencies : javaNode.getDependencyTo()) {
                if(dependencies.getNode().getEntityClass().equals("JavaInterfaceNode")
                        && dependencies.getDependency().getINHERITANCE() > 0 &&
                        !javaNode.getEntityClass().equals("JavaClassNode")) {
//                    System.out.println(javaNode.getQualifiedName());
                    dependencies.setDependency(new DependencyCountTable(1,0,0,0,0));
                }
            }
        }

        for (Object children : javaNode.getChildren()) {
            if (children instanceof JavaNode) {
                changeDependencyType((JavaNode) children);
            }
        }
    }
}
