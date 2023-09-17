package com.example.unittesting.Sevice;

import com.example.unittesting.Sevice.project.ProjectService;
import com.example.unittesting.ast.Node.Parameter;
import com.example.unittesting.model.DataTest;
import com.example.unittesting.model.InfoMethod;
import com.example.unittesting.model.Response;
import com.example.unittesting.model.result.Concolic.ConcolicTestResult;
import com.example.unittesting.utils.JwtUtils;
import com.example.unittesting.utils.testing.ConcolicTesting;
import com.example.unittesting.utils.worker.Getter;
import com.example.unittesting.utils.worker.Writer;
import com.example.unittesting.utils.worker.findNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service

public class UTestServiceImpl implements UTestService {
    private static int testIdCounter = 1;
    final ProjectService projectService;

    final JwtUtils jwtUtils;

    public UTestServiceImpl(ProjectService projectService, JwtUtils jwtUtils, ConcolicTesting appStart) {
        this.projectService = projectService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public ResponseEntity<Object> build(String path) throws IOException {
        Response response = new Response();
        response = Getter.getResponse(path);
        Writer.write(path, response, "tmp-prjt");
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Override
    public String buildProject(List<String> parser, MultipartFile file, String user, String project) throws IOException{
        String userPath = user;
        if (!userPath.equals("anonymous")) {
            userPath = jwtUtils.extractUsername(user);
        }
        long storeStartTime = System.currentTimeMillis();
        String fileName = projectService.storeFile(file, userPath, project);
        return fileName;
    }
    @Override
    public ResponseEntity<Object> saveDataTest(InfoMethod requestMethod) {
        try{
            int idmethod = requestMethod.getId();
            // Lưu chuỗi JSON vào tệp tin
            String path = requestMethod.getPath();
            int zipProjectIndex = path.indexOf(".zip.project");
            String targetDirectoryName = path.substring(0, zipProjectIndex + ".zip.project".length());
            String outputFolderPath = targetDirectoryName + File.separator;
            File jsonFile = new File(outputFolderPath +"/tmp-prjt.json");
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode data = objectMapper.readTree(jsonFile);
            JsonNode nodeWithId = findNode.getNodeById(idmethod, data.get("rootNode"));
            String simpleName = nodeWithId.get("simpleName").asText();
            //System.out.println(simpleName);
            String entityClass = nodeWithId.get("entityClass").asText();
            String qualifiedName = nodeWithId.get("qualifiedName").asText();
            String uniqueName = nodeWithId.get("uniqueName").asText();
            int openingParenthesisIndex = simpleName.indexOf("(");
            String name = simpleName.substring(0, openingParenthesisIndex).trim();

            StringBuilder content = new StringBuilder();
            CompilationUnit compilationUnit = StaticJavaParser.parse(new File(path));
            List<com.github.javaparser.ast.body.MethodDeclaration> methodDeclarations = compilationUnit.findAll(MethodDeclaration.class);
            for (MethodDeclaration methodDeclaration : methodDeclarations) {
                String methodname = methodDeclaration.getSignature().asString();
                //System.out.println(methodName);
                if(methodname.equals(simpleName)){
                    content = new StringBuilder(methodDeclaration.toString());
                }
            }
            List<Parameter> parameters = new ArrayList<>();
            List<LinkedHashMap<String, String>> parameterMaps = requestMethod.getParameters();

            for (LinkedHashMap<String, String> parameterMap : parameterMaps) {
                String nameParameter = parameterMap.get("nameParameter");
                String describe = parameterMap.get("describe");
                String value = parameterMap.get("value");

                Parameter parameter = new Parameter(nameParameter, describe, value);
                parameters.add(parameter);
            }
            StringBuilder parameterTypes = new StringBuilder();
            for (Parameter parameter : parameters) {
                parameterTypes.append(parameter.getDescribe()).append("_");
                System.out.println(parameter +"\n");
            }
            parameterTypes.deleteCharAt(parameterTypes.length() - 1); // Xóa dấu "_" cuối cùng

            int testId = testIdCounter++;
            String nameTest = name +idmethod + "_" + parameterTypes.toString() +"_"+testId;
            LocalDateTime localDateTime = LocalDateTime.now();
            String currentTime = localDateTime.toString();

            requestMethod.setSimpleName(name);
            requestMethod.setQualifiedName(qualifiedName);
            requestMethod.setUniqueName(uniqueName);
            requestMethod.setEntityClass(entityClass);
            requestMethod.setContent(content);
            DataTest dataTest = new DataTest(nameTest, testId, "not executed", requestMethod, currentTime, "");
            String json = objectMapper.writeValueAsString(dataTest);

            // Lưu chuỗi JSON vào tệp tin


            File outputFolder = new File(outputFolderPath);
            if (!outputFolder.exists()) {
                outputFolder.mkdirs(); // Tạo thư mục nếu chưa tồn tại
            }

            String outputFilePath = outputFolderPath + nameTest + ".json";
            File outputFile = new File(outputFilePath);
            FileWriter writer = new FileWriter(outputFile);
            writer.write(json);
            writer.close();

            // Đối tượng ResponseEntity cho kết quả
            return ResponseEntity.ok("Expect value has been set and saved successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý lỗi nếu cần
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request. Exception: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> getRunFullConcolic(int targetId, String nameProject) throws IOException {
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
                        int openingParenthesisIndex = simpleName.indexOf("(");
                        String name = simpleName.substring(0, openingParenthesisIndex).trim();
                        File file = new File(pathMethod);
                        String className = file.getName();
                        ConcolicTestResult result = ConcolicTesting.runFullConcolic(pathMethod, name, className);
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