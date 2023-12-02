package com.example.springservice.utils.worker;

import com.example.springservice.ast.node.JavaNode;
import com.example.springservice.dependency.Dependency;
import com.example.springservice.resource.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Converter {

    public static List<JavaNode> convertSpringJavaNodes(List<JavaNode> javaNodes) {

        List<JavaNode> springJavaNodes = new ArrayList<>();
        for (JavaNode javaNode : javaNodes) {
            if (Checker.containSpringAnnotations(javaNode, Resource.SPRING_ANNOTATION_SIMPLE_NAME)) {
                springJavaNodes.add(javaNode);
            }
            if (Checker.isSpringInterface(javaNode, Resource.SPRING_REPOSITORY_INTERFACE_SIMPLE_NAME)) {
                springJavaNodes.add(javaNode);
            }
        }
        return springJavaNodes;
    }

    public static List<Dependency> convertSetToList(Set<Dependency> dependencySet) {
        List<Dependency> dependencies = new ArrayList<>();

        for (Dependency dependency : dependencySet) {
            dependencies.add(dependency);
        }

        return dependencies;

    }
}
