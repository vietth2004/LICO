package com.example.projectservice.version;


import com.example.projectservice.model.NamedEntity;
import com.example.projectservice.project.Project;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "version",
uniqueConstraints = @UniqueConstraint(columnNames = {"name", "id"}))
public class Version extends NamedEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pid", referencedColumnName = "id")
    private Project project;

    @Column(name = "path", unique = true)
    private String path = new String();

    @Column(name = "file", unique = true)
    private String file = new String();

    @Column(name = "date")
    private Date uploadDate = new Date();

    public Version() {
    }

    public Version(Integer id, String name, String path) {
        super(id, name);
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getProject() {
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

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date date) {
        this.uploadDate = date;
    }
}
