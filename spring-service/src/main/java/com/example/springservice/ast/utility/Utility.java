package com.example.springservice.ast.utility;

import com.example.springservice.ast.annotation.JavaAnnotation;
import com.example.springservice.ast.node.JavaNode;
import com.example.springservice.ast.node.Node;
import com.example.springservice.ast.type.JavaType;
import mrmathami.annotations.Nonnull;
import mrmathami.cia.java.jdt.tree.annotate.Annotate;
import mrmathami.cia.java.jdt.tree.node.AbstractNode;
import mrmathami.cia.java.jdt.tree.type.AbstractType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Utility {


    @Nonnull
    public static List<JavaNode> convertAbstractNode(List<AbstractNode> abstractNodeList) {
        List<JavaNode> children = new ArrayList<>();
        for(AbstractNode node : abstractNodeList) {
            children.add(new JavaNode(node));
        }

        return children;
    }

    @Nonnull
    public static List<Node> convertMap(Set<AbstractNode> nodeList) {
        List<Node> javaNodeList = new ArrayList<>();
        for(AbstractNode node : nodeList) {
            javaNodeList.add(new Node(node));
        }
        return javaNodeList;
    }

    @Nonnull
    public static List<String> convertModifiers(int modifierId) {
        List<String> modifierList = new ArrayList<>();

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
}
