package com.example.parserservice.util.worker;

import com.example.parserservice.ast.dependency.Dependency;
import com.example.parserservice.constant.HostIPConstants;
import com.example.parserservice.model.FrameworkRequest;
import com.example.parserservice.model.cia.CiaRequest;
import com.example.parserservice.model.cia.CiaResponse;
import com.example.parserservice.model.parser.Request;
import com.example.parserservice.model.spring.DependencyResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Requester {

    @Autowired
    private HostIPConstants ipConstants;

    public static List<Dependency> getDependencies(String parser, Request request) {
        RestTemplate restTemplate = new RestTemplate();
//        Request frameworkRequest = new Request(
//                request.getRootNode(),
//                request.getAllDependencies(),
//                request.getJavaNodes(),
//                request.getXmlNodes(),
//                request.getJspNodes());


        List<Dependency> dependencies = new ArrayList();

        if (parser.equals("spring-parser")) {

            DependencyResponse springs = restTemplate.postForObject(
                    "http://localhost:7003/api/spring-service/dependency/spring" //spring-service
                    , new FrameworkRequest(request.getJavaNodes(), request.getXmlNodes(), request.getJspNodes())
                    , DependencyResponse.class);

            dependencies.addAll(springs.getAllDependencies());
        }

        if (parser.equals("struts-parser")) {
            DependencyResponse struts = restTemplate.postForObject(
                    "http://localhost:7007/api/struts-service/dependency/struts", //struts-service
                    new FrameworkRequest(request.getJavaNodes(), request.getXmlNodes(), request.getJspNodes()),
                    DependencyResponse.class);

            dependencies.addAll(struts.getAllDependencies());
        }

        if (parser.equals("jsf-parser")) {
            DependencyResponse jsf = restTemplate.postForObject(
                    "http://localhost:7004/api/jsf-service/dependency/jsf/new", //jsf-service
                    new FrameworkRequest(request.getJavaNodes(), request.getXmlNodes(), request.getJspNodes(), request.getPropertiesNodes()),
                    DependencyResponse.class);

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
