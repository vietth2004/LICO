package com.example.parserservice.util.worker;

import com.example.parserservice.ast.dependency.Dependency;
import com.example.parserservice.ast.node.JavaNode;

import java.util.List;

public class Wrapper {

    public static List<Dependency> wrapDependency (List<Dependency> dependencies, List<Dependency> frameworkDependencies, String type) {

        for(Dependency dependency : frameworkDependencies) {
            for(Dependency base : dependencies) {
                if(Checker.isDependency(base, dependency)) {
                    if(type.equals("SPRING")){
                        base.getType().setSPRING(dependency.getType().getSPRING());
                    }
                }
            }
        }

        return dependencies;
    }


    public static void wrapRootNode(JavaNode javaNode, List<Dependency> dependencies) {
        javaNode.setDependencyTo(Getter.getDependencyTo(javaNode, javaNode.getDependencyTo(), dependencies));
        javaNode.setDependencyFrom(Getter.getDependencyFrom(javaNode, javaNode.getDependencyFrom(), dependencies));

        for (Object childNode : javaNode.getChildren()) {
            if(childNode instanceof JavaNode){
                wrapRootNode((JavaNode) childNode, dependencies);
            }
        }
    }
}
