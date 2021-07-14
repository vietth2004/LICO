package com.example.springservice.dependency;

import com.example.springservice.ast.annotation.JavaAnnotation;
import com.example.springservice.ast.node.JavaNode;
import com.example.springservice.resource.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DependencyServiceImpl implements DependencyService{

    public List<Dependency> getAllDependency(List<JavaNode> javaNodeList) {
        List<JavaNode> springJavaNodeList = convertSpringNodes(javaNodeList);
        List<Dependency> dependencies = new ArrayList<>();
        dependencies.addAll(getControllerServiceDependency(springJavaNodeList));


        return dependencies;
    }

    private List<Dependency> getControllerServiceDependency(List<JavaNode> springJavaNodeList) {
        List<Dependency> dependencies = new ArrayList<>();
        List<JavaNode> springControllerJavaNodeList = new ArrayList<>();
        List<JavaNode> springServiceJavaNodeList = new ArrayList<>();

        for(JavaNode javaNode : springJavaNodeList) {
            if(containSpringAnnotations(javaNode, Resource.SPRING_CONTROLLER_ANNOTATION_QUALIFIED_NAME)) {
                springControllerJavaNodeList.add(javaNode);
            }
            if(containSpringAnnotations(javaNode, Resource.SPRING_MVC_SERVICE_QUALIFIED_NAME)) {
                springServiceJavaNodeList.add(javaNode);
            }
        }

        dependencies.addAll(findControllerServiceDependency(springControllerJavaNodeList, springServiceJavaNodeList));
        return dependencies;
    }

    private List<Dependency> findControllerServiceDependency(List<JavaNode> springControllerJavaNodeList,  List<JavaNode> springServiceJavaNodeList) {
        List<Dependency> dependencies = new ArrayList<>();



        return dependencies;
    }

    private List<JavaNode> convertSpringNodes(List<JavaNode> javaNodeList) {

        List<JavaNode> springJavaNodeList = new ArrayList<>();
        for (JavaNode javaNode : javaNodeList) {
            if(containSpringAnnotations(javaNode, Resource.SPRING_ANNOTATION_QUALIFIED_NAME)) {
                springJavaNodeList.add(javaNode);
            }
        }
        return springJavaNodeList;
    }

    private Boolean containSpringAnnotations(JavaNode javaNode, List<String> conditionState) {
        for(JavaAnnotation javaAnnotation : javaNode.getAnnotates()) {
            if(conditionState.contains(javaAnnotation.getName())) {
                return true;
            }
        }
        return false;
    }
}
