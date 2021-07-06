package com.example.springservice.dependency;

import com.example.springservice.ast.node.JavaNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

public class DependencyController {

    @Autowired
    private final DependencyService dependencyService;

    public DependencyController(DependencyService dependencyService) {
        this.dependencyService = dependencyService;
    }

    @PostMapping("/api/dependency/spring")
    public List<Dependency> getAllSpringDependencies(List<JavaNode> javaNodeList) {
        return dependencyService.getAllDependency(javaNodeList);
    }
}
