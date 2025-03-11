package com.example.ciaservice.ast;

public class Node {

    private String uniqueName;
    private Integer id;

    private Integer weight;

    public Node() {
    }

    public Node(Integer node, Integer weight) {
        this.id = node;
        this.weight = weight;
    }

    public Node(Integer node, Integer weight, String uniqueName) {
        this.id = node;
        this.weight = weight;
        this.uniqueName = uniqueName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }
}
