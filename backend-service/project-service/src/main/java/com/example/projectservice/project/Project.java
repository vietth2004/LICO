package com.example.projectservice.project;


import com.example.projectservice.entity.NamedEntity;
import com.example.projectservice.version.Version;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "project",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Project extends NamedEntity {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project", fetch = FetchType.EAGER)
    private List<Version> versionList = new ArrayList<>();

    private Boolean gitProject = false;

    @Column(name = "user")
    private String user;

    public Project() {
    }

    public Project(Integer id, String name, String type, String user) {
        super(id, name, type);
        this.user = user;
    }

    public Project(Integer id, String name, String type, List<Version> versionList, Boolean gitProject, String user) {
        super(id, name, type);
        this.versionList = versionList;
        this.gitProject = gitProject;
        this.user = user;
    }

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

    public Boolean getGitProject() {
        return gitProject;
    }

    public void setGitProject(Boolean gitProject) {
        this.gitProject = gitProject;
    }
}
