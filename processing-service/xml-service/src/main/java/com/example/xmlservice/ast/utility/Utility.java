package com.example.xmlservice.ast.utility;

import com.example.xmlservice.ast.annotation.JavaAnnotation;
import com.example.xmlservice.ast.dependency.Dependency;
import com.example.xmlservice.ast.dependency.DependencyCountTable;
import com.example.xmlservice.ast.dependency.Pair;
import com.example.xmlservice.ast.node.JavaNode;
import com.example.xmlservice.ast.node.Node;
import com.example.xmlservice.ast.type.JavaType;
import mrmathami.annotations.Nonnull;
import mrmathami.cia.java.jdt.tree.annotate.Annotate;
import mrmathami.cia.java.jdt.tree.node.AbstractNode;
import mrmathami.cia.java.jdt.tree.type.AbstractType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Utility {

    public static void printList(List list) {

        for (Object obj : list) {
            if (obj instanceof JavaNode) {
                System.out.println(((JavaNode) obj).getQualifiedName());
            }
        }
    }

    public static List getDependency(JavaNode rootNode) {
        List<Dependency> dependencies = new ArrayList<>();

        for (Pair node : rootNode.getDependencyTo()) {
            dependencies.add(new Dependency(rootNode.getId(), node.getNode().getId(), node.getDependency()));
        }

        for (Object javaNode : rootNode.getChildren()) {
            if (javaNode instanceof JavaNode) {
                dependencies.addAll(getDependency((JavaNode) javaNode));
            }
        }

        return dependencies;
    }

    @Nonnull
    public static List<JavaNode> convertAbstractNode(List<AbstractNode> abstractNodeList) {
        List<JavaNode> javaNodeList = new ArrayList<>();
        for (AbstractNode node : abstractNodeList) {
            Integer parent = node.getParent().getId();
            String path = new String();
            if (!node.getEntityClass().equals("JavaPackageNode")) {
                path = node.getSourceFile().getRelativePath().toString();
            }
            javaNodeList.add(new JavaNode(node, true, parent, path));
        }

        return javaNodeList;
    }

    @Nonnull
    public static List<JavaNode> convertToAllNodes(List<AbstractNode> abstractNodeList) {
        List<JavaNode> javaNodeList = new ArrayList<>();
        for (AbstractNode node : abstractNodeList) {
            try {
                Integer parent = node.getParent().getId();
                javaNodeList.add(new JavaNode(node, false, parent));
            } catch (Exception e) {
                javaNodeList.add(new JavaNode(node, false, 0));
            }
        }

        return javaNodeList;
    }

    @Nonnull
    public static List<Integer> convertChildren(List<AbstractNode> abstractNodeList) {
        List<Integer> javaNodeList = new ArrayList<>();
        for (AbstractNode node : abstractNodeList) {
            javaNodeList.add(node.getId());
        }

        return javaNodeList;
    }

    @Nonnull
    public static List<Node> convertNode(List<AbstractNode> abstractNodeList) {
        List<Node> children = new ArrayList<>();
        for (AbstractNode node : abstractNodeList) {
            children.add(new Node(node));
        }

        return children;
    }

    @Nonnull
    public static List convertMap(Map<AbstractNode, mrmathami.cia.java.jdt.tree.dependency.DependencyCountTable> nodeList) {
        List<Pair> javaNodeList = new ArrayList<>();
        for (AbstractNode node : nodeList.keySet()) {
            DependencyCountTable dependencyCountTable = new DependencyCountTable(nodeList.get(node));
            javaNodeList.add(new Pair(new Node(node), dependencyCountTable));
        }
        return javaNodeList;
    }

    @Nonnull
    public static List<String> convertModifiers(Set modifierSet) {
        List<String> modifierList = new ArrayList<>();

        for (Object obj : modifierSet) {
            modifierList.add(obj.toString());
        }

        return modifierList;
    }

    public static List<JavaAnnotation> convertAnnotates(List<Annotate> annotates) {
        List<JavaAnnotation> javaAnnotationList = new ArrayList<>();

        for (Annotate annotate : annotates) {
            javaAnnotationList.add(new JavaAnnotation(annotate));
        }
        return javaAnnotationList;
    }

    public static List<JavaType> convertParameters(List<AbstractType> parameters) {
        List<JavaType> javaTypeList = new ArrayList<>();

        for (AbstractType parameter : parameters) {
            javaTypeList.add(new JavaType(parameter));
        }

        return javaTypeList;
    }

    public static List<JavaType> convertArguments(List<AbstractType> argumentList) {
        List<JavaType> arguments = new ArrayList<>();

        for (AbstractType abstractType : argumentList) {
            arguments.add(new JavaType(abstractType));
        }

        return arguments;
    }

    public static void findDependency(mrmathami.cia.java.tree.node.JavaNode javaRootNode) {

        printDependency(javaRootNode.getDependencyTo());

        for (mrmathami.cia.java.tree.node.JavaNode javaNode : javaRootNode.getChildren()) {
            findDependency(javaNode);
        }
    }

    private static void printDependency(Map Dependencies) {
        Set<AbstractNode> nodes = Dependencies.keySet();


        for (AbstractNode node : nodes) {
            mrmathami.cia.java.jdt.tree.dependency.DependencyCountTable dependencyCountTable = (mrmathami.cia.java.jdt.tree.dependency.DependencyCountTable) Dependencies.get(node);
//            System.out.println(dependencyCountTable.getCount(JavaDependency.USE));
        }
    }
}
