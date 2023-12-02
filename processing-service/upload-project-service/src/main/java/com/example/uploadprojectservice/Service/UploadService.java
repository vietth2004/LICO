package com.example.uploadprojectservice.Service;

import com.example.uploadprojectservice.ast.data.InfoMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UploadService {
    public ResponseEntity<Object> build(String path) throws IOException;

    public String buildProject(List<String> parser, MultipartFile file, String user, String project) throws IOException;

    public ResponseEntity<Object> saveDataTest(InfoMethod requestMethod);
}
