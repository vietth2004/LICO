package com.example.parserservice.util.worker;

import com.example.parserservice.ast.dependency.Dependency;
import com.example.parserservice.ast.node.JavaNode;
import com.example.parserservice.dom.Node;
import com.example.parserservice.model.parser.Request;

import java.util.ArrayList;
import java.util.List;

public class Wrapper {

    public static List<Dependency> wrapDependency (List<Dependency> dependencies, List<Dependency> frameworkDependencies, String type) {

        for(Dependency dependency : frameworkDependencies) {
            for(Dependency base : dependencies) {
                if(Checker.isDependency(base,  dependency)) {
                    if(type.equals("SPRING")){
                        base.getType().setSPRING(dependency.getType().getSPRING());
                    }
                }
            }
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

        totalNodes = wrapXmlNode(request.getXmlNodes(), totalNodes, xmlNodes);
        totalNodes = wrapJspNode(request.getJspNodes(), totalNodes, jspNodes);

        Request tmpRequest = new Request(
                request.getRootNode()
                , request.getAllDependencies()
                , request.getJavaNodes()
                , request.getXmlNodes()
                , request.getJspNodes());

        return tmpRequest;
    }

    public static int wrapXmlNode(List nodes, int totalNodes, List<Node> xmlNodes) {
        for(Object xmlNode : nodes) {
            if(xmlNode instanceof Node) {
                ((Node) xmlNode).setId(++totalNodes);
                xmlNodes.add((Node) xmlNode);

                totalNodes = wrapXmlNode(((Node) xmlNode).getChildren(), totalNodes, xmlNodes);
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
}
