package com.example.parserservice.service;

import com.example.parserservice.constant.HostIPConstants;
import com.example.parserservice.model.jsf.JSFResponse;
import com.example.parserservice.service.project.ProjectService;
import com.example.parserservice.model.*;
import com.example.parserservice.model.parser.Request;
import com.example.parserservice.util.JwtUtils;
import com.example.parserservice.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ParserServiceImpl implements ParserService{

    final ProjectService projectService;

    final JwtUtils jwtUtils;

    public ParserServiceImpl(ProjectService projectService, JwtUtils jwtUtils) {
        this.projectService = projectService;
        this.jwtUtils = jwtUtils;
    }

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HostIPConstants ipConstants;

    //**
    //Build Project with Multipart File
    //**
    @Override
    public Response build(List<String> parserList, MultipartFile file, String user, String project) {

        String userPath = user;
        if(!userPath.equals("anonymous")){
            userPath = jwtUtils.extractUsername(user);
        }
        String fileName = projectService.storeFile(file, userPath, project);

        Path filePath = new Path("./project/" + userPath + "/" + project + "/" + fileName + ".project");
        Request request = buildProject(filePath);
        JSFResponse jsf = buildJsf(filePath);
        return Utils.getResponse(parserList, request, filePath.getPath(), jsf);
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
        JSFResponse jsf = buildJsf(path);
        return Utils.getResponse(parserList, request, path.getPath(), jsf);
    }

    @Override
    public Request buildProject(Path path) {
        String serverUrl = "http://" + ipConstants.getJavaServiceIp() + ":7002/api/java-service/pathParse"; //java-service
        ResponseEntity<Request> request = restTemplate.postForEntity(serverUrl, path, Request.class);
        return request.getBody();
    }

    @Override
    public JSFResponse buildJsf(Path path) {
        String serverUrl = "http://" + ipConstants.getXmlServiceIp() + ":7004/api/jsf-service/analyze";
        ResponseEntity<JSFResponse> jsfResponse = restTemplate.postForEntity(serverUrl, path, JSFResponse.class);
        return jsfResponse.getBody();
    }


}
