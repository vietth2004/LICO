package com.example.projectservice.entity;

import javax.persistence.*;

@MappedSuperclass
public abstract class NamedEntity extends Entity{

    @Column(name="name")
    private String name;

    @Column(name="type")
    private String type;

    public NamedEntity() {
    }

    public NamedEntity(Integer id, String name, String type) {
        super(id);
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
