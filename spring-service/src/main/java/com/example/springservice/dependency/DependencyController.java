package com.example.springservice.dependency;

import com.example.springservice.ast.node.JavaNode;
import com.example.springservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/spring-service/")
public class DependencyController {

    @Autowired
    private final DependencyService dependencyService;

    public DependencyController(DependencyService dependencyService) {
        this.dependencyService = dependencyService;
    }

    @PostMapping("/dependency/spring/response")
    public List<Dependency> getAllSpringDependencies(@RequestBody List<JavaNode> javaNodeList) {
        return dependencyService.getAllDependency(javaNodeList);
    }

    @PostMapping("/dependency/spring")
    public Response returnResponse(@RequestBody List<JavaNode> javaNodeList) {
        return new Response(dependencyService.getAllDependency(javaNodeList));
    }
}
