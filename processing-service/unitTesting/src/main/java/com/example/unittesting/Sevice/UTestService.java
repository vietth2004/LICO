package com.example.unittesting.Sevice;

import core.testGeneration.PairwiseTesting.PairwiseTesting;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface UTestService {

    public ResponseEntity<Object> runAutomationTest(int targetId, String nameProject, PairwiseTesting.Coverage coverage) throws IOException;

}
