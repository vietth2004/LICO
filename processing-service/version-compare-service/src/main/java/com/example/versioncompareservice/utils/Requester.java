package com.example.versioncompareservice.utils;

import com.example.versioncompareservice.ast.dependency.Dependency;
import com.example.versioncompareservice.ast.node.JavaNode;
import com.example.versioncompareservice.ast.node.NodeWeight;
import com.example.versioncompareservice.model.CiaRequest;
import com.example.versioncompareservice.model.CiaResponse;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;

public class Requester {

    public static Set<NodeWeight> getImpactedNodes (List<JavaNode> javaNodes, List<JavaNode> changedNodes, List<Dependency> dependencies) {
        RestTemplate restTemplate = new RestTemplate();

        List<Integer> changedNodesId = Converter.convertNodesToNodeIds(changedNodes);

        CiaResponse ciaResponse = restTemplate.postForObject("http://localhost:6001/api/cia-service/impact",
                new CiaRequest(javaNodes, dependencies, javaNodes.size(), changedNodesId),
                CiaResponse.class);

        return ciaResponse.getNodes();
    }
}
