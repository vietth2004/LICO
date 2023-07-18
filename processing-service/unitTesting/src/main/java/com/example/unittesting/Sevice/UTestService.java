package com.example.unittesting.Sevice;

import com.example.unittesting.model.Response;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface UTestService {
    public ResponseEntity<Response> build(String path) throws IOException;
}
