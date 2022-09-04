package com.example.versioncompareservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Path {

    private String path;

    public Path() {
    }

    public Path(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
