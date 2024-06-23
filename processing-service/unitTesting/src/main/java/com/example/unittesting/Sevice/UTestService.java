package com.example.unittesting.Sevice;

import com.example.unittesting.utils.testing.NTDTesting;
import com.example.unittesting.utils.testing.PairwiseTesting.PairwiseTesting;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface UTestService {

    public ResponseEntity<Object> runAutomationTest(int targetId, String nameProject, PairwiseTesting.Coverage coverage) throws IOException;

}
