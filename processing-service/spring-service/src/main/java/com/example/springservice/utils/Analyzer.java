package com.example.springservice.utils;

import com.example.springservice.ast.dependency.DependencyCountTable;
import com.example.springservice.ast.dependency.Pair;
import com.example.springservice.ast.node.JavaNode;
import com.example.springservice.ast.node.Node;
import com.example.springservice.dependency.Dependency;
import com.example.springservice.dom.Xml.XmlTagNode;
import com.example.springservice.resource.Resource;
import com.example.springservice.utils.worker.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Analyzer {



    public static List<Dependency> getSpringDependency(List<JavaNode> springJavaNodes,
                                                       List<JavaNode> javaNodes,
                                                       List<com.example.springservice.dom.Node> xmlNodes) {
        List<Dependency> dependencies = new ArrayList<>();
        HashSet<JavaNode> springControllerJavaNodes = new HashSet<>();
        HashSet<JavaNode> springServiceJavaNodes = new HashSet<>();
        HashSet<JavaNode> springRepositoryJavaNodes = new HashSet<>();
        List<com.example.springservice.dom.Node> xmlNodesWithoutNull = Filter.filteringNullNodes(xmlNodes);

        // Gather each Spring Java class
        for(JavaNode javaNode : springJavaNodes) {
            if(Checker.containSpringAnnotations(javaNode, Resource.SPRING_MVC_CONTROLLER_SIMPLE_NAME)) {
                springControllerJavaNodes.addAll(gatherControllerNode(javaNodes, javaNode));
            }
            if(Checker.containSpringAnnotations(javaNode, Resource.SPRING_MVC_SERVICE_SIMPLE_NAME)) {
                springServiceJavaNodes.addAll(gatherDaoNode(javaNodes, javaNode));
            }
            if(Checker.containSpringAnnotations(javaNode, Resource.SPRING_MVC_REPOSITORY_SIMPLE_NAME)
                    || Checker.isSpringInterface(javaNode, Resource.SPRING_REPOSITORY_INTERFACE_SIMPLE_NAME)) {
                springRepositoryJavaNodes.addAll(gatherDaoNode(javaNodes, javaNode));
            }
        }

        //Filtering null node


//        print(springControllerJavaNodes,springServiceJavaNodes, springRepositoryJavaNodes);

        //Add all dependencies
        dependencies.addAll(getControllerServiceDependency(springControllerJavaNodes, springServiceJavaNodes));
        dependencies.addAll(getServiceRepositoryDependency(springServiceJavaNodes, springRepositoryJavaNodes));
        dependencies.addAll(getControllerRepositoryDependency(springControllerJavaNodes, springRepositoryJavaNodes));
        dependencies.addAll(getXmlJavaDependency(javaNodes, xmlNodesWithoutNull));

        return dependencies;
    }

    public static List<Dependency> getControllerServiceDependency(HashSet<JavaNode> springControllerJavaNodeList, HashSet<JavaNode> springServiceJavaNodeList) {
        return getDependencies(springControllerJavaNodeList, springServiceJavaNodeList);
    }

    public static List<Dependency> getServiceRepositoryDependency(HashSet<JavaNode> springServiceJavaNodeList, HashSet<JavaNode> springRepositoryJavaNodeList) {
        return getDependencies(springServiceJavaNodeList, springRepositoryJavaNodeList);
    }

    public static List<Dependency> getControllerRepositoryDependency(HashSet<JavaNode> springControllerJavaNodeList, HashSet<JavaNode> springRepositoryJavaNodeList) {
        return getDependencies(springControllerJavaNodeList, springRepositoryJavaNodeList);
    }

    public static List<Dependency> getXmlJavaDependency(List<JavaNode> javaNodes, List<com.example.springservice.dom.Node> xmlNodes) {
        Set<Dependency> dependencies = new HashSet<>();

        for(com.example.springservice.dom.Node xmlNode : xmlNodes) {
            for(XmlTagNode xmlTagNode : xmlNode.getChildren()) {
                dependencies.addAll(findSpringXmlNodes(javaNodes, xmlTagNode, xmlNodes, dependencies));
            }
        }

        return Converter.convertSetToList(dependencies);
    }

    public static Set<Dependency> findSpringXmlNodes(List<JavaNode> javaNodes, XmlTagNode xmlTagNode,
                                                     List<com.example.springservice.dom.Node> xmlNodes,
                                                     Set<Dependency> dependencies) {

        if (xmlTagNode.getName().equals("beans")) {
            if (Checker.isSpring(xmlTagNode.getListAttr())) {
                analyzeXmlNodes(javaNodes, xmlTagNode.getChildren(), dependencies, xmlNodes);
            }
        }

        return dependencies;
    }

    public static void analyzeXmlNodes(List<JavaNode> javaNodes,
                                       List<XmlTagNode> xmlTagNodes,
                                       Set<Dependency> dependencies,
                                       List<com.example.springservice.dom.Node> xmlNodes) {

        for(XmlTagNode xmlTagNode : xmlTagNodes) {

            if(xmlTagNode.getTagName().equals("import")) {
                String xmlNodeName = xmlTagNode.getAttributes().get("resource");

                dependencies.add(new Dependency(xmlTagNode.getId(), Searcher.searchXmlNode(xmlNodeName, xmlNodes).getId(),
                        new DependencyCountTable(0,0,0,0,0, 1)));
            }

            if(xmlTagNode.getTagName().contains("component-scan")) {
                String packageName = xmlTagNode.getAttributes().get("base-package");

                dependencies.add(new Dependency(xmlTagNode.getId(), Searcher.searchJavaNode(packageName, javaNodes).getId(),
                        new DependencyCountTable(0,0,0,0,0, 1)));

            }

            if(xmlTagNode.getTagName().contains("property-placeholder")) {

            }

            if(xmlTagNode.getTagName().contains("bean")) {

            }

            if(xmlTagNode.getTagName().contains("beans")) {
                analyzeXmlNodes(javaNodes, xmlTagNode.getChildren(), dependencies, xmlNodes);
            }
        }

    }


    public static List<Dependency> getDependencies(HashSet<JavaNode> springCallerJavaNodes, HashSet<JavaNode> springCalleeJavaNodes) {
        List<Dependency> dependencies = new ArrayList<>();

        for (JavaNode callerNode : springCallerJavaNodes) {

            for (Pair dependenceNode : callerNode.getDependencyTo()) {
                Node node = dependenceNode.getNode();
                for (JavaNode calleeNode : springCalleeJavaNodes) {
                    if (node.getId().equals(calleeNode.getId()) && dependenceNode.getDependency().getMEMBER().equals(0)) {
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
        javaControllerNodes.addAll(Getter.getSpringChildren(javaNode.getChildren(), javaNodes));
        return javaControllerNodes;
    }

    public static List<JavaNode> gatherDaoNode(List<JavaNode> javaNodes, JavaNode javaNode) {
        List<JavaNode> javaDaoNodes = new ArrayList<>();
        javaDaoNodes.add(javaNode);
        javaDaoNodes.addAll(Getter.getSpringChildren(javaNode.getChildren(), javaNodes));
        List<Integer> tempNodes = Searcher.searchJavaNode(Getter.getInheritanceNode(javaNode.getDependencyTo()), javaNodes).getChildren();
        javaDaoNodes.addAll(Getter.getSpringChildren(tempNodes, javaNodes));
        return javaDaoNodes;
    }



    public static void print (List<JavaNode> springControllerJavaNodes,
                              List<JavaNode> springServiceJavaNodes,
                              List<JavaNode> springRepositoryJavaNodes) {

        System.out.println();

        for (JavaNode javaNode : springControllerJavaNodes) {
            System.out.println(javaNode.getQualifiedName());
        }

        System.out.println();

        for (JavaNode javaNode : springServiceJavaNodes) {
            System.out.println(javaNode.getQualifiedName());
        }

        System.out.println();

        for (JavaNode javaNode : springRepositoryJavaNodes) {
            System.out.println(javaNode.getQualifiedName());
        }

        System.out.println();
    }

}
