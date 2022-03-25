package com.example.springservice.dependency;

import com.example.springservice.ast.annotation.JavaAnnotation;
import com.example.springservice.ast.dependency.DependencyCountTable;
import com.example.springservice.ast.dependency.Pair;
import com.example.springservice.ast.node.JavaNode;
import com.example.springservice.ast.node.Node;
import com.example.springservice.ast.type.JavaType;
import com.example.springservice.resource.Resource;
import com.example.springservice.utils.Getter;
import com.example.springservice.utils.Searcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DependencyServiceImpl implements DependencyService{

    public List<Dependency> getAllDependency(List<JavaNode> javaNodeList) {
        List<JavaNode> springJavaNodeList = convertSpringNodes(javaNodeList);
        List<Dependency> dependencies = new ArrayList<>();
        dependencies.addAll(getSpringDependency(springJavaNodeList, javaNodeList));

        return dependencies;
    }

    private List<Dependency> getSpringDependency(List<JavaNode> springJavaNodes, List<JavaNode> javaNodeList) {
        List<Dependency> dependencies = new ArrayList<>();
        List<JavaNode> springControllerJavaNodes = new ArrayList<>();
        List<JavaNode> springServiceJavaNodes = new ArrayList<>();
        List<JavaNode> springRepositoryJavaNodes = new ArrayList<>();

        // Gather each Spring Java class
        for(JavaNode javaNode : springJavaNodes) {
            if(containSpringAnnotations(javaNode, Resource.SPRING_MVC_CONTROLLER_SIMPLE_NAME)) {
                springControllerJavaNodes.add(javaNode);
                springControllerJavaNodes.addAll(getSpringChildren(javaNode.getChildren(), javaNodeList));
            }
            if(containSpringAnnotations(javaNode, Resource.SPRING_MVC_SERVICE_SIMPLE_NAME)) {
                springControllerJavaNodes.add(javaNode);
                springServiceJavaNodes.addAll(getSpringChildren(javaNode.getChildren(), javaNodeList));
                List<Integer> tempNodes = Searcher.searchJavaNode(Getter.getInheritanceNode(javaNode.getDependencyTo()), javaNodeList).getChildren();
                springServiceJavaNodes.addAll(getSpringChildren(tempNodes, javaNodeList));
            }
            if(containSpringAnnotations(javaNode, Resource.SPRING_MVC_REPOSITORY_SIMPLE_NAME)
                    || isSpringInterface(javaNode, Resource.SPRING_REPOSITORY_INTERFACE_SIMPLE_NAME)) {
                springControllerJavaNodes.add(javaNode);
                springRepositoryJavaNodes.addAll(getSpringChildren(javaNode.getChildren(), javaNodeList));
            }
        }

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

        //Add all dependencies
        dependencies.addAll(getControllerServiceDependency(springControllerJavaNodes, springServiceJavaNodes));
        dependencies.addAll(getServiceRepositoryDependency(springServiceJavaNodes, springRepositoryJavaNodes));
        dependencies.addAll(getControllerRepositoryDependency(springControllerJavaNodes, springRepositoryJavaNodes));

        return dependencies;
    }

    private List<Dependency> getControllerServiceDependency(List<JavaNode> springControllerJavaNodeList, List<JavaNode> springServiceJavaNodeList) {
        return getDependencies(springControllerJavaNodeList, springServiceJavaNodeList);
    }

    private List<Dependency> getServiceRepositoryDependency(List<JavaNode> springServiceJavaNodeList, List<JavaNode> springRepositoryJavaNodeList) {
        return getDependencies(springServiceJavaNodeList, springRepositoryJavaNodeList);
    }

    private List<Dependency> getControllerRepositoryDependency(List<JavaNode> springControllerJavaNodeList, List<JavaNode> springRepositoryJavaNodeList) {
        return getDependencies(springControllerJavaNodeList, springRepositoryJavaNodeList);
    }

    private List<JavaNode> convertSpringNodes(List<JavaNode> javaNodeList) {

        List<JavaNode> springJavaNodeList = new ArrayList<>();
        for (JavaNode javaNode : javaNodeList) {
            if(containSpringAnnotations(javaNode, Resource.SPRING_ANNOTATION_SIMPLE_NAME)) {
                springJavaNodeList.add(javaNode);
            }
            if(isSpringInterface(javaNode, Resource.SPRING_REPOSITORY_INTERFACE_SIMPLE_NAME)) {
                springJavaNodeList.add(javaNode);
            }
        }
        return springJavaNodeList;
    }

    private Boolean containSpringAnnotations(JavaNode javaNode, List<String> conditionState) {
        for(JavaAnnotation javaAnnotation : javaNode.getAnnotates()) {
            for(String condition : conditionState) {
                if(javaAnnotation.getName().contains(condition)) {
//                    System.out.println(javaNode.getQualifiedName());
                    return true;
                }
            }
        }
        return false;
    }

    private Boolean isSpringInterface(JavaNode javaNode, List<String> interfaceList) {

        if(javaNode.getEntityClass().equals("JavaInterfaceNode")) {
            for(JavaType extendInterface : javaNode.getExtendInterfaces()) {
                for(String interfaceName : interfaceList) {
                    if(extendInterface.getDescribe().contains(interfaceName)){
//                        System.out.println(javaNode.getQualifiedName());
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


    private List<Dependency> getDependencies(List<JavaNode> springCallerJavaNodeList, List<JavaNode> springCalleeJavaNodeList) {
        List<Dependency> dependencies = new ArrayList<>();

        for (JavaNode callerNode : springCallerJavaNodeList) {
            for (Pair dependenceNode : callerNode.getDependencyTo()) {
                Node node = dependenceNode.getNode();
                for (JavaNode calleeNode : springCalleeJavaNodeList) {
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
}
