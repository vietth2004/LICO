package com.example.springservice.dependency;

import com.example.springservice.ast.annotation.JavaAnnotation;
import com.example.springservice.ast.dependency.DependencyCountTable;
import com.example.springservice.ast.dependency.Pair;
import com.example.springservice.ast.node.JavaNode;
import com.example.springservice.ast.node.Node;
import com.example.springservice.ast.type.JavaType;
import com.example.springservice.resource.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class DependencyServiceImpl implements DependencyService{

    public List<Dependency> getAllDependency(List<JavaNode> javaNodeList) {
        List<JavaNode> springJavaNodeList = convertSpringNodes(javaNodeList);
        List<Dependency> dependencies = new ArrayList<>();
        dependencies.addAll(getSpringDependency(springJavaNodeList, javaNodeList));

        return dependencies;
    }

    private List<Dependency> getSpringDependency(List<JavaNode> springJavaNodeList, List<JavaNode> javaNodeList) {
        List<Dependency> dependencies = new ArrayList<>();
        List<JavaNode> springControllerJavaNodeList = new ArrayList<>();
        List<JavaNode> springServiceJavaNodeList = new ArrayList<>();
        List<JavaNode> springRepositoryJavaNodeList = new ArrayList<>();

        // Gather each Spring Java class

        for(JavaNode javaNode : springJavaNodeList) {
            if(containSpringAnnotations(javaNode, Resource.SPRING_CONTROLLER_ANNOTATION_QUALIFIED_NAME)) {
                springControllerJavaNodeList.addAll(getSpringChildren(javaNode.getChildren(), javaNodeList));
                System.out.println("Found Spring Controller: " + javaNode.getQualifiedName());
            }
            if(containSpringAnnotations(javaNode, Resource.SPRING_MVC_SERVICE_QUALIFIED_NAME)) {
                springServiceJavaNodeList.addAll(getSpringChildren(javaNode.getChildren(), javaNodeList));
                System.out.println("Found Spring Service: " + javaNode.getQualifiedName());
            }
            if(containSpringAnnotations(javaNode, Resource.SPRING_MVC_REPOSITORY_QUALIFIED_NAME)) {
                springRepositoryJavaNodeList.addAll(getSpringChildren(javaNode.getChildren(), javaNodeList));
                System.out.println("Found Spring Repository: " + javaNode.getQualifiedName());
            }
        }

        //Add all dependencies

        dependencies.addAll(getControllerServiceDependency(springControllerJavaNodeList, springServiceJavaNodeList));
        dependencies.addAll(getServiceRepositoryDependency(springServiceJavaNodeList, springRepositoryJavaNodeList));
        dependencies.addAll(getControllerRepositoryDependency(springControllerJavaNodeList, springRepositoryJavaNodeList));

        return dependencies;
    }

    private List<Dependency> getControllerServiceDependency(List<JavaNode> springControllerJavaNodeList, List<JavaNode> springServiceJavaNodeList) {
        List<Dependency> dependencies = new ArrayList<>();

        for (JavaNode controllerNode : springControllerJavaNodeList) {
            for (Pair dependenceNode : controllerNode.getDependencyTo()) {
                Node node = dependenceNode.getNode();

                for (JavaNode serviceNode : springServiceJavaNodeList) {
                    if (node.getId() == serviceNode.getId()) {
                        dependencies.add(new Dependency(
                                node,
                                serviceNode,
                                new DependencyCountTable(0,0,0,0,0)));
                    }
                }
            }
        }

        return dependencies;
    }

    private List<Dependency> getServiceRepositoryDependency(List<JavaNode> springServiceJavaNodeList, List<JavaNode> springRepositoryJavaNodeList) {
        List<Dependency> dependencies = new ArrayList<>();

        for (JavaNode controllerNode : springServiceJavaNodeList) {
            for (Pair dependenceNode : controllerNode.getDependencyTo()) {
                Node node = dependenceNode.getNode();

                for (JavaNode serviceNode : springRepositoryJavaNodeList) {
                    if (node.getId() == serviceNode.getId()) {
                        dependencies.add(new Dependency(
                                node,
                                serviceNode,
                                new DependencyCountTable(0,0,0,0,0)));
                    }
                }
            }
        }

        return dependencies;
    }

    private List<Dependency> getControllerRepositoryDependency(List<JavaNode> springControllerJavaNodeList, List<JavaNode> springRepositoryJavaNodeList) {
        List<Dependency> dependencies = new ArrayList<>();

        for (JavaNode controllerNode : springControllerJavaNodeList) {
            for (Pair dependenceNode : controllerNode.getDependencyTo()) {
                Node node = dependenceNode.getNode();

                for (JavaNode serviceNode : springRepositoryJavaNodeList) {
                    if (node.getId() == serviceNode.getId()) {
                        dependencies.add(new Dependency(
                                node,
                                serviceNode,
                                new DependencyCountTable(0,0,0,0,0)));
                    }
                }
            }
        }

        return dependencies;
    }

    private List<JavaNode> convertSpringNodes(List<JavaNode> javaNodeList) {

        List<JavaNode> springJavaNodeList = null;
        for (JavaNode javaNode : javaNodeList) {
            if(containSpringAnnotations(javaNode, Resource.SPRING_ANNOTATION_QUALIFIED_NAME)) {
                springJavaNodeList.add(javaNode);
                System.out.println("Found Spring: " + javaNode.getQualifiedName());
            }
            if(isSpringInterface(javaNode, Resource.SPRING_REPOSITORY_INTERFACE_QUALIFIED_NAME)) {
                springJavaNodeList.add(javaNode);
                System.out.println("Found Spring: " + javaNode.getQualifiedName());
            }
        }
        return springJavaNodeList;
    }

    private Boolean containSpringAnnotations(JavaNode javaNode, List<String> conditionState) {
        for(JavaAnnotation javaAnnotation : javaNode.getAnnotates()) {
            System.out.println("Annotations: " + javaAnnotation.getName());
            if(conditionState.contains(javaAnnotation.getName())) {
                return true;
            }
        }
        return false;
    }

    private Boolean isSpringInterface(JavaNode javaNode, List<String> interfaceList) {

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

    private List<JavaNode> getSpringChildren (List<Integer> childNodes, List<JavaNode> javaNodeList) {
        List springNodes = new ArrayList();

        for (JavaNode javaNode : javaNodeList) {
            if (childNodes.contains(javaNode.getId())) {
                springNodes.add(javaNode);
            }
        }

        return springNodes;
    }
}
