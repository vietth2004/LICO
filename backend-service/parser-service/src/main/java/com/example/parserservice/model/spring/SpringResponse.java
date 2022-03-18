package com.example.parserservice.model.spring;

import com.example.parserservice.ast.dependency.Dependency;

import java.util.List;

public class SpringResponse {

    private List<Dependency> allDependencies;

    public SpringResponse() {
    }

    public SpringResponse(List allDependencies) {
        this.allDependencies = allDependencies;
    }

    public List<Dependency> getAllDependencies() {
        return allDependencies;
    }

    public void setAllDependencies(List<Dependency> allDependencies) {
        this.allDependencies = allDependencies;
    }

}
