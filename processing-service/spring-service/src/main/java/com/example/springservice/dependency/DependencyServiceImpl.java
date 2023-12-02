package com.example.springservice.dependency;

import com.example.springservice.ast.node.JavaNode;
import com.example.springservice.dom.Node;
import com.example.springservice.utils.Analyzer;
import com.example.springservice.utils.worker.Converter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DependencyServiceImpl implements DependencyService{

    public List<Dependency> getDependencies(List<JavaNode> javaNodes, List<Node> xmlNodes) {
        List<JavaNode> springJavaNodes = Converter.convertSpringJavaNodes(javaNodes);
        List<Dependency> dependencies = new ArrayList<>();
        dependencies.addAll(Analyzer.getSpringDependency(springJavaNodes, javaNodes, xmlNodes));

        return dependencies;
    }
}
