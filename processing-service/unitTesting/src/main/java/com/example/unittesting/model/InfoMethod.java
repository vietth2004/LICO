package com.example.unittesting.model;

import com.example.unittesting.ast.Node.MethodNode;
import com.example.unittesting.ast.Node.Parameter;
import com.fasterxml.jackson.annotation.JsonProperty;
import mrmathami.cia.java.tree.annotate.JavaAnnotate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InfoMethod extends MethodNode implements Serializable{

    @JsonProperty("content")
    private StringBuilder content;

    private List<Parameter>  parameters;

    public InfoMethod() {
    }

    public InfoMethod(int id, String name, List children, String path, String qualifiedName, StringBuilder content, List  parameters) {
        super(id, name, children, path, qualifiedName);
        this.content = content;
        this.parameters = parameters;
    }

    public InfoMethod(int id, String name, ArrayList<Object> children, String path, String qualifiedName, String uniqueName, StringBuilder content) {
        super(id, name, children, path, qualifiedName, uniqueName);
        this.content = content;
    }

    public StringBuilder getContent() {
        return content;
    }

    public void setContent(StringBuilder content) {
        this.content = content;
    }

    public List getParameters() {
        return parameters;
    }

    public void setParameters(List parameters) {
        this.parameters = parameters;
    }
}
