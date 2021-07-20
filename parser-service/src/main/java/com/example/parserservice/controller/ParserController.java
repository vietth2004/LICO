package com.example.parserservice.controller;

import com.example.parserservice.model.Response;
import com.example.parserservice.service.ParserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ParserController {


    private final ParserService parserService;

    public ParserController(ParserService parserService) {
        this.parserService = parserService;
    }


    @PostMapping("api/parse")
    public Response parseProjectToRootNode () {


        return new Response();
    }
}
