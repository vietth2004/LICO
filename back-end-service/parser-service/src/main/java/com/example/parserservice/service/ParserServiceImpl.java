package com.example.parserservice.service;

import com.example.parserservice.constant.HostIPConstants;
import com.example.parserservice.model.jsf.JSFResponse;
import com.example.parserservice.service.project.ProjectService;
import com.example.parserservice.model.*;
import com.example.parserservice.model.parser.Request;
import com.example.parserservice.util.JwtUtils;
import com.example.parserservice.util.worker.Getter;
import com.example.parserservice.util.worker.Requester;
import com.example.parserservice.util.worker.Wrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
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

    private Requester requester;

    //**
    //Build Project with Multipart File
    //**
    @Override
    public Response build(List<String> parserList, MultipartFile file, String user, String project) {
        log.info("Function: build() Thread name: {}, id: {}, state: {}", Thread.currentThread().getName(), Thread.currentThread().getId(), Thread.currentThread().getState());
        String userPath = user;
        if(!userPath.equals("anonymous")){
            userPath = jwtUtils.extractUsername(user);
        }
        String fileName = projectService.storeFile(file, userPath, project);

        Path filePath = new Path("./project/" + userPath + "/" + project + "/" + fileName + ".project");
        CompletableFuture<Request> reqFuture = CompletableFuture.supplyAsync(() -> buildProject(filePath));
        Request request = null;
        try {
            request = reqFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return Getter.getResponse(parserList, request, filePath.getPath());
    }

    @Override
    public Request buildProject(MultipartFile file) {
        log.info("Function: buildProject() Thread name: {}, id: {}, state: {}", Thread.currentThread().getName(), Thread.currentThread().getId(), Thread.currentThread().getState());
        String serverUrl = "http://" + ipConstants.getJavaServiceIp() + ":7002/api/java-service/fileParse"; //java-service
        ResponseEntity<Request> request = restTemplate.postForEntity(serverUrl, Requester.getResponseEntity(file), Request.class);
        return request.getBody();
    }

    //**
    //Build Project with File Path
    //**
    @Override
    public Response build(List<String> parserList, Path path) {
        log.info("Function: build() Thread name: {}, id: {}, state: {}", Thread.currentThread().getName(), Thread.currentThread().getId(), Thread.currentThread().getState());

        /**
         * Submit parser task to future (multithreading)
         */
        CompletableFuture<Request> reqFuture = CompletableFuture.supplyAsync(() -> buildProject(path));
        Request request = null;
        try {
            request = reqFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return Getter.getResponse(parserList, request, path.getPath());
    }

    public JSFResponse buildJsf(Path path) {
        log.info("Function: buildJsf() Thread name: {}, id: {}, state: {}", Thread.currentThread().getName(), Thread.currentThread().getId(), Thread.currentThread().getState());
        String serverUrl = "http://" + ipConstants.getJsfServiceIP() + ":7004/api/jsf-service/analyze";
        ResponseEntity<JSFResponse> jsfResponse = restTemplate.postForEntity(serverUrl, path, JSFResponse.class);
        return jsfResponse.getBody();
    }

    public Request buildProject(Path path) {
        log.info("Function: buildProject() Thread name: {}, id: {}, state: {}", Thread.currentThread().getName(), Thread.currentThread().getId(), Thread.currentThread().getState());
        String javaServerUrl = "http://" + ipConstants.getJavaServiceIp() + ":7002/api/java-service/pathParse"; //java-service
        String xmlServerUrl = "http://" + ipConstants.getXmlServiceIp() + ":7006/api/xml-service/pathParse/old"; //xml-service
        String jspServerUrl = "http://" + ipConstants.getJspServiceIp() + ":7005/api/jsp-service/pathParse/old"; //xml-service

        ResponseEntity<Request> javaRequest = restTemplate.postForEntity(javaServerUrl, path, Request.class);
        ResponseEntity<Request> xmlRequest = restTemplate.postForEntity(xmlServerUrl, path, Request.class);
        ResponseEntity<Request> jspRequest = restTemplate.postForEntity(jspServerUrl, path, Request.class);

        Request request = new Request(
                javaRequest.getBody().getRootNode()
                , javaRequest.getBody().getAllDependencies()
                , javaRequest.getBody().getJavaNodes()
                , xmlRequest.getBody().getXmlNodes()
                , jspRequest.getBody().getJspNodes()
                );

        Wrapper.wrapXmlAndJspNode(request);

        return request;
    }
}
