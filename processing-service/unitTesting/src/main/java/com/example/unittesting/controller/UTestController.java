package com.example.unittesting.controller;

import com.example.unittesting.Sevice.UTestService;
import com.example.unittesting.ast.Node.Parameter;
import com.example.unittesting.model.InfoMethod;
import com.example.unittesting.utils.worker.findNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
@RestController
@RequestMapping("/api/unit-testing-service")
public class UTestController {
    private static int testIdCounter = 1;
    private final UTestService utestService;

    public UTestController(UTestService utestService) {
        this.utestService = utestService;

    }


    @GetMapping("/is-running")
    public String running() {
        return "Hi there, I am still alive";
    }
    @PostMapping("/process")
    public JsonNode process(@RequestParam(name = "parser") List<String> parserList,
                            @RequestBody MultipartFile file,
                            @RequestParam(name = "user", required = false, defaultValue = "anonymous") String user,
                            @RequestParam(name = "project", required = false, defaultValue = "tmp-prj") String project) throws IOException {
        if (file != null) {
            String path = utestService.buildProject(parserList, file, user, project);

            Object result = utestService.build(path);
            path += "\\tmp-prjt.json";
            File jsonFile = new File(path);

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode data = objectMapper.readTree(jsonFile);
                return data;
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Error reading JSON file: " + e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("File is null");
        }
    }


    @GetMapping(value = "/view-tree")
    public ResponseEntity<Object> NodeTree(@RequestParam String nameProject) {

        try {
            File file = new File("project/anonymous/tmp-prj/" + nameProject + "/tmp-prjt.json");
            if (!file.exists()) {
                // Xử lý khi tệp không tồn tại
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Đường dẫn không tồn tại!");
            } else {
                JSONParser jsonParser = new JSONParser();
                FileReader fileReader = new FileReader(file.getAbsolutePath());
                Object analysisFile;
                try {
                    Object obj = jsonParser.parse(fileReader);
                    analysisFile = obj;
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                // Trả về kết quả phân tích từ tệp JSON
                return ResponseEntity.ok(analysisFile);
            }
        } catch (IOException e) {
            // Xử lý ngoại lệ và trả về phản hồi lỗi
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi xảy ra trong quá trình xử lý yêu cầu: " + e.getMessage());
        }
    }
//    @PostMapping("/source-code")
//    public ResponseEntity<Object> NodeByPath (@RequestBody Request request) throws IOException {
//        try {
//            File file = new File(request.getPath());
//            String path = file.getAbsolutePath();
//            if (!file.exists()) {
//                // Xử lý khi tệp không tồn tại
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//            } else {
//                path += "\\tmp-prjt.json";
//                Object result = utestService.build(file.getAbsolutePath());
//                return ResponseEntity.ok(result);
//            }
//        } catch (IOException e) {
//            // Xử lý ngoại lệ và trả về phản hồi lỗi
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Lỗi xảy ra trong quá trình xử lý yêu cầu: " + e.getMessage());
//        }
//
//    }
    @GetMapping(value = "/read")
    public ResponseEntity<Object> getInfoMethod(@RequestParam int targetId, @RequestParam String nameProject) {
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
                        //System.out.println(simpleName);
                        String entityClass = nodeWithId.get("entityClass").asText();
                        String pathMethod = nodeWithId.get("path").asText();
                        List<String> children = new ArrayList<>();
                        JsonNode childrenNode = nodeWithId.get("children");
                        if (childrenNode.isArray()) {
                            for (JsonNode childNode : childrenNode) {
                                children.add(childNode.asText());
                            }
                        }
                        String qualifiedName = nodeWithId.get("qualifiedName").asText();
                        String uniqueName = nodeWithId.get("uniqueName").asText();
                        int openingParenthesisIndex = simpleName.indexOf("(");
                        String name = simpleName.substring(0, openingParenthesisIndex).trim();

                        StringBuilder content = new StringBuilder();
                        List<Parameter> parameters = new ArrayList<>();
                        CompilationUnit compilationUnit = StaticJavaParser.parse(new File(pathMethod));
                        List<com.github.javaparser.ast.body.MethodDeclaration> methodDeclarations = compilationUnit.findAll(MethodDeclaration.class);
                        for (MethodDeclaration methodDeclaration : methodDeclarations) {
                            String methodName = methodDeclaration.getSignature().asString();
                            //System.out.println(methodName);
                            if(methodName.equals(simpleName)){
                                content = new StringBuilder(methodDeclaration.toString());
                                for (com.github.javaparser.ast.body.Parameter parameter : methodDeclaration.getParameters()) {
                                    parameters.add(new Parameter(parameter.getNameAsString(), parameter.getType().asString()));
                                }
                                  String returnType = methodDeclaration.getType().asString();
                                  parameters.add(new Parameter("return", returnType));
                            }
                        }

                        InfoMethod infoMethod = new InfoMethod(
                                targetId,
                                name,
                                children,
                                pathMethod,
                                qualifiedName,
                                content,
                                parameters
                        );
                            return ResponseEntity.ok(infoMethod);
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

    @PostMapping(value = "/expect")
    public ResponseEntity<Object> setExpectValue(@RequestBody InfoMethod requestMethod){
        if (requestMethod != null) {
            try {
                ResponseEntity<Object> response = utestService.saveDataTest(requestMethod);
                return response;
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request. Exception: " + e.getMessage());
            }
        } else {
            return ResponseEntity.badRequest().body("Request body is empty.");
        }
    }
    @GetMapping(value = "/unit")
    public ResponseEntity<Object> getUnitTest(@RequestParam int targetId, @RequestParam String nameProject) throws IOException {
        return ResponseEntity.ok(utestService.getRunFullConcolic(targetId, nameProject));
    }

}