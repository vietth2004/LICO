package com.example.parserservice.util;

import com.example.parserservice.ast.dependency.Dependency;
import com.example.parserservice.ast.dependency.Pair;
import com.example.parserservice.ast.node.JavaNode;
import com.example.parserservice.model.Response;
import com.example.parserservice.model.cia.CiaRequest;
import com.example.parserservice.model.cia.CiaResponse;
import com.example.parserservice.model.parser.Request;
import com.example.parserservice.model.parser.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static Boolean isDependency(Dependency base, Dependency dependency) {
        if(base.getCalleeNode().equals(dependency.getCalleeNode())
                && base.getCallerNode().equals(dependency.getCallerNode())) {
            return true;
        }
        return false;
    }

    public static List<Dependency> wrapDependency (List<Dependency> dependencies, List<Dependency> frameworkDependencies, String type) {

        for(Dependency dependency : frameworkDependencies) {
            for(Dependency base : dependencies) {
                if(isDependency(base, dependency)) {
                    if(type.equals("SPRING")){
                        base.getType().setSPRING(dependency.getType().getSPRING());
                    }
                }
            }
        }

        return dependencies;
    }

    public static HttpEntity getResponseEntity(MultipartFile file) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        return requestEntity;
    }

    public static List getDependencies(String parser, List javaNodes) {
        RestTemplate restTemplate = new RestTemplate();
        List dependencies = new ArrayList();
        if(parser.equals("spring-parser")) {

            Request springs = restTemplate.postForObject(
                    "http://localhost:7003/api/dependency/spring", //spring-service
                    javaNodes,
                    Request.class);

            dependencies.addAll(springs.getAllDependencies());
        }
        return dependencies;
    }

    public static Response getResponse(List<String> parserList, Request request) {
        JavaNode javaNode = request.getRootNode();
        List javaNodes = request.getAllNodes();

        List<Dependency> dependencies = request.getAllDependencies();

        for (String parser : parserList) {
            if(Resource.PARSER.contains(parser)) {
                dependencies = wrapDependency(dependencies, getDependencies(parser, javaNodes), "SPRING");
            }
        }

        wrapRootNode(javaNode, dependencies);

        List nodes = getNodesWeight(dependencies, javaNodes.size());

        return new Response(javaNode, javaNodes.size());
    }

    private static void wrapRootNode(JavaNode javaNode, List<Dependency> dependencies) {
        javaNode.setDependencyTo(getDependency(javaNode, javaNode.getDependencyTo(), dependencies));
        javaNode.setDependencyFrom(getDependency(javaNode, javaNode.getDependencyFrom(), dependencies));

        for (Object childNode : javaNode.getChildren()) {
            if(childNode instanceof JavaNode){
                wrapRootNode((JavaNode) childNode, dependencies);
            }
        }

//        for (Pair pair : javaNode.getDependencyTo()) {
//            for(Dependency dependency : dependencies) {
//                if(javaNode.getId().equals(dependency.getCallerNode())
//                && pair.getNode().equals(dependency.getCalleeNode())) {
//                    pair.setDependency(dependency.getType());
//                }
//            }
//        }

//        for (Dependency dependency : dependencies) {
//            if(javaNode.getId().equals(dependency.getCallerNode())) {
//                List<Pair> dependenciesTemp = new ArrayList<>();
//                for(Pair pair : javaNode.getDependencyTo()) {
//                    if(pair.getNode().equals(dependency.getCalleeNode())) {
//                        pair.setDependency(dependency.getType());
//                    }
//                }
//                javaNode.setDependencyTo(dependenciesTemp);
//            }
//
//            if(javaNode.getId().equals(dependency.getCalleeNode())) {
//                for(Pair pair : javaNode.getDependencyFrom()) {
//                    if(pair.getNode().equals(dependency.getCallerNode())) {
//                        pair.setDependency(dependency.getType());
//                    }
//                }
//            }
//        }
    }

    public static List<Pair> getDependency(JavaNode javaNode, List<Pair> nodeDependency, List<Dependency> dependencies) {
        List<Pair> dependenciesTempList = new ArrayList<>();
        for (Pair pair : nodeDependency) {
            for(Dependency dependency : dependencies) {
                if(javaNode.getId().equals(dependency.getCallerNode())
                        && pair.getNode().getId().equals(dependency.getCalleeNode())) {
                    pair.setDependency(dependency.getType());
                }
            }
            dependenciesTempList.add(pair);
        }
        return dependenciesTempList;
    }

    public static List getNodesWeight(List dependencies, Integer size) {

        RestTemplate restTemplate = new RestTemplate();

        CiaResponse ciaResponse = restTemplate.postForObject(
                "http://localhost:6001/api/cia/calculate", //cia-service
                new CiaRequest(dependencies, size),
                CiaResponse.class);

        return ciaResponse.getNodes();
    }

}
