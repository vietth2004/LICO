package com.example.parserservice.util.worker;

import com.example.parserservice.ast.dependency.Dependency;
import com.example.parserservice.ast.node.JavaNode;
import com.example.parserservice.dom.Node;
import com.example.parserservice.dom.Properties.PropertiesFileNode;
import com.example.parserservice.dom.Properties.PropertiesNode;
import com.example.parserservice.model.parser.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Wrapper {

    public static List<Dependency> wrapDependency (List<Dependency> dependencies, List<Dependency> frameworkDependencies, String type) {

        if(type.equals("SPRING")){
            dependencies.addAll(frameworkDependencies);
        }

        if(type.equals("JSF")) {
            dependencies.addAll(frameworkDependencies);
        }

        if(type.equals("STRUTS")){
            dependencies.addAll(frameworkDependencies);
        }

        return dependencies;
    }


    public static void wrapRootNode(JavaNode javaNode, List<Dependency> dependencies) {
//        javaNode.setDependencyTo(Getter.getDependencyTo(javaNode, javaNode.getDependencyTo(), dependencies));
//        javaNode.setDependencyFrom(Getter.getDependencyFrom(javaNode, javaNode.getDependencyFrom(), dependencies));
        Stack<JavaNode> stack = new Stack<>();
        stack.push(javaNode);
//        for (int i = javaNode.getChildren().size() - 1; i >= 0; i--) {
//            if (javaNode.getChildren().get(i) instanceof JavaNode) {
//                stack.push((JavaNode) javaNode.getChildren().get(i));
//            }
//        }
        while (!stack.isEmpty()) {
            JavaNode childNode = stack.pop();
            javaNode.setDependencyTo(Getter.getDependencyTo(childNode, javaNode.getDependencyTo(), dependencies));
            javaNode.setDependencyFrom(Getter.getDependencyFrom(childNode, javaNode.getDependencyFrom(), dependencies));
            for (int i = childNode.getChildren().size() - 1; i >= 0; i--) {
                stack.push((JavaNode) childNode.getChildren().get(i));
            }
        }
//        for (Object childNode : javaNode.getChildren()) {
//            if(childNode instanceof JavaNode){
//                wrapRootNode((JavaNode) childNode, dependencies);
//            }
//        }
    }

    public static void wrapAllNode(JavaNode javaNode, List<Dependency> dependencies, List<Node> xmlNode, List<Node> jspNode) {

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
        totalNodes = wrapPropNode(request.getPropertiesNodes(), totalNodes);
        System.out.println("Total Nodes: " + totalNodes);


        Request tmpRequest = new Request(
                request.getRootNode()
                , request.getAllDependencies()
                , request.getJavaNodes()
                , xmlNodes
                , jspNodes);

        return tmpRequest;
    }

    public static int wrapXmlNode(List nodes, int totalNodes, List<Node> xmlNodes) {
        Stack<Node> stackWrapXmlNode = new Stack<>();
        for (int i = nodes.size() - 1; i >= 0 ; i--) {
             if (nodes.get(i) instanceof Node) {
                 stackWrapXmlNode.push((Node) nodes.get(i));
             }
        }
        while (!stackWrapXmlNode.isEmpty()) {
           Node xmlNode = stackWrapXmlNode.pop();
           xmlNode.setId(++totalNodes);
           xmlNodes.add(xmlNode);
           for (int i = xmlNode.getChildren().size() - 1; i >= 0; i--) {
               stackWrapXmlNode.push(xmlNode.getChildren().get(i));
           }
        }
//        for(Object xmlNode : nodes) {
//            if(xmlNode instanceof Node) {
//                ((Node) xmlNode).setId(++totalNodes);
//                xmlNodes.add((Node) xmlNode);
//
//                totalNodes = wrapJspNode(((Node) xmlNode).getChildren(), totalNodes, xmlNodes);
//            }
//        }
        return totalNodes;
    }

    public static int wrapJspNode(List nodes, int totalNodes, List<Node> jspNodes) {
        Stack<Node> stackWrapJspNode = new Stack<>();
        for (int i = nodes.size() - 1; i >= 0 ; i--) {
            if (nodes.get(i) instanceof Node) {
                stackWrapJspNode.push((Node) nodes.get(i));
            }
        }
        while (!stackWrapJspNode.isEmpty()) {
            Node jspNode = stackWrapJspNode.pop();
            jspNode.setId(++totalNodes);
            jspNodes.add(jspNode);
            for (int i = jspNode.getChildren().size() - 1; i >= 0; i--) {
                stackWrapJspNode.push(jspNode.getChildren().get(i));
            }
        }
//        for(Object jspNode : nodes) {
//            if(jspNode instanceof Node) {
//                ((Node) jspNode).setId(++totalNodes);
//                jspNodes.add((Node) jspNode);
//
//                totalNodes = wrapJspNode(((Node) jspNode).getChildren(), totalNodes, jspNodes);
//            }
//        }
        return totalNodes;
    }

    public static int wrapPropNode(List nodes, int totalNodes) {
        for(Object propNode : nodes) {
            if(propNode instanceof PropertiesFileNode) {
                ((PropertiesFileNode) propNode).setId(++totalNodes);
                for(PropertiesNode prop : ((PropertiesFileNode) propNode).getProperties()) {
                    prop.setId(++totalNodes);
                }
            }
        }
        return totalNodes;
    }

}
