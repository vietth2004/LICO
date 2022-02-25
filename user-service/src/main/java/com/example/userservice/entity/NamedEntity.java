package com.example.userservice.entity;

import javax.persistence.*;
import java.io.Serializable;


@MappedSuperclass
public abstract class NamedEntity extends Entity implements Serializable {


    @Column(name="name", unique = true)
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
