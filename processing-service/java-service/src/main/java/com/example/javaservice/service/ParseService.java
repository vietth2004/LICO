package com.example.javaservice.service;

import com.example.javaservice.ast.annotation.JavaAnnotation;
import com.example.javaservice.ast.annotation.MemberValuePair;
import com.netflix.discovery.shared.Pair;
import mrmathami.cia.java.jdt.tree.node.AbstractNode;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParseService {

    public static List<JavaAnnotation> getAnnotation(String path, AbstractNode abstractNode) throws IOException {

        final List<JavaAnnotation> javaAnnotationList = new ArrayList<>();

        Pair<Integer, Integer> javaIdentifierNodeCount = getIdentifierPos(abstractNode);
        int count = javaIdentifierNodeCount.first();
        int pos = javaIdentifierNodeCount.second();

        ASTParser parser = ASTParser.newParser(AST.JLS8);
        char[] fileContent = getFileContent(path).toCharArray();
        parser.setSource(fileContent);

        CompilationUnit cu = (CompilationUnit) parser.createAST(null);

        String packageName = cu.getPackage().getName().getFullyQualifiedName();

        if (abstractNode.getEntityClass().equals("JavaClassNode")) {
            for (Object obj : cu.types()) {
                if (obj instanceof TypeDeclaration) {
//                    System.out.println(((TypeDeclaration) obj).getName());
                    javaAnnotationList.addAll(visitModifier(((TypeDeclaration) obj).modifiers()));
                }
            }
        }

        if (abstractNode.getEntityClass().equals("JavaMethodNode")) {
            int nodePos = abstractNode.getId() - abstractNode.getParent().getId() - 1;
            int id = nodePos - behindInitNode(nodePos, pos);

//            System.out.println("Id: " + id);

//            System.out.println();

            TypeDeclaration typeDeclaration = (TypeDeclaration) cu.types().get(0);
            MethodDeclaration methodDeclaration = (MethodDeclaration) typeDeclaration.bodyDeclarations().get(id);

//            System.out.println("Method Node: " + typeDeclaration.getName());

            javaAnnotationList.addAll(visitModifier(methodDeclaration.modifiers()));
        }

        if (abstractNode.getEntityClass().equals("JavaFieldNode")) {
            int nodePos = abstractNode.getId() - abstractNode.getParent().getId() - 1;
            int id = nodePos - behindInitNode(nodePos, pos);

//            System.out.println("Id: " + id);

            TypeDeclaration typeDeclaration = (TypeDeclaration) cu.types().get(0);
            FieldDeclaration fieldDeclaration = (FieldDeclaration) typeDeclaration.bodyDeclarations().get(id);

//            System.out.println("Field Node: " + typeDeclaration.getName());

            javaAnnotationList.addAll(visitModifier(fieldDeclaration.modifiers()));
        }

//        System.out.println();

        return javaAnnotationList;
    }

    public static String getFileContent(String filepath) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(filepath));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while (line != null) {
            sb.append(line);
            sb.append(System.lineSeparator());
            line = br.readLine();
        }
        return sb.toString();
    }

    public static List<JavaAnnotation> visitModifier(List modifierList) {
        List<JavaAnnotation> javaAnnotationList = new ArrayList<>();
        int count = 0;

        for (Object modifier : modifierList) {

            if (modifier instanceof MarkerAnnotation) {
                MarkerAnnotation annotation = (MarkerAnnotation) modifier;
                javaAnnotationList.add(new JavaAnnotation(
                        "JavaAnnotate",
                        "JavaAnnotate",
                        count,
                        annotation.getTypeName().getFullyQualifiedName(),
                        "",
                        new ArrayList<>()));

                ++count;
            }

            if (modifier instanceof SingleMemberAnnotation) {
                SingleMemberAnnotation annotation = (SingleMemberAnnotation) modifier;

                javaAnnotationList.add(new JavaAnnotation(
                        "JavaAnnotate",
                        "JavaAnnotate",
                        count,
                        annotation.getTypeName().getFullyQualifiedName(),
                        annotation.getValue().toString(),
                        new ArrayList<>()));

                ++count;
            }

            if (modifier instanceof NormalAnnotation) {

                NormalAnnotation annotation = (NormalAnnotation) modifier;

                List<MemberValuePair> pairs = new ArrayList<>();
                for (Object obj : ((NormalAnnotation) modifier).values()) {
                    if (obj instanceof org.eclipse.jdt.core.dom.MemberValuePair) {
                        org.eclipse.jdt.core.dom.MemberValuePair memberValuePair = (org.eclipse.jdt.core.dom.MemberValuePair) obj;
                        pairs.add(new MemberValuePair(memberValuePair.getName().getIdentifier(), memberValuePair.getValue().toString()));
                    }
                }


                javaAnnotationList.add(new JavaAnnotation(
                        "JavaAnnotate",
                        "JavaAnnotate",
                        count,
                        annotation.getTypeName().getFullyQualifiedName(),
                        "",
                        pairs));

                ++count;
            }

//            if(modifier instanceof Annotation) {
//                Annotation annotation = (Annotation) modifier;
//
//                javaAnnotationList.add(new JavaAnnotation(
//                        "JavaAnnotate",
//                        "JavaAnnotate",
//                        count,
//                        annotation.getTypeName().getFullyQualifiedName(),
//                        "",
//                        new ArrayList<>()));
//
//                ++count;
//            }

        }

        return javaAnnotationList;
    }

    public static String generateQualifiedName(MethodDeclaration methodDeclaration, String packageName) {
        String uniqueName = new String();

        uniqueName = packageName + "." + methodDeclaration.getName().getFullyQualifiedName();

        return uniqueName;
    }

    public static String generateQualifiedName(FieldDeclaration fieldDeclaration, String packageName) {
        String uniqueName = new String();


        return uniqueName;
    }

    public static Pair<Integer, Integer> getIdentifierPos(AbstractNode abstractNode) {
        int count = 0;
        int pos = 0;
        if (abstractNode.getEntityClass().equals("JavaClassNode")) {
            for (AbstractNode childNode : abstractNode.getChildren()) {
                if (childNode.getSimpleName().equals("<init>")) {
                    ++count;
                    pos = abstractNode.getId() - childNode.getId() - 1;
                }
            }
        }
        return new Pair<>(count, pos);
    }

    public static int behindInitNode(int nodePos, int initPos) {
        if (initPos == 0) {
            return 0;
        }

        if (nodePos > initPos) {
            return 1;
        }

        return 0;

    }
}
