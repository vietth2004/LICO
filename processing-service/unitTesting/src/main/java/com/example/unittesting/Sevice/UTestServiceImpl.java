package com.example.unittesting.Sevice;

import com.example.unittesting.Sevice.project.ProjectService;
import com.example.unittesting.ast.Node.Parameter;
import com.example.unittesting.model.DataTest;
import com.example.unittesting.model.InfoMethod;
import com.example.unittesting.model.Response;
import com.example.unittesting.util.JwtUtils;
import com.example.unittesting.util.worker.Getter;
import com.example.unittesting.util.worker.Writer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service

public class UTestServiceImpl implements UTestService {
    private static int testIdCounter = 1;
    final ProjectService projectService;

    final JwtUtils jwtUtils;

    public UTestServiceImpl(ProjectService projectService, JwtUtils jwtUtils) {
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
            String methodName = requestMethod.getSimpleName();
            int idmethod = requestMethod.getId();
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
            String nameTest = methodName +idmethod + "_" + parameterTypes.toString() +"_"+testId;
            LocalDateTime localDateTime = new LocalDateTime();
            String currentTime = localDateTime.toString();
            DataTest dataTest = new DataTest(nameTest, testId, "not implemented", requestMethod, currentTime, new ArrayList<>());
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(dataTest);

            // Lưu chuỗi JSON vào tệp tin
            String path = requestMethod.getPath();
            int zipProjectIndex = path.indexOf(".zip.project");
            String targetDirectoryName = path.substring(0, zipProjectIndex + ".zip.project".length());
            String outputFolderPath = targetDirectoryName + File.separator;

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
}