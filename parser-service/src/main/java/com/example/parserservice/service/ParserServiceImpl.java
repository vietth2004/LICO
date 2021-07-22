package com.example.parserservice.service;

import com.example.parserservice.ast.node.JavaNode;
import com.example.parserservice.model.Resource;
import com.example.parserservice.model.Response;
import com.google.inject.spi.Dependency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParserServiceImpl implements ParserService{

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Response build(List<String> parserList, MultipartFile file) {
        JavaNode javaNode = buildProject(file);
        List javaNodes = new ArrayList();
        List Dependency = new ArrayList();

        for (String parser : parserList) {
            if(Resource.PARSER.contains(parser)) {
                Dependency.add(parse(parser, javaNodes));
            }
        }

        return null;
    }

    @Override
    public List parse(String parser, List javaNodes) {
        List dependency = new ArrayList();
        if(parser.equals("spring-parser")) {
//            List springs = restTemplate.postForObject();
//            dependency.addAll()
        }
        return null;
    }

    @Override
    public JavaNode buildProject(MultipartFile file) {
        JavaNode rootNode = restTemplate.postForObject("", file, JavaNode.class);
        return rootNode;
    }
}
