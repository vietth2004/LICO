package com.example.springservice.dependency;

import com.example.springservice.ast.node.JavaNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DependencyController {

    @Autowired
    private final DependencyService dependencyService;

    public DependencyController(DependencyService dependencyService) {
        this.dependencyService = dependencyService;
    }

    @PostMapping("/api/dependency/spring")
    public List<Dependency> getAllSpringDependencies(@RequestBody List<JavaNode> javaNodeList) {
        return dependencyService.getAllDependency(javaNodeList);
    }
}
