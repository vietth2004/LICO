package com.example.strutsservice.utils.communicator;

import com.example.strutsservice.ast.dependency.Dependency;

import java.util.List;

public class Response {

    private List<Dependency> allDependencies;

    public Response() {
    }

    public Response(List allDependencies) {
        this.allDependencies = allDependencies;
    }

    public List<Dependency> getAllDependencies() {
        return allDependencies;
    }

    public void setAllDependencies(List<Dependency> allDependencies) {
        this.allDependencies = allDependencies;
    }

}
