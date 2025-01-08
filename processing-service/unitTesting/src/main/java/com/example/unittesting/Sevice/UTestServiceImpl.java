package com.example.unittesting.Sevice;


import com.example.unittesting.model.MethodTest;
import com.fasterxml.jackson.databind.node.ArrayNode;
import core.testGeneration.ConcolicTestGeneration.ConcolicTesting;
import core.testGeneration.ConcolicTestGeneration.ConcolicTestingWithStub.AS4UT;
import core.testGeneration.TestGeneration;
import core.entity.ParameterInput;
import core.testResult.result.autoTestResult.TestResult;

import com.example.unittesting.utils.findNode;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service

public class UTestServiceImpl implements UTestService {
    private MethodTest methodTest;
    private List<ParameterInput> parameterInputs;

    @Override
    public ResponseEntity<Object> runAutomationTest(int targetId, String nameProject, TestGeneration.Coverage coverage) throws IOException {
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

                        // TEST TEMPLATE
//                        createMethodTest("project/anonymous/tmp-prj/" + nameProject + "/tmp-prjt.json", targetId);

                        TestResult result = AS4UT.runFullConcolic(pathMethod, name, className, coverage);

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

    private final String JAVA_METHOD_NODE = "JavaMethodNode";

    @Override
    public ResponseEntity<Object> runRegressionTest(String nameProject, TestGeneration.Coverage coverage) {
        try {
            String dirPath = "project/anonymous/tmp-prj/" + nameProject;
            File jsonFile = new File(dirPath + "/tmp-prj.json");
            if (!jsonFile.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Non exist file "+ jsonFile.getPath());
            } else {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    JsonNode data = objectMapper.readTree(jsonFile);
                    List<TestResult> finalResult = new ArrayList<>();

                    ArrayNode changedNodes = (ArrayNode) data.get("changedNodes");

                    for (int i = 0; i < changedNodes.size(); i++) {
                        JsonNode changeNode = changedNodes.get(i);

                        if (changeNode.get("entityClass").asText().equals(JAVA_METHOD_NODE)) {

                            String path = changeNode.get("path").asText();
                            String name = changeNode.get("simpleName").asText();
                            String className = (new File(path)).getName();

                            TestResult result = ConcolicTesting.runFullConcolic(path, name, className, coverage);
                            finalResult.add(result);
                        }

                        System.out.println("abc");
                    }

                    return ResponseEntity.ok(finalResult);
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

    public void createMethodTest(String path, Integer targerId) throws IOException {
        File file = new File(path);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodes = objectMapper.readTree(file);
        JsonNode rootNode = nodes.get("rootNode");
        NodeServiceImpl nodeService = new NodeServiceImpl(rootNode);
        JsonNode node = nodeService.findByIdNode(targerId);
        Integer idMethod = node.get("id").intValue();
        String nameMethod = node.get("simpleName").textValue();

        JsonNode parentNode = nodeService.findParentById(targerId);
//        String className = parentNode.get("simpleName").textValue() + "Test";
        String methodPath = node.get("path").textValue();
//        String qualifiedClassName = parentNode.get("qualifiedName").textValue();

//        methodTest = new MethodTest(idMethod, nameMethod, qualifiedClassName, methodPath);
        methodTest = new MethodTest(idMethod, nameMethod, "ABC", methodPath);
        nodeService.getParameterDependency(targerId);
        parameterInputs = nodeService.getParameterInputs();
    }


}