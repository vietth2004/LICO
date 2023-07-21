package com.example.unittesting.Sevice;

import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface UTestService {
    public ResponseEntity<Object> build(String path) throws IOException;
}
