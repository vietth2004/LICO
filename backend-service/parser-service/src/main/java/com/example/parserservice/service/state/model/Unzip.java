package com.example.parserservice.service.state.model;

public class Unzip {
    private boolean progress;
    private double extractedSize;

    public Unzip(boolean progress, double  extractedSize) {
        this.progress = progress;
        this.extractedSize = extractedSize;
    }
    public Unzip(){

    }

    public boolean isProgress() {
        return progress;
    }

    public double getExtractedSize() {
        return extractedSize;
    }
}
