package com.example.springservice.dependency;

import com.example.springservice.ast.annotation.JavaAnnotation;
import com.example.springservice.ast.dependency.DependencyCountTable;
import com.example.springservice.ast.dependency.Pair;
import com.example.springservice.ast.node.JavaNode;
import com.example.springservice.ast.type.JavaType;
import com.example.springservice.dom.Node;
import com.example.springservice.dom.Xml.XmlTagNode;
import com.example.springservice.resource.Resource;
import com.example.springservice.utils.Analyzer;
import com.example.springservice.utils.Getter;
import com.example.springservice.utils.Searcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DependencyServiceImpl implements DependencyService{

    public List<Dependency> getAllDependency(List<JavaNode> javaNodes, List<Node> xmlTagNodes) {
        List<JavaNode> springJavaNodes = Analyzer.convertSpringJavaNodes(javaNodes);
        List<Node> springXmlNodes = Analyzer.convertSpringXmlNodes(xmlTagNodes);
        List<Dependency> dependencies = new ArrayList<>();
        dependencies.addAll(getSpringDependency(springJavaNodes, javaNodes, springXmlNodes));

        return dependencies;
    }

    private List<Dependency> getSpringDependency(List<JavaNode> springJavaNodes, List<JavaNode> javaNodes, List<Node> xmlTagNodes) {
        List<Dependency> dependencies = new ArrayList<>();
        List<JavaNode> springControllerJavaNodes = new ArrayList<>();
        List<JavaNode> springServiceJavaNodes = new ArrayList<>();
        List<JavaNode> springRepositoryJavaNodes = new ArrayList<>();

        // Gather each Spring Java class
        for(JavaNode javaNode : springJavaNodes) {
            if(Analyzer.containSpringAnnotations(javaNode, Resource.SPRING_MVC_CONTROLLER_SIMPLE_NAME)) {
                springControllerJavaNodes.addAll(Analyzer.gatherControllerNode(javaNodes, javaNode));
            }
            if(Analyzer.containSpringAnnotations(javaNode, Resource.SPRING_MVC_SERVICE_SIMPLE_NAME)) {
                springServiceJavaNodes.addAll(Analyzer.gatherDaoNode(javaNodes, javaNode));
            }
            if(Analyzer.containSpringAnnotations(javaNode, Resource.SPRING_MVC_REPOSITORY_SIMPLE_NAME)
                    || Analyzer.isSpringInterface(javaNode, Resource.SPRING_REPOSITORY_INTERFACE_SIMPLE_NAME)) {
                springRepositoryJavaNodes.addAll(Analyzer.gatherDaoNode(javaNodes, javaNode));
            }
        }

        for(Node xmlNode : xmlTagNodes) {

        }

        //Add all dependencies
        dependencies.addAll(Analyzer.getControllerServiceDependency(springControllerJavaNodes, springServiceJavaNodes));
        dependencies.addAll(Analyzer.getServiceRepositoryDependency(springServiceJavaNodes, springRepositoryJavaNodes));
        dependencies.addAll(Analyzer.getControllerRepositoryDependency(springControllerJavaNodes, springRepositoryJavaNodes));

        return dependencies;
    }

    private void print (List<JavaNode> springControllerJavaNodes,
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
