package com.example.fileservice.model;

public class FileResponse {

    private String content;

    public FileResponse() {
    }

    public FileResponse(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
