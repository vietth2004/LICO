package com.example.parserservice.model;

import com.example.parserservice.ast.node.JavaNode;

import java.util.List;


public class Response {

    private JavaNode javaModel;

    private List Dependency;

    public Response() {
    }

    public Response(JavaNode javaModel, List dependency) {
        this.javaModel = javaModel;
        Dependency = dependency;
    }

    public JavaNode getJavaModel() {
        return javaModel;
    }

    public void setJavaModel(JavaNode javaModel) {
        this.javaModel = javaModel;
    }

    public List getDependency() {
        return Dependency;
    }

    public void setDependency(List dependency) {
        Dependency = dependency;
    }
}
