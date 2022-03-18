package com.example.parserservice.model.spring;

import com.example.parserservice.ast.dependency.Dependency;

import java.util.List;

public class DependencyResponse {

    private List<Dependency> allDependencies;

    public DependencyResponse() {
    }

    public DependencyResponse(List allDependencies) {
        this.allDependencies = allDependencies;
    }

    public List<Dependency> getAllDependencies() {
        return allDependencies;
    }

    public void setAllDependencies(List<Dependency> allDependencies) {
        this.allDependencies = allDependencies;
    }

}
