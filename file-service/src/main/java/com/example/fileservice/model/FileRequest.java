package com.example.fileservice.model;

public class FileRequest {
    private String address;

    public FileRequest() {
    }

    public FileRequest(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
