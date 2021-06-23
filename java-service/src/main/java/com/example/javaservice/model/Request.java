package com.example.javaservice.model;


public class Request {
    private String path = new String();

    public Request() {
    }

    public Request(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
