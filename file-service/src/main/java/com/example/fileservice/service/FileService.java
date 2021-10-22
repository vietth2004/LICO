package com.example.fileservice.service;

import com.example.fileservice.model.FileResponse;

import java.io.IOException;

public interface FileService {

    FileResponse readFile(String address) throws IOException;
}
