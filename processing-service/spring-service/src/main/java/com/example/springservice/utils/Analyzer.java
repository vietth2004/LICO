package com.example.springservice.utils;

import com.example.springservice.ast.annotation.JavaAnnotation;
import com.example.springservice.ast.dependency.DependencyCountTable;
import com.example.springservice.ast.dependency.Pair;
import com.example.springservice.ast.node.JavaNode;
import com.example.springservice.ast.node.Node;
import com.example.springservice.ast.type.JavaType;
import com.example.springservice.dependency.Dependency;
import com.example.springservice.dom.Xml.XmlTagNode;
import com.example.springservice.resource.Resource;

import java.util.ArrayList;
import java.util.List;

public class Analyzer {


    public static List<Dependency> getControllerServiceDependency(List<JavaNode> springControllerJavaNodeList, List<JavaNode> springServiceJavaNodeList) {
        return getDependencies(springControllerJavaNodeList, springServiceJavaNodeList);
    }

    public static List<Dependency> getServiceRepositoryDependency(List<JavaNode> springServiceJavaNodeList, List<JavaNode> springRepositoryJavaNodeList) {
        return getDependencies(springServiceJavaNodeList, springRepositoryJavaNodeList);
    }

    public static List<Dependency> getControllerRepositoryDependency(List<JavaNode> springControllerJavaNodeList, List<JavaNode> springRepositoryJavaNodeList) {
        return getDependencies(springControllerJavaNodeList, springRepositoryJavaNodeList);
    }

    public static List<JavaNode> convertSpringJavaNodes(List<JavaNode> javaNodes) {

        List<JavaNode> springJavaNodes = new ArrayList<>();
        for (JavaNode javaNode : javaNodes) {
            if(containSpringAnnotations(javaNode, Resource.SPRING_ANNOTATION_SIMPLE_NAME)) {
                springJavaNodes.add(javaNode);
            }
            if(isSpringInterface(javaNode, Resource.SPRING_REPOSITORY_INTERFACE_SIMPLE_NAME)) {
                springJavaNodes.add(javaNode);
            }
        }
        return springJavaNodes;
    }

    public static List<com.example.springservice.dom.Node> convertSpringXmlNodes(List<com.example.springservice.dom.Node> xmlTagNodes) {
        List<com.example.springservice.dom.Node> springXmlNodes = new ArrayList<>();

        return springXmlNodes;
    }

    public static Boolean containSpringAnnotations(JavaNode javaNode, List<String> conditionState) {
        for(JavaAnnotation javaAnnotation : javaNode.getAnnotates()) {
            for(String condition : conditionState) {
                if(javaAnnotation.getName().contains(condition)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Boolean isSpringInterface(JavaNode javaNode, List<String> interfaceList) {

        if(javaNode.getEntityClass().equals("JavaInterfaceNode")) {
            for(JavaType extendInterface : javaNode.getExtendInterfaces()) {
                for(String interfaceName : interfaceList) {
                    if(extendInterface.getDescribe().contains(interfaceName)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static List<JavaNode> getSpringChildren (List<Integer> childNodes, List<JavaNode> javaNodes) {
        List springNodes = new ArrayList();

        for (JavaNode javaNode : javaNodes) {
            if (childNodes.contains(javaNode.getId())) {
                springNodes.add(javaNode);
            }
        }

        return springNodes;
    }


    public static List<Dependency> getDependencies(List<JavaNode> springCallerJavaNodes, List<JavaNode> springCalleeJavaNodes) {
        List<Dependency> dependencies = new ArrayList<>();

        for (JavaNode callerNode : springCallerJavaNodes) {
            for (Pair dependenceNode : callerNode.getDependencyTo()) {
                Node node = dependenceNode.getNode();
                for (JavaNode calleeNode : springCalleeJavaNodes) {
                    if (node.getId().equals(calleeNode.getId()) && dependenceNode.getDependency().getMEMBER().equals(0)) {
                        System.out.println(callerNode.getQualifiedName());
                        System.out.println(calleeNode.getQualifiedName());
                        System.out.println();

                        dependencies.add(new Dependency(
                                callerNode.getId(),
                                calleeNode.getId(),
                                new DependencyCountTable(0,0,0,0,0, 1)));
                    }
                }
            }
        }
        return dependencies;
    }

    public static List<JavaNode> gatherControllerNode (List<JavaNode> javaNodes, JavaNode javaNode) {
        List<JavaNode> javaControllerNodes = new ArrayList<>();
        javaControllerNodes.add(javaNode);
        javaControllerNodes.addAll(Analyzer.getSpringChildren(javaNode.getChildren(), javaNodes));
        return javaControllerNodes;
    }

    public static List<JavaNode> gatherDaoNode(List<JavaNode> javaNodes, JavaNode javaNode) {
        List<JavaNode> javaDaoNodes = new ArrayList<>();
        javaDaoNodes.add(javaNode);
        javaDaoNodes.addAll(Analyzer.getSpringChildren(javaNode.getChildren(), javaNodes));
        List<Integer> tempNodes = Searcher.searchJavaNode(Getter.getInheritanceNode(javaNode.getDependencyTo()), javaNodes).getChildren();
        javaDaoNodes.addAll(Analyzer.getSpringChildren(tempNodes, javaNodes));
        return javaDaoNodes;
    }

}
