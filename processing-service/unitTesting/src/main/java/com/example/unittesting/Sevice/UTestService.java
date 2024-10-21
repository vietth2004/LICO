package com.example.unittesting.Sevice;

import core.testGeneration.NTDTestGeneration.NTDPairwiseTesting.NTDPairwiseTesting;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface UTestService {

    public ResponseEntity<Object> runAutomationTest(int targetId, String nameProject, NTDPairwiseTesting.Coverage coverage) throws IOException;

}
