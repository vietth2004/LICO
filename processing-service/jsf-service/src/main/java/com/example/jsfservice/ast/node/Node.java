package com.example.jsfservice.ast.node;

import mrmathami.cia.java.jdt.tree.node.AbstractNode;

public class Node {

    private String entityClass = new String();
    private String idClass = new String();
    private Integer id;
    private String qualifiedName = new String();
    private String uniqueName = new String();
    private String simpleName = new String();

    public Node() {
    }

    public Node(Integer id) {
        this.id = id;
    }

    public Node(String entityClass, String idClass, Integer id) {
        this.entityClass = entityClass;
        this.idClass = idClass;
        this.id = id;
    }

    public Node(AbstractNode abstractNode) {
        this.entityClass = abstractNode.getEntityClass();
        this.idClass = abstractNode.getIdClass();
        this.id = abstractNode.getId();
        this.qualifiedName = abstractNode.getQualifiedName();
        this.uniqueName = abstractNode.getUniqueName();
        this.simpleName = abstractNode.getSimpleName();
    }

    public Node(String entityClass, String idClass, Integer id, String qualifiedName, String uniqueName, String simpleName) {
        this.entityClass = entityClass;
        this.idClass = idClass;
        this.id = id;
        this.qualifiedName = qualifiedName;
        this.uniqueName = uniqueName;
        this.simpleName = simpleName;
    }

    public String getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(String entityClass) {
        this.entityClass = entityClass;
    }

    public String getIdClass() {
        return idClass;
    }

    public void setIdClass(String idClass) {
        this.idClass = idClass;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQualifiedName() {
        return qualifiedName;
    }

    public void setQualifiedName(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

}
