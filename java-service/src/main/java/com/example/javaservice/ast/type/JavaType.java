package com.example.javaservice.ast.type;

import com.example.javaservice.ast.annotation.JavaAnnotation;
import com.example.javaservice.ast.node.Node;
import com.example.javaservice.ast.utility.Utility;
import mrmathami.cia.java.jdt.tree.type.AbstractType;
import mrmathami.cia.java.jdt.tree.type.SimpleType;

import java.util.ArrayList;
import java.util.List;

public class JavaType {
    private String entityClass = new String();
    private String idClass = new String();
    private Integer id;
    private String describe = new String();
    private List<JavaAnnotation> annotates = new ArrayList<>();

    private JavaType innerType = new JavaType();
    private List<JavaType> arguments = null;
    private List<JavaType> bounds = null;

    private Node node = new Node();

    public JavaType() {
    }

    public JavaType(AbstractType abstractType) {
        this.entityClass = abstractType.getEntityClass();
        this.idClass = abstractType.getIdClass();
        this.id = abstractType.getId();
        this.describe = abstractType.getDescription();

        this.annotates = Utility.convertAnnotates(abstractType.getAnnotates());

        if(abstractType instanceof SimpleType) {
//            System.out.println(((SimpleType) abstractType).getInnerType().toJson());
        }

//        if(abstractType instanceof ReferenceType) {
//            this.arguments = ((ReferenceType) abstractType).getArguments();
//            this.node = ((ReferenceType) abstractType).getNode();
//        }

//        if(abstractType instanceof SyntheticType) {
//            this.bounds = ((SyntheticType) abstractType).getBounds();
//        }
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

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public JavaType getInnerType() {
        return innerType;
    }

    public void setInnerType(JavaType innerType) {
        this.innerType = innerType;
    }

    public List<JavaType> getArguments() {
        return arguments;
    }

    public void setArguments(List<JavaType> arguments) {
        this.arguments = arguments;
    }

    public List<JavaType> getBounds() {
        return bounds;
    }

    public void setBounds(List<JavaType> bounds) {
        this.bounds = bounds;
    }

    public List<JavaAnnotation> getAnnotates() {
        return annotates;
    }

    public void setAnnotates(List<JavaAnnotation> annotates) {
        this.annotates = annotates;
    }
}
