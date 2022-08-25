package com.example.springservice.controller;

import com.example.springservice.ast.node.JavaNode;
import com.example.springservice.dependency.Dependency;
import com.example.springservice.dependency.DependencyService;
import com.example.springservice.dom.Node;
import com.example.springservice.model.Request;
import com.example.springservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
    public List<Dependency> getAllSpringDependencies(@RequestBody List<JavaNode> request) {
        long start = System.currentTimeMillis();
        List<Dependency> dependencyList = dependencyService.getDependencies(request, new ArrayList<Node>());
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        return dependencyList;
    }

    @PostMapping("/dependency/spring")
    public Response returnResponse(@RequestBody Request request) {
        long start = System.currentTimeMillis();
        List<Dependency> dependencyList = dependencyService.getDependencies(request.getJavaNodes(), request.getXmlNodes());
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        return new Response(dependencyList);
    }

    @PostMapping("/dependency/spring/new")
    public Response returnResponseWithXml(@RequestBody Request request) {
        long start = System.currentTimeMillis();
        List<Dependency> dependencyList = dependencyService.getDependencies(request.getJavaNodes(), request.getXmlNodes());
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        return new Response(dependencyList);
    }
}
