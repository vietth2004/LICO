package com.example.javaservice.ast.annotation;

import mrmathami.cia.java.jdt.project.tree.annotate.Annotate;

import java.util.ArrayList;
import java.util.List;


public class JavaAnnotation {
    private String entityClass = new String();
    private String idClass = new String();
    private Integer id;
    private String name = new String();
    private String value = new String();
    private List<MemberValuePair> memberValuePair = new ArrayList<>();

    public JavaAnnotation() {
    }

    public JavaAnnotation(Annotate annotate) {
        this.entityClass = annotate.getEntityClass();
        this.idClass = annotate.getIdClass();
        this.id = annotate.getId();
        this.name = annotate.getName();
    }

    public JavaAnnotation(String entityClass, String idClass, Integer id, String name, String value, List<MemberValuePair> memberValuePair) {
        this.entityClass = entityClass;
        this.idClass = idClass;
        this.id = id;
        this.name = name;
        this.value = value;
        this.memberValuePair = memberValuePair;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<MemberValuePair> getMemberValuePair() {
        return memberValuePair;
    }

    public void setMemberValuePair(List<MemberValuePair> memberValuePair) {
        this.memberValuePair = memberValuePair;
    }
}
