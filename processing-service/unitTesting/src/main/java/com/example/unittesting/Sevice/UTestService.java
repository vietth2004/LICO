package com.example.unittesting.Sevice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UTestService {

    public ResponseEntity<Object> getRunFullConcolic(int targetId, String nameProject) throws IOException;

}
