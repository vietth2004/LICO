package com.example.springservice.ast.utility;
import com.example.springservice.ast.annotation.JavaAnnotation;
import com.example.springservice.ast.dependency.DependencyCountTable;
import com.example.springservice.ast.dependency.Pair;
import com.example.springservice.ast.node.Node;
import com.example.springservice.ast.type.JavaType;
import mrmathami.annotations.Nonnull;
import mrmathami.cia.java.jdt.tree.annotate.Annotate;
import mrmathami.cia.java.jdt.tree.node.AbstractNode;
import mrmathami.cia.java.jdt.tree.type.AbstractType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Utility {

    public static void printList (List list, String request) {
        System.out.println(request);

        for(Object obj : list) {
            if(obj instanceof Node) {
                System.out.println(((Node) obj).getQualifiedName());
            }
            if(obj instanceof Pair) {
                System.out.println(((Pair) obj).getNode().getQualifiedName());
            }
        }

        System.out.println();
    }

    @Nonnull
    public static List<Integer> convertChildren(List<AbstractNode> abstractNodeList) {
        List<Integer> javaNodeList = new ArrayList<>();
        for(AbstractNode node : abstractNodeList) {
            javaNodeList.add(node.getId());
        }

        return javaNodeList;
    }

    @Nonnull
    public static List convertMap(Map<AbstractNode, mrmathami.cia.java.jdt.tree.dependency.DependencyCountTable> nodeList) {
        List<Pair> javaNodeList = new ArrayList<>();
        for(AbstractNode node : nodeList.keySet()) {
            DependencyCountTable dependencyCountTable = new DependencyCountTable(nodeList.get(node));
            javaNodeList.add(new Pair(new Node(node), dependencyCountTable));
        }
        return javaNodeList;
    }

    public static List<JavaAnnotation> convertAnnotates(List<Annotate> annotates) {
        List<JavaAnnotation> javaAnnotationList = new ArrayList<>();

        for(Annotate annotate : annotates) {
            javaAnnotationList.add(new JavaAnnotation(annotate));
        }
        return javaAnnotationList;
    }

    public static List<JavaType> convertParameters(List<AbstractType> parameters) {
        List<JavaType> javaTypeList = new ArrayList<>();

        for(AbstractType parameter : parameters) {
            javaTypeList.add(new JavaType(parameter));
        }

        return javaTypeList;
    }

    public static List<JavaType> convertArguments(List<AbstractType> argumentList) {
        List<JavaType> arguments = new ArrayList<>();

        for(AbstractType abstractType : argumentList) {
            arguments.add(new JavaType(abstractType));
        }

        return arguments;
    }
}
