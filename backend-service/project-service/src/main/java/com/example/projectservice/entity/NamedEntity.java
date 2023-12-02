package com.example.projectservice.entity;

import javax.persistence.*;

@MappedSuperclass
public abstract class NamedEntity extends Entity {

    @Column(name = "name")
    private String name;

    public NamedEntity() {
    }

    public NamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
