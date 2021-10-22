package com.example.fileservice.service;

import java.io.IOException;

public interface FileService {

    String readFile(String address) throws IOException;
}
