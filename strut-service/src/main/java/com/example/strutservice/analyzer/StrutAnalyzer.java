package com.example.strutservice.analyzer;

import com.example.strutservice.ast.dependency.Dependency;
import com.example.strutservice.ast.node.JavaNode;
import com.example.strutservice.ast.node.StrutsNode;
import com.example.strutservice.dom.Jsp.StrutsInterceptor;
import com.example.strutservice.dom.Node;

import java.util.ArrayList;
import java.util.List;

public class StrutAnalyzer {

    public static List<Dependency> actionDependencyAnalyzer(List<JavaNode> javaNodes, List<Node> strutsNodes) {
        List<Dependency> strutActionDeps = new ArrayList<>();

        for(Node node : strutsNodes) {

        }

        return strutActionDeps;
    }

    public static List<Dependency> strutsDependencyAnalyzer(List<JavaNode> javaNodes, List<Node> strutsNodes) {
        List<Dependency> strutDeps = new ArrayList<>();

        for(Node node : strutsNodes) {

        }

        return strutDeps;
    }

    public static List<Dependency> strutInterceptorDepsAnalyzer(List<JavaNode> javaNodes, List<Node> strutsNodes) {
        List<Dependency> strutInterceptorDeps = new ArrayList<>();

        for(Node node : strutsNodes) {

        }

        return strutInterceptorDeps;
    }

    public static List<Dependency> strutInterceptorStackDepsAnalyzer(List<JavaNode> javaNodes, List<Node> strutsNodes) {
        List<Dependency> strutInterceptorStackDeps = new ArrayList<>();

        for(Node node : strutsNodes) {

        }

        return strutInterceptorStackDeps;
    }

    public static List<Dependency> strutJspDepsAnalyzer(List<JavaNode> javaNodes, List<Node> strutsNodes) {
        List<Dependency> strutJspDeps = new ArrayList<>();

        for(Node node : strutsNodes) {

        }

        return strutJspDeps;
    }

    public static List<Dependency> strutPackageDepsAnalyzer(List<JavaNode> javaNodes, List<Node> strutsNodes) {
        List<Dependency> strutPackageDeps = new ArrayList<>();

        for(Node node : strutsNodes) {

        }

        return strutPackageDeps;
    }

    public static List<Dependency> strutResultDepsAnalyzer(List<JavaNode> javaNodes, List<Node> strutsNodes) {
        List<Dependency> strutResultDeps = new ArrayList<>();

        for(Node node : strutsNodes) {

        }

        return strutResultDeps;
    }

    public static List<Dependency> strutResultTypeDepsAnalyzer(List<JavaNode> javaNodes, List<Node> strutsNodes) {
        List<Dependency> strutResultTypeDeps = new ArrayList<>();

        for(Node node : strutsNodes) {

        }

        return strutResultTypeDeps;
    }
}
