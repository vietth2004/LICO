package com.example.gitservice.ast.utility;

import com.example.gitservice.ast.annotation.JavaAnnotation;
import com.example.gitservice.ast.dependency.DependencyCountTable;
import com.example.gitservice.ast.dependency.Pair;
import com.example.gitservice.ast.node.JavaNode;
import com.example.gitservice.ast.node.Node;
import com.example.gitservice.ast.type.JavaType;
import mrmathami.annotations.Nonnull;
import mrmathami.cia.java.jdt.project.tree.annotate.Annotate;
import mrmathami.cia.java.jdt.project.tree.node.AbstractNode;
import mrmathami.cia.java.jdt.project.tree.type.AbstractType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Utility {

    public static void printList (List list) {

        for(Object obj : list) {
            if(obj instanceof JavaNode) {
                System.out.println(((JavaNode) obj).getQualifiedName());
            }
        }
    }

    public static List<JavaNode> convertJavaNodeList (List nodeList) {
        List<JavaNode> nodes = new ArrayList<>();

        for(Object javaNode : nodeList) {
            if(javaNode instanceof mrmathami.cia.java.tree.node.JavaNode) {
                nodes.add(new JavaNode((AbstractNode) javaNode, true));
            }
        }

        return nodes;
    }

    public static List<JavaNode> convertJavaNodeSet (Set<mrmathami.cia.java.tree.node.JavaNode> nodeList) {
        List<JavaNode> nodes = new ArrayList<>();

        for(mrmathami.cia.java.tree.node.JavaNode javaNode : nodeList) {
            nodes.add(new JavaNode(javaNode));
        }

        return nodes;
    }

    public static List<JavaNode> convertJavaNodeSet (Set<mrmathami.cia.java.tree.node.JavaNode> nodeList, String status) {
        List<JavaNode> nodes = new ArrayList<>();

        for(mrmathami.cia.java.tree.node.JavaNode javaNode : nodeList) {
            nodes.add(new JavaNode(javaNode, status));
        }

        return nodes;
    }

    public static List<JavaNode> convertJavaNodePairSet (
            Set<mrmathami.utils.Pair<mrmathami.cia.java.tree.node.JavaNode, mrmathami.cia.java.tree.node.JavaNode>> nodeList,
            String status) {

        List<JavaNode> nodes = new ArrayList<>();

        for(mrmathami.utils.Pair<mrmathami.cia.java.tree.node.JavaNode, mrmathami.cia.java.tree.node.JavaNode> javaNode : nodeList) {
            nodes.add(new JavaNode(javaNode.getA(), status));
        }

        return nodes;
    }

    @Nonnull
    public static List<JavaNode> convertAbstractNode(List<AbstractNode> abstractNodeList, boolean getDependency) {
        List<JavaNode> javaNodeList = new ArrayList<>();
        for(AbstractNode node : abstractNodeList) {
            javaNodeList.add(new JavaNode(node, true, getDependency));
        }

        return javaNodeList;
    }

    @Nonnull
    public static List<JavaNode> convertToAllNodes(List<AbstractNode> abstractNodeList) {
        List<JavaNode> javaNodeList = new ArrayList<>();
        for(AbstractNode node : abstractNodeList) {
            javaNodeList.add(new JavaNode(node, false));
        }

        return javaNodeList;
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
    public static List<Node> convertNode(List<AbstractNode> abstractNodeList) {
        List<Node> children = new ArrayList<>();
        for(AbstractNode node : abstractNodeList) {
            children.add(new Node(node));
        }

        return children;
    }

    @Nonnull
    public static List convertMap(Map<AbstractNode, mrmathami.cia.java.jdt.project.tree.dependency.DependencyCountTable> nodeList) {
        List<Pair> javaNodeList = new ArrayList<>();
        for(AbstractNode node : nodeList.keySet()) {
            DependencyCountTable dependencyCountTable = new DependencyCountTable(nodeList.get(node));
            javaNodeList.add(new Pair(new Node(node), dependencyCountTable));
        }
        return javaNodeList;
    }

    @Nonnull
    public static List<String> convertModifiers(Set modifierSet) {
        List<String> modifierList = new ArrayList<>();

        for(Object obj : modifierSet) {
            modifierList.add(obj.toString());
        }

        return modifierList;
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
            mrmathami.cia.java.jdt.project.tree.dependency.DependencyCountTable dependencyCountTable = (mrmathami.cia.java.jdt.project.tree.dependency.DependencyCountTable) Dependencies.get(node);
//            System.out.println(dependencyCountTable.getCount(JavaDependency.USE));
        }
    }
}
