package com.example.javaservice.utility;

import com.example.javaservice.ast.dependency.DependencyCountTable;
import com.example.javaservice.ast.dependency.Pair;
import com.example.javaservice.ast.node.JavaNode;

import java.util.Stack;

public class Checker {

    public static void changeDependencyType(JavaNode javaNode) {
        Stack<JavaNode> javaNodeStack = new Stack<>();
        javaNodeStack.push(javaNode);
        while (!javaNodeStack.isEmpty()) {
            JavaNode javaNodeNew = javaNodeStack.pop();
            if (javaNodeNew.getEntityClass().equals("JavaInterfaceNode")) {
                for (Pair dependencies : javaNodeNew.getDependencyFrom()) {
                    if (dependencies.getDependency().getINHERITANCE() > 0 &&
                            !(dependencies.getNode().getEntityClass().equals("JavaClassNode") ||
                                    dependencies.getNode().getEntityClass().equals("JavaInterfaceNode"))) {
//                    System.out.println(javaNode.getQualifiedName());
                        dependencies.setDependency(new DependencyCountTable(1, 0, 0, 0, 0));
                    }
                }
            } else {
                for (Pair dependencies : javaNodeNew.getDependencyTo()) {
                    if (dependencies.getNode().getEntityClass().equals("JavaInterfaceNode")
                            && dependencies.getDependency().getINHERITANCE() > 0 &&
                            !javaNodeNew.getEntityClass().equals("JavaClassNode")) {
//                    System.out.println(javaNode.getQualifiedName());
                        dependencies.setDependency(new DependencyCountTable(1, 0, 0, 0, 0));
                    }
                }
            }
            for (Object children : javaNodeNew.getChildren()) {
                if (children instanceof JavaNode) {
                    javaNodeStack.push((JavaNode) children);
                }
            }
        }
//
//        for (Object children : javaNode.getChildren()) {
//            if (children instanceof JavaNode) {
//                changeDependencyType((JavaNode) children);
//            }
//        }
    }
}
