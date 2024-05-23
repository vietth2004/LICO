package com.example.parserservice.controller;

import com.example.parserservice.model.Path;
import com.example.parserservice.model.Response;
import com.example.parserservice.model.newResponse.NewResponse;
import com.example.parserservice.model.newResponse.Node;
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
    public NewResponse parseProjectToRootNodeByFile (@RequestParam(name="parser") List<String> parserList,
                                                  @RequestBody MultipartFile file,
                                                  @RequestParam(name="user", required = false, defaultValue = "anonymous") String user,
                                                  @RequestParam(name="project", required = false, defaultValue = "tmp-prj") String project) throws IOException {

        long start = System.currentTimeMillis();
        long memoryBefore = getUsedMemory();
        Response response = parserService.build(parserList, file, user, project);
        NewResponse newResponse = Node.convertToNewResponse(response);
        long end = System.currentTimeMillis();
        long memoryAfter = getUsedMemory();
        System.out.println("Total time is " + (end-start));
        System.out.println("Total memory is " + (memoryAfter-memoryBefore));

        return newResponse;
    }

    private long getUsedMemory()
    {
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
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
