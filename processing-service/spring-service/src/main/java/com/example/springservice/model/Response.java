package com.example.springservice.model;

import java.util.List;

public class Response {

    private List allDependencies;

    public Response() {
    }

    public Response(List allDependencies) {
        this.allDependencies = allDependencies;
    }

    public List getAllDependencies() {
        return allDependencies;
    }

    public void setAllDependencies(List allDependencies) {
        this.allDependencies = allDependencies;
    }
}
