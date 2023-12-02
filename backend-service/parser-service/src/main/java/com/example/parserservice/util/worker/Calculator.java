package com.example.parserservice.util.worker;

import com.example.parserservice.ast.dependency.Dependency;
import com.example.parserservice.ast.dependency.Pair;
import com.example.parserservice.ast.node.JavaNode;
import com.example.parserservice.model.cia.CiaRequest;
import com.example.parserservice.model.cia.CiaResponse;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class Calculator {

    public static List getNodesWeight(List dependencies, Integer size) {

        RestTemplate restTemplate = new RestTemplate();

        CiaResponse ciaResponse = restTemplate.postForObject(
                "http://localhost:6001/api/cia-service/calculate", //cia-service
                new CiaRequest(dependencies, size),
                CiaResponse.class);

        return ciaResponse.getNodes();
    }

    public static void applyWeight(JavaNode rootNode, List<Dependency> dependencies) {

        rootNode.setWeight(calculateJavaNodeWeight(rootNode, dependencies));

    }

    public static int calculateJavaNodeWeight(JavaNode javaNodes, List<Dependency> dependencies) {
        int weight = 0;
        int childWeight = 0;

        for (Pair dependency : javaNodes.getDependencyFrom()) {
            weight += 0;
        }

        for (Object javaNode : javaNodes.getChildren()) {
            if (javaNode instanceof JavaNode) {
                int tmp = calculateJavaNodeWeight((JavaNode) javaNode, dependencies);
                ((JavaNode) javaNode).setWeight(tmp);
                childWeight += tmp;
            }
        }

        weight += childWeight;

        return weight;
    }

    public static int calculateDependenciesWeight(List<Dependency> dependencies) {
        int weight = 0;


        return weight;
    }

}
