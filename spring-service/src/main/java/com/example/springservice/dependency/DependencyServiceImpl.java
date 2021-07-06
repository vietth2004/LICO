package com.example.springservice.dependency;

import com.example.springservice.ast.node.JavaNode;
import com.example.springservice.ast.node.Node;
import com.example.springservice.ast.utility.Utility;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DependencyServiceImpl implements DependencyService{

    public List<Dependency> getAllDependency(List<JavaNode> javaNodeList) {
        List<JavaNode> springJavaNodeList = Utility.convertSpringNodes(javaNodeList);
        List<Dependency> dependencies = new ArrayList<>();


        return dependencies;
    }


}
