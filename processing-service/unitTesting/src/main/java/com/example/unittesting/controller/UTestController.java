package com.example.unittesting.controller;

import com.example.unittesting.Sevice.UTestService;
import com.example.unittesting.model.Request;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;


@SpringBootApplication
@RestController
@RequestMapping("/api/unit-testing-service")
public class UTestController {
   private final UTestService utestService;

    public UTestController(UTestService utestService) {
        this.utestService = utestService;
    }


    @GetMapping("/is-running")
    public String running() {
        return "Hi there, I am still alive";
    }

//   @PostMapping("/unit-testing/source-code")
//    public ResponseEntity<Response> NodeByPath (@RequestBody Request request) throws IOException {
//        //String path = "project\\anonymous\\tmp-prj\\hiiii-v1.0.zip.project";
//       File file = new File(request.getPath());
//       if (!file.exists()) {
//           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//       }
//      return utestService.build(file.getAbsolutePath());
//    }
@PostMapping("/unit-testing/source-code")
public ResponseEntity<Object> NodeByPath (@RequestBody Request request) throws IOException {
    File file = new File(request.getPath());
    String path = file.getAbsolutePath();

    String checkJson = ".json";
    if (!path.contains(checkJson)) {
        utestService.build(path);
        path += "\\tmp-prjt.json";
    }

    JSONParser jsonParser = new JSONParser();
    FileReader fileReader = new FileReader(path);
    Object analysisFile;
    try {
        Object obj = jsonParser.parse(fileReader);
        analysisFile = obj;
    } catch (ParseException e) {
        throw new RuntimeException(e);
    }

    return ResponseEntity.ok(analysisFile);
}

//    @GetMapping("/unit-testing/source-code")
//    public ResponseEntity<Response> NodeByPath () throws IOException {
//        String path = "project\\anonymous\\tmp-prj\\Spring-Petclinic-master-v1.0.zip.project";
//        return utestService.build(path);
//    }
}
