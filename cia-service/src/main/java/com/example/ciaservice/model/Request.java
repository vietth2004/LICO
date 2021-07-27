package com.example.ciaservice.model;

import com.example.ciaservice.ast.Dependency;

import java.util.ArrayList;
import java.util.List;

public class Request {

    List<Dependency> dependencies = new ArrayList<>();

    public Request() {
    }

    public Request(List<Dependency> dependencies) {
        this.dependencies = dependencies;
    }

    public List<Dependency> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<Dependency> dependencies) {
        this.dependencies = dependencies;
    }
}
