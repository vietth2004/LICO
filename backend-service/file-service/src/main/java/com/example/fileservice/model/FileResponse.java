package com.example.fileservice.model;

import java.util.ArrayList;
import java.util.List;

public class FileResponse {

    private transient String content;

    private transient List<String> fileContent = new ArrayList<>();

    public FileResponse() {
    }

    public FileResponse(String content) {
        this.content = content;
    }

    public FileResponse(List<String> fileContent) {
        this.fileContent = fileContent;
    }

    public FileResponse(String content, List<String> fileContent) {
        this.content = content;
        this.fileContent = fileContent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getFileContent() {
        return fileContent;
    }

    public void setFileContent(List<String> fileContent) {
        this.fileContent = fileContent;
    }
}
