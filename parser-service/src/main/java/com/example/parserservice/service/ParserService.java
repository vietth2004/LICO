package com.example.parserservice.service;

import com.example.parserservice.ast.node.JavaNode;
import com.example.parserservice.model.Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ParserService {

    List parse (String parser, List javaNodes);

    JavaNode buildProject(MultipartFile file);

    public Response build(List<String> parser, MultipartFile file);
}
