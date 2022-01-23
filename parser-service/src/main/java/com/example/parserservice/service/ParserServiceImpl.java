package com.example.parserservice.service;

import com.example.parserservice.constant.HostIPConstants;
import com.example.parserservice.download.ProjectService;
import com.example.parserservice.model.*;
import com.example.parserservice.model.parser.Request;
import com.example.parserservice.util.Utils;
import mrmathami.cia.java.jdt.project.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ParserServiceImpl implements ParserService{

    final ProjectService projectService;

    public ParserServiceImpl(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HostIPConstants ipConstants;

    //**
    //Build Project with Multipart File
    //**
    @Override
    public Response build(List<String> parserList, MultipartFile file, String user) {
        String fileName = projectService.storeFile(file, user);
        Path filePath = new Path("./project/" + user + "/" + fileName + ".project");
        Request request = buildProject(filePath);
        return Utils.getResponse(parserList, request, filePath.getPath());
    }

    @Override
    public Request buildProject(MultipartFile file) {
        String serverUrl = "http://" + ipConstants.getJavaServiceIp() + ":7002/api/java-service/fileParse"; //java-service
        ResponseEntity<Request> request = restTemplate.postForEntity(serverUrl, Utils.getResponseEntity(file), Request.class);
        return request.getBody();
    }

    //**
    //Build Project with File Path
    //**
    @Override
    public Response build(List<String> parserList, Path path) {
        Request request = buildProject(path);
        return Utils.getResponse(parserList, request, path.getPath());
    }

    @Override
    public Request buildProject(Path path) {
        String serverUrl = "http://" + ipConstants.getJavaServiceIp() + ":7002/api/java-service/pathParse"; //java-service
        ResponseEntity<Request> request = restTemplate.postForEntity(serverUrl, path, Request.class);
        return request.getBody();
    }


}
