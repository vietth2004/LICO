package com.example.jsfservice.utils;

import com.example.jsfservice.constant.HostIPConstants;
import com.example.jsfservice.dto.Request;
import com.example.jsfservice.dto.java.JavaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class JavaUtils {

    @Autowired
    HostIPConstants ipConstants;

    public JavaResponse getJavaNode(Request request) {
        RestTemplate restTemplate = new RestTemplate();
        String serverUrl = "http://" + ipConstants.getJavaServiceIp() + ":7002/api/java-service/pathParse";
        JavaResponse response = restTemplate.postForObject(serverUrl, request, JavaResponse.class);
        return response;
    }

}
