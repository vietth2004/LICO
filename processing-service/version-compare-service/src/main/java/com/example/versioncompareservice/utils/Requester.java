package com.example.versioncompareservice.utils;

import com.example.versioncompareservice.ast.dependency.Dependency;
import com.example.versioncompareservice.ast.node.JavaNode;
import com.example.versioncompareservice.ast.node.NodeWeight;
import com.example.versioncompareservice.dom.Node;
import com.example.versioncompareservice.model.CiaRequest;
import com.example.versioncompareservice.model.CiaResponse;
import com.example.versioncompareservice.model.Path;
import com.example.versioncompareservice.utils.communicator.Response;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
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

    public static Response getXmlNodes(String path) {
        RestTemplate restTemplate = new RestTemplate();

        //setting up the request headers
        HttpHeaders requestHeaders = new HttpHeaders();

        //request entity is created with request body and headers
        HttpEntity<Path> requestEntity = new HttpEntity<>(new Path(path), requestHeaders);

        List<Node> xmlNodes = new ArrayList<>();
        ResponseEntity<Response> responseEntity =
                restTemplate.exchange(
                        "http://localhost:7006/api/xml-service/pathParse/old",
                        HttpMethod.POST,
                        requestEntity,
                        Response.class
                );
        return responseEntity.getBody();
    }

}
