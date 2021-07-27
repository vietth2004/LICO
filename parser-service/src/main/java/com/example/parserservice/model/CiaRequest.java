package com.example.parserservice.model;

import com.example.parserservice.ast.dependency.Dependency;

import java.util.ArrayList;
import java.util.List;

public class CiaRequest {
    private List<Dependency> allDependencies = new ArrayList<>();

    private Integer totalNodes;

    public CiaRequest() {
    }

    public CiaRequest(List<Dependency> allDependencies, Integer totalNodes) {
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
