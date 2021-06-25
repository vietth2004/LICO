package com.example.projectservice.project;


import com.example.projectservice.model.NamedEntity;
import com.example.projectservice.version.Version;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "project")
public class Project extends NamedEntity {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project", fetch = FetchType.EAGER)
    private List<Version> versionList = new ArrayList<>();

    @Column(name = "user")
    private String user;

    public List<Version> getVersionList() {
        return versionList;
    }

    public void setVersionList(List<Version> versionList) {
        this.versionList = versionList;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
