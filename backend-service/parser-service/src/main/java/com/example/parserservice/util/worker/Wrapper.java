package com.example.parserservice.util.worker;

import com.example.parserservice.ast.dependency.Dependency;
import com.example.parserservice.ast.node.JavaNode;
import com.example.parserservice.dom.Node;
import com.example.parserservice.dom.Properties.PropertiesFileNode;
import com.example.parserservice.dom.Properties.PropertiesNode;
import com.example.parserservice.model.parser.Request;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class Wrapper {

    public static List<Dependency> wrapDependency (List<Dependency> dependencies, List<Dependency> frameworkDependencies, String type) {

        if(type.equals("SPRING")){
            for(Dependency dependency : frameworkDependencies) {
                for(Dependency base : dependencies) {
                    if(Checker.isDependency(base,  dependency)) {
                        base.getType().setSPRING(dependency.getType().getSPRING());
                    }
                }
            }
        }

        if(type.equals("JSF")) {
            dependencies.addAll(frameworkDependencies);
        }

        return dependencies;
    }


    public static void wrapRootNode(JavaNode javaNode, List<Dependency> dependencies) {
        javaNode.setDependencyTo(Getter.getDependencyTo(javaNode, javaNode.getDependencyTo(), dependencies));
        javaNode.setDependencyFrom(Getter.getDependencyFrom(javaNode, javaNode.getDependencyFrom(), dependencies));

        for (Object childNode : javaNode.getChildren()) {
            if(childNode instanceof JavaNode){
                wrapRootNode((JavaNode) childNode, dependencies);
            }
        }
    }

    public static Request wrapXmlAndJspNode(Request request) {
        int totalNodes = request.getJavaNodes().size();

        List<Node> xmlNodes = new ArrayList<>();
        List<Node> jspNodes = new ArrayList<>();

        System.out.println("Total Nodes: " + totalNodes);
        totalNodes = wrapXmlNode(request.getXmlNodes(), totalNodes, xmlNodes);
        System.out.println("Total Nodes: " + totalNodes);
        totalNodes = wrapJspNode(request.getJspNodes(), totalNodes, jspNodes);
        System.out.println("Total Nodes: " + totalNodes);
        wrapPropNode(request.getPropertiesNodes(), totalNodes);

        Request tmpRequest = new Request(
                request.getRootNode()
                , request.getAllDependencies()
                , request.getJavaNodes()
                , xmlNodes
                , jspNodes);

        return tmpRequest;
    }

    public static int wrapXmlNode(List nodes, int totalNodes, List<Node> xmlNodes) {
        ObjectMapper mapper = new ObjectMapper();

        for(Object xmlNode : nodes) {
            if(xmlNode instanceof Node) {
                ((Node) xmlNode).setId(++totalNodes);
                xmlNodes.add((Node) xmlNode);

                totalNodes = wrapJspNode(((Node) xmlNode).getChildren(), totalNodes, xmlNodes);
            }
        }
        return totalNodes;
    }

    public static int wrapJspNode(List nodes, int totalNodes, List<Node> jspNodes) {
        for(Object jspNode : nodes) {
            if(jspNode instanceof Node) {
                ((Node) jspNode).setId(++totalNodes);
                jspNodes.add((Node) jspNode);

                totalNodes = wrapJspNode(((Node) jspNode).getChildren(), totalNodes, jspNodes);
            }
        }
        return totalNodes;
    }

    public static void wrapPropNode(List nodes, int totalNodes) {
        for(Object propNode : nodes) {
            if(propNode instanceof PropertiesFileNode) {
                ((PropertiesFileNode) propNode).setId(++totalNodes);
                for(PropertiesNode prop : ((PropertiesFileNode) propNode).getProperties()) {
                    prop.setId(++totalNodes);
                }
            }
        }
    }

}
