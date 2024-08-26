package com.example.parserservice.model.newResponse;

import com.example.parserservice.model.Response;

import java.util.*;

public class JavaNode extends Node {

    public static ArrayList convertJavaNodes(Response newResponse) {
        List<LinkedHashMap> javaNodes = newResponse.getJavaNodes();

        Set<LinkedHashMap> annotations = new HashSet<>();

        if (javaNodes != null) {
            for (LinkedHashMap javaNode : javaNodes) {
                if (javaNode != null) {
                    addTypeToJavaNode(javaNode);
                    convertAnnotations(javaNode, annotations);
                    convertDependencies(javaNode);
                }
            }
        }

        return new ArrayList<>(annotations);
    }

    private static void convertAnnotations(LinkedHashMap javaNode, Set<LinkedHashMap> allAnnotations) {
        ArrayList annotates = (ArrayList) javaNode.get("annotates");
        ArrayList annotatesWithValue = (ArrayList) javaNode.get("annotatesWithValue");

        ArrayList<LinkedHashMap> annotations = new ArrayList();
        if (annotates != null) {
            annotations.addAll(annotates);
        }
        if (annotatesWithValue != null) {
            annotations.addAll(annotatesWithValue);
        }
        javaNode.remove("annotates");
        javaNode.remove("annotatesWithValue");

        ArrayList<Integer> annotationsId = new ArrayList<>();

        for (LinkedHashMap annotation : annotations) {
            annotationsId.add((Integer) annotation.get("id"));
            allAnnotations.add(annotation);
        }

        javaNode.put("annotationsId", annotationsId);
    }

    private static void addTypeToJavaNode(LinkedHashMap javaNode) {
        javaNode.put("type", "JavaNode");
    }

    private static void convertDependencies(LinkedHashMap javaNode) {
        javaNode.remove("dependencyFrom");
        ArrayList<LinkedHashMap> dependencies = new ArrayList<>();

        ArrayList<LinkedHashMap> dependencyTo = (ArrayList) javaNode.get("dependencyTo");

        if (dependencyTo != null)
            for (LinkedHashMap dependency : dependencyTo) {
                LinkedHashMap newDependency = new LinkedHashMap<>();

                Integer id = (Integer) ((LinkedHashMap) dependency.get("node")).get("id");
                newDependency.put("id", id);

                Integer inheritance = (Integer) ((LinkedHashMap) dependency.get("dependency")).get("inheritance");
                Integer use = (Integer) ((LinkedHashMap) dependency.get("dependency")).get("use");
                Integer member = (Integer) ((LinkedHashMap) dependency.get("dependency")).get("member");
                Integer invocation = (Integer) ((LinkedHashMap) dependency.get("dependency")).get("invocation");
                Integer override = (Integer) ((LinkedHashMap) dependency.get("dependency")).get("override");

                LinkedHashMap dependencyDetails = new LinkedHashMap<>();
                if (inheritance != 0) {
                    dependencyDetails.put("inheritance", inheritance);
                }
                if (use != 0) {
                    dependencyDetails.put("use", use);
                }
                if (member != 0) {
                    dependencyDetails.put("member", member);
                }
                if (invocation != 0) {
                    dependencyDetails.put("invocation", invocation);
                }
                if (override != 0) {
                    dependencyDetails.put("override", override);
                }

                newDependency.put("details", dependencyDetails);

                dependencies.add(newDependency);
            }

        javaNode.remove("dependencyTo");
        javaNode.put("dependencies", dependencies);
    }


}
