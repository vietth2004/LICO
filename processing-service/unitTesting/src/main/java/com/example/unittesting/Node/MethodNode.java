package com.example.unittesting.Node;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MethodNode extends Node implements Serializable {

    private String qualifiedName;
    private String uniqueName;
    private ArrayList<Parameter> parameters;

    @JsonProperty("content")
    private StringBuilder content;


    public MethodNode(int id, String name, List children, String path, String qualifiedName) {
        super(id, name, "MethodJavaNode", children, path);
        this.qualifiedName = qualifiedName;
    }

    public MethodNode(int id, String name, List children, String path, String qualifiedName, String uniqueName, ArrayList<Parameter> parameters, StringBuilder content) {
        super(id, name, "MethodJavaNode", children, path);
        this.qualifiedName = qualifiedName;
        this.uniqueName = uniqueName;
        this.parameters = parameters;
        this.content = content;
    }

    public MethodNode(String qualifiedName, String uniqueName, ArrayList<Parameter> parameters, StringBuilder content) {
        this.qualifiedName = qualifiedName;
        this.uniqueName = uniqueName;
        this.parameters = parameters;
        this.content = content;
    }

    public MethodNode(int id, String simpleName, String entityClass, List children, String path, String qualifiedName, String uniqueName, ArrayList<Parameter> parameters, StringBuilder content) {
        super(id, simpleName, entityClass, children, path);
        this.qualifiedName = qualifiedName;
        this.uniqueName = uniqueName;
        this.parameters = parameters;
        this.content = content;
    }

    public MethodNode(int id, String path, String qualifiedName, String uniqueName, ArrayList<Parameter> parameters, StringBuilder content) {
        super(id, path);
        this.qualifiedName = qualifiedName;
        this.uniqueName = uniqueName;
        this.parameters = parameters;
        this.content = content;
    }

    public void setQualifiedName(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }



    public String getQualifiedName() {
        return qualifiedName;
    }

    public String getUniqueName() {
        return uniqueName;
    }


    public StringBuilder getContent() {
        return content;
    }


    public ArrayList<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<Parameter> parameters) {
        this.parameters = parameters;
    }

    public void setContent(StringBuilder content) {
        this.content = content;
    }

}
