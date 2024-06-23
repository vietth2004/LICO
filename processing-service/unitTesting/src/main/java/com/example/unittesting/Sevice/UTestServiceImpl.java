package com.example.unittesting.Sevice;


import com.example.unittesting.utils.testing.NTDTesting;
import com.example.unittesting.utils.testing.PairwiseTesting.PairwiseTesting;
import core.testResult.result.autoTestResult.TestResult;

import com.example.unittesting.utils.findNode;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.io.File;

import java.io.IOException;


@Service

public class UTestServiceImpl implements UTestService {
    @Override
    public ResponseEntity<Object> runAutomationTest(int targetId, String nameProject, PairwiseTesting.Coverage coverage) throws IOException {
        try {
            File jsonFile = new File("project/anonymous/tmp-prj/" + nameProject + "/tmp-prjt.json");
            if (!jsonFile.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Đường dẫn không tồn tại!");
            } else {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    JsonNode data = objectMapper.readTree(jsonFile);
                    JsonNode nodeWithId = findNode.getNodeById(targetId, data.get("rootNode"));
                    if (nodeWithId.get("entityClass").asText().equals("JavaMethodNode")) {
                        System.out.println(nodeWithId + "\n");
                        String simpleName = nodeWithId.get("simpleName").asText();
                        String pathMethod = nodeWithId.get("path").asText();
                        System.out.println(pathMethod);
                        int openingParenthesisIndex = simpleName.indexOf("(");
                        String name = simpleName.substring(0, openingParenthesisIndex).trim();
                        File file = new File(pathMethod);
                        String className = file.getName();

                        TestResult result = PairwiseTesting.runFullPairwise(pathMethod, name, className, coverage);

                        return ResponseEntity.ok(result);
                    } else {
                        System.out.println("Node with id not JavaMethodNode.\n");
                        return ResponseEntity.ok(nodeWithId);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error reading JSON file.\n");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reading JSON file.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error processing request. Exception: " + e.getMessage()+"\n");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request. Exception: " + e.getMessage());
        }
    }
}