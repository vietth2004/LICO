package com.example.projectservice.version;


import com.example.projectservice.model.NamedEntity;
import com.example.projectservice.project.Project;

import javax.persistence.*;

@Entity
@Table(name = "version")
public class Version extends NamedEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "path")
    private String path = new String();

    @Column(name = "file")
    private String file = new String();

    public Version() {
    }

    public Version(String id, String name, String path) {
        super(id, name);
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getProject() {
        return project.getId();
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
