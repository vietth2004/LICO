package com.example.parserservice.controller;

import com.example.parserservice.ast.node.JavaNode;
import com.example.parserservice.model.Resource;
import com.example.parserservice.model.Response;
import com.example.parserservice.service.ParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@RestController
public class ParserController {


    private final ParserService parserService;


    public ParserController(ParserService parserService) {
        this.parserService = parserService;
    }


    @PostMapping("api/parse")
    public Response parseProjectToRootNode (@RequestParam(name="parser") List<String> parserList, @RequestBody MultipartFile file) {
        return parserService.build(parserList, file);
    }
}
