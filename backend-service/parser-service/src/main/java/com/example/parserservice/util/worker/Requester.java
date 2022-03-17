package com.example.parserservice.util.worker;

import com.example.parserservice.constant.HostIPConstants;
import com.example.parserservice.model.Path;
import com.example.parserservice.model.Response;
import com.example.parserservice.model.cia.CiaRequest;
import com.example.parserservice.model.cia.CiaResponse;
import com.example.parserservice.model.jsf.JSFResponse;
import com.example.parserservice.model.parser.Request;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Requester {

    @Autowired
    private static HostIPConstants ipConstants;

    public static List getDependencies(String parser, Request request) {
        RestTemplate restTemplate = new RestTemplate();
        List dependencies = new ArrayList();

        if(parser.equals("spring-parser")) {

            Request springs = restTemplate.postForObject(
                    "http://localhost:7003/api/spring-service/dependency/spring",
                    request,
                    Request.class);

            dependencies.addAll(springs.getAllDependencies());
        }

        if(parser.equals("struts-parser")) {
            Request struts = restTemplate.postForObject(
                    "http://localhost:7007/api/struts-service/dependency/struts", //struts-service
                    request,
                    Request.class);

            dependencies.addAll(struts.getAllDependencies());
        }

        if(parser.equals("jsf-parser")) {
            Request jsf = restTemplate.postForObject(
                    "http://localhost:7004/api/jsf-service/dependency/jsf", //jsf-service
                    request,
                    Request.class);

            dependencies.addAll(jsf.getAllDependencies());
        }

        return dependencies;
    }

    public static HttpEntity getResponseEntity(MultipartFile file) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        return requestEntity;
    }


    public static List getNodesWeight(List dependencies, Integer size) {

        RestTemplate restTemplate = new RestTemplate();

        CiaResponse ciaResponse = restTemplate.postForObject(
                "http://localhost:6001/api/cia-service/calculate", //cia-service
                new CiaRequest(dependencies, size),
                CiaResponse.class);

        return ciaResponse.getNodes();
    }


//    public JSFResponse buildJsf(Path path) {
//        RestTemplate restTemplate = new RestTemplate();
//        log.info("Function: buildJsf() Thread name: {}, id: {}, state: {}", Thread.currentThread().getName(), Thread.currentThread().getId(), Thread.currentThread().getState());
//        String serverUrl = "http://" + ipConstants.getXmlServiceIp() + ":7004/api/jsf-service/analyze";
//        ResponseEntity<JSFResponse> jsfResponse = restTemplate.postForEntity(serverUrl, path, JSFResponse.class);
//        return jsfResponse.getBody();
//    }
//
//    public Request buildProject(Path path) {
//        RestTemplate restTemplate = new RestTemplate();
//        log.info("Function: buildProject() Thread name: {}, id: {}, state: {}", Thread.currentThread().getName(), Thread.currentThread().getId(), Thread.currentThread().getState());
//        String serverUrl = "http://" + ipConstants.getJavaServiceIp() + ":7002/api/java-service/pathParse"; //java-service
//        ResponseEntity<Request> request = restTemplate.postForEntity(serverUrl, path, Request.class);
//        return request.getBody();
//    }
}
