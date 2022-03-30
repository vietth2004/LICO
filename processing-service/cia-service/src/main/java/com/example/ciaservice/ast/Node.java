package com.example.ciaservice.ast;

public class Node {
    private Integer id;

    private Integer weight;

    public Node() {
    }

    public Node(Integer node, Integer weight) {
        this.id = node;
        this.weight = weight;
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
}
