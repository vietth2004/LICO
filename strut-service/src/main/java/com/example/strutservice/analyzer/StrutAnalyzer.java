package com.example.strutservice.analyzer;

import com.example.strutservice.ast.dependency.Dependency;
import com.example.strutservice.ast.node.JavaNode;
import com.example.strutservice.dom.Node;

import java.util.ArrayList;
import java.util.List;

public class StrutAnalyzer {

    public static List<Dependency> actionDependencyAnalyzer(List<JavaNode> javaNodes, List<Node> strutsNodes) {
        List<Dependency> strutActionDeps = new ArrayList<>();

        return strutActionDeps;
    }

    public static List<Dependency> strutsDependencyAnalyzer(List<JavaNode> javaNodes, List<Node> strutsNodes) {
        List<Dependency> strutDeps = new ArrayList<>();

        return strutDeps;
    }

    public static List<Dependency> strutInterceptorDepsAnalyzer(List<JavaNode> javaNodes, List<Node> strutsNodes) {
        List<Dependency> strutInterceptorDeps = new ArrayList<>();

        return strutInterceptorDeps;
    }

    public static List<Dependency> strutInterceptorStackDepsAnalyzer(List<JavaNode> javaNodes, List<Node> strutsNodes) {
        List<Dependency> strutInterceptorStackDeps = new ArrayList<>();

        return strutInterceptorStackDeps;
    }

    public static List<Dependency> strutJspDepsAnalyzer(List<JavaNode> javaNodes, List<Node> strutsNodes) {
        List<Dependency> strutJspDeps = new ArrayList<>();

        return strutJspDeps;
    }

    public static List<Dependency> strutPackageDepsAnalyzer(List<JavaNode> javaNodes, List<Node> strutsNodes) {
        List<Dependency> strutPackageDeps = new ArrayList<>();

        return strutPackageDeps;
    }

    public static List<Dependency> strutResultDepsAnalyzer(List<JavaNode> javaNodes, List<Node> strutsNodes) {
        List<Dependency> strutResultDeps = new ArrayList<>();

        return strutResultDeps;
    }

    public static List<Dependency> strutResultTypeDepsAnalyzer(List<JavaNode> javaNodes, List<Node> strutsNodes) {
        List<Dependency> strutResultTypeDeps = new ArrayList<>();

        return strutResultTypeDeps;
    }
}
