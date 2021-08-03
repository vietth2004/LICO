package com.example.parserservice.controller;

import com.example.parserservice.model.Path;
import com.example.parserservice.model.Response;
import com.example.parserservice.service.ParserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
public class ParserController {


    private final ParserService parserService;


    public ParserController(ParserService parserService) {
        this.parserService = parserService;
    }


    @PostMapping("api/parse/file")
    public Response parseProjectToRootNodeByFile (@RequestParam(name="parser") List<String> parserList, @RequestBody MultipartFile file) throws IOException {
        return parserService.build(parserList, file);
    }

    @PostMapping("api/parse/path")
    public Response parseProjectToRootNodeByPath (@RequestParam(name="parser") List<String> parserList, @RequestBody Path path) throws IOException {
        return parserService.build(parserList, path);
    }
}
