package com.example.parserservice.util.worker;

import com.example.parserservice.model.cia.CiaRequest;
import com.example.parserservice.model.cia.CiaResponse;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class Calculator {

    public static List getNodesWeight(List dependencies, Integer size) {

        RestTemplate restTemplate = new RestTemplate();

        CiaResponse ciaResponse = restTemplate.postForObject(
                "http://localhost:6001/api/cia-service/calculate", //cia-service
                new CiaRequest(dependencies, size),
                CiaResponse.class);

        return ciaResponse.getNodes();
    }

}
