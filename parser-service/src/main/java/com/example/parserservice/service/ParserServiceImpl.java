package com.example.parserservice.service;

import com.example.parserservice.ast.node.JavaNode;
import com.example.parserservice.model.Path;
import com.example.parserservice.model.Request;
import com.example.parserservice.model.Resource;
import com.example.parserservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParserServiceImpl implements ParserService{

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List parse(String parser, List javaNodes) {
        List dependencies = new ArrayList();
        if(parser.equals("spring-parser")) {
            Request springs = restTemplate.postForObject("http://localhost:7003/api/dependency/spring", javaNodes, Request.class);
            dependencies.addAll(springs.getAllDependencies());
        }
        return dependencies;
    }

    private Response getResponse(List<String> parserList, Request request) {
        JavaNode javaNode = request.getRootNode();
        List javaNodes = request.getAllNodes();
        List dependencies = request.getAllDependencies();

        for (String parser : parserList) {
            if(Resource.PARSER.contains(parser)) {
                dependencies.addAll(parse(parser, javaNodes));
            }
        }

        return new Response(javaNode, dependencies, javaNodes);
    }

    //Build Project with Multipart File
    @Override
    public Response build(List<String> parserList, MultipartFile file) throws IOException {
        Request request = buildProject(file);
        return getResponse(parserList, request);
    }

    @Override
    public Request buildProject(MultipartFile file) throws IOException {
        String serverUrl = "http://localhost:7002/api/fileParse";
        ResponseEntity<Request> request = restTemplate.postForEntity(serverUrl, getResponseEntity(file), Request.class);

        return request.getBody();
    }

    private HttpEntity getResponseEntity(MultipartFile file) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        return requestEntity;
    }


    //Build Project with File Path
    @Override
    public Response build(List<String> parserList, Path path) throws IOException {
        Request request = buildProject(path);

        return getResponse(parserList, request);
    }

    @Override
    public Request buildProject(Path path) throws IOException {

        String serverUrl = "http://localhost:7002/api/pathParse";
        ResponseEntity<Request> request = restTemplate.postForEntity(serverUrl, path, Request.class);
        System.out.println(request);
        return request.getBody();
//        return new Request();
    }


}
