package com.example.parserservice.service;

import com.example.parserservice.model.*;
import com.example.parserservice.model.parser.Request;
import com.example.parserservice.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ParserServiceImpl implements ParserService{

    @Autowired
    private RestTemplate restTemplate;

    //**
    //Build Project with Multipart File
    //**
    @Override
    public Response build(List<String> parserList, MultipartFile file) throws IOException {
        Request request = buildProject(file);
        return Utils.getResponse(parserList, request);
    }

    @Override
    public Request buildProject(MultipartFile file) throws IOException {
        String serverUrl = "http://localhost:7002/api/fileParse"; //java-service
        ResponseEntity<Request> request = restTemplate.postForEntity(serverUrl, Utils.getResponseEntity(file), Request.class);
        return request.getBody();
    }

    //**
    //Build Project with File Path
    //**
    @Override
    public Response build(List<String> parserList, Path path) throws IOException {
        Request request = buildProject(path);
        return Utils.getResponse(parserList, request);
    }

    @Override
    public Request buildProject(Path path) throws IOException {
        String serverUrl = "http://localhost:7002/api/pathParse"; //java-service
        ResponseEntity<Request> request = restTemplate.postForEntity(serverUrl, path, Request.class);
        return request.getBody();
    }


}
