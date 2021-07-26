package com.example.parserservice.service;

import com.example.parserservice.ast.node.JavaNode;
import com.example.parserservice.model.Path;
import com.example.parserservice.model.Request;
import com.example.parserservice.model.Response;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ParserService {

    List parse (String parser, List javaNodes);

    Request buildProject(MultipartFile file) throws IOException;

    Response build(List<String> parser, MultipartFile file) throws IOException;

    Response build(List<String> parser, Path path) throws IOException;

    Request buildProject(Path path) throws IOException;
}
