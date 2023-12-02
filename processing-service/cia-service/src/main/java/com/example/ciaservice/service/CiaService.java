package com.example.ciaservice.service;

import com.example.ciaservice.ast.Dependency;
import com.example.ciaservice.ast.JavaNode;
import com.example.ciaservice.model.Response;

import java.util.List;

public interface CiaService {

    public Response calculate(List<Dependency> dependencies, Integer totalNodes);

    public Response findImpact(List<JavaNode> javaNodes, List<Dependency> dependencies, Integer totalNodes, List<Integer> changedNodes);
}
