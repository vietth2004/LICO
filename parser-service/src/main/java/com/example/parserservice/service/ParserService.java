package com.example.parserservice.service;

import com.example.parserservice.model.Path;
import com.example.parserservice.model.jsf.JSFResponse;
import com.example.parserservice.model.parser.Request;
import com.example.parserservice.model.Response;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ParserService {

    Request buildProject(MultipartFile file) throws IOException;

    Response build(List<String> parser, MultipartFile file, String user, String project) throws IOException;

    Response build(List<String> parser, Path path) throws IOException;

    Request buildProject(Path path) throws IOException;

    JSFResponse buildJsf(Path path) throws IOException;
}
