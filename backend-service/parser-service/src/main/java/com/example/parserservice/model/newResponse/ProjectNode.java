package com.example.parserservice.model.newResponse;

import java.util.ArrayList;
import java.util.List;

public class ProjectNode {
    private String type = "ProjectNode";
    private Integer id;
    private ArrayList<Integer> children = new ArrayList<>();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ArrayList<Integer> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Integer> children) {
        this.children = children;
    }

    public void addChildren(Integer child) {
        children.add(child);
    }
}
