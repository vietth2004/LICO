package com.example.parserservice.controller;

import com.example.parserservice.model.Path;
import com.example.parserservice.model.Response;
import com.example.parserservice.service.ParserService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/parser-service/")
public class ParserController {

    private final ParserService parserService;

    public ParserController(ParserService parserService) {
        this.parserService = parserService;
    }


    @PostMapping("/parse/file")
    public Response parseProjectToRootNodeByFile (@RequestParam(name="parser") List<String> parserList,
                                                  @RequestBody MultipartFile file,
                                                  @RequestParam(name="user", required = false, defaultValue = "anonymous") String user,
                                                  @RequestParam(name="project", required = false, defaultValue = "tmp-prj") String project) throws IOException {
        System.out.println("1111111111111111111111");
        return parserService.build(parserList, file, user, project);
    }

    @PostMapping("/parse/path")
    public ResponseEntity<Object> parseProjectToRootNodeByPath (@RequestParam(name="parser") List<String> parserList,
                                                                    @RequestBody Path path) throws IOException {
        String checkJson = ".json";
        if(!path.getPath().contains(checkJson)) {
            parserService.build(parserList, path);
            path.setPath(path.getPath() + "/tmp-prj.json");
        }
        JSONParser jsonParser = new JSONParser();
        FileReader file = new FileReader(path.getPath());
        Object analysisFile;
        try {
            Object obj = jsonParser.parse(file);
            analysisFile =  obj;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(analysisFile);
//        return parserService.build(parserList, path);
    }
}
