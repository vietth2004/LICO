package com.example.parserservice.util.worker;

import com.example.parserservice.ast.dependency.Dependency;
import com.example.parserservice.ast.dependency.Pair;
import com.example.parserservice.ast.node.JavaNode;
import com.example.parserservice.model.Response;
import com.example.parserservice.model.jsf.JSFResponse;
import com.example.parserservice.model.parser.Request;
import com.example.parserservice.model.parser.Resource;

import java.util.ArrayList;
import java.util.List;

public class Getter {

    public static List<Pair> getDependencyTo(JavaNode javaNode, List<Pair> nodeDependency, List<Dependency> dependencies) {
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

    public static List<Pair> getDependencyFrom(JavaNode javaNode, List<Pair> nodeDependency, List<Dependency> dependencies) {
        List<Pair> dependenciesTempList = new ArrayList<>();
        for (Pair pair : nodeDependency) {
            for(Dependency dependency : dependencies) {
                if(javaNode.getId().equals(dependency.getCalleeNode())
                        && pair.getNode().getId().equals(dependency.getCallerNode())) {
                    pair.setDependency(dependency.getType());
                }
            }
            dependenciesTempList.add(pair);
        }
        return dependenciesTempList;
    }

//    public static Response getResponse(List<String> parserList, Request request, String path, JSFResponse jsfResponse) {
//        JavaNode javaNode = request.getRootNode();
//        List javaNodes = request.getJavaNodes();
//        List<Dependency> dependencies = request.getAllDependencies();
//
//        for (String parser : parserList) {
//            if(Resource.PARSER.contains(parser)) {
//                dependencies = Wrapper.wrapDependency(dependencies, Requester.getDependencies(parser, javaNodes), "SPRING");
//            }
//        }
//
//        Wrapper.wrapRootNode(javaNode, dependencies);
//
//        List nodes = Requester.getNodesWeight(dependencies, javaNodes.size());
//
//        return new Response(javaNode, javaNodes.size(), javaNodes, dependencies, path, jsfResponse.getAllNodes());
//    }

    public static Response getResponse(List<String> parserList, Request request, String path) {
        JavaNode javaNode = request.getRootNode();
        List javaNodes = request.getJavaNodes();
        List<Dependency> dependencies = request.getAllDependencies();
        List xmlNodes = request.getXmlNodes();
        List jspNodes = request.getJspNodes();


        Request frameworkRequest = new Request(javaNode, javaNodes, dependencies, xmlNodes, jspNodes);
        for (String parser : parserList) {
            if(Resource.PARSER.contains(parser)) {
                dependencies = Wrapper.wrapDependency(dependencies, Requester.getDependencies(parser, frameworkRequest), "SPRING");
                dependencies = Wrapper.wrapDependency(dependencies, Requester.getDependencies(parser, frameworkRequest), "JSF");
                dependencies = Wrapper.wrapDependency(dependencies, Requester.getDependencies(parser, frameworkRequest), "STRUTS");
            }
        }

        Wrapper.wrapRootNode(javaNode, dependencies);

        List nodes = Requester.getNodesWeight(dependencies, javaNodes.size());

        return new Response(javaNode, javaNodes.size(), javaNodes, dependencies, path, xmlNodes, jspNodes);
    }
}
