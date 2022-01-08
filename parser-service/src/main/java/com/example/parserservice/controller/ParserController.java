package com.example.parserservice.controller;

import com.example.parserservice.model.Path;
import com.example.parserservice.model.Response;
import com.example.parserservice.service.ParserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
                                                  @RequestParam(name="user", required = false, defaultValue = "anonymous") String user) throws IOException {
        return parserService.build(parserList, file, user);
    }

    @PostMapping("/parse/path")
    public Response parseProjectToRootNodeByPath (@RequestParam(name="parser") List<String> parserList,
                                                  @RequestBody Path path) throws IOException {
        return parserService.build(parserList, path);
    }
}
