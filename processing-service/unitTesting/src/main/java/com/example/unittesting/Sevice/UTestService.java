package com.example.unittesting.Sevice;

import com.example.unittesting.utils.testing.ConcolicTesting;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface UTestService {

    public ResponseEntity<Object> getRunFullConcolic(int targetId, String nameProject, ConcolicTesting.Coverage coverage) throws IOException;

}
