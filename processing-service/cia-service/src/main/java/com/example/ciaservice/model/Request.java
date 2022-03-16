package com.example.ciaservice.model;

import com.example.ciaservice.ast.Dependency;

import java.util.ArrayList;
import java.util.List;

public class Request {

    private List<Dependency> allDependencies = new ArrayList<>();

    private Integer totalNodes;

    public Request() {
    }

    public Request(List<Dependency> allDependencies, Integer totalNodes) {
        this.allDependencies = allDependencies;
        this.totalNodes = totalNodes;
    }

    public List<Dependency> getAllDependencies() {
        return allDependencies;
    }

    public void setAllDependencies(List<Dependency> allDependencies) {
        this.allDependencies = allDependencies;
    }

    public Integer getTotalNodes() {
        return totalNodes;
    }

    public void setTotalNodes(Integer totalNodes) {
        this.totalNodes = totalNodes;
    }
}
