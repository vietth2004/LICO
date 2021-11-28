package com.example.githubservice.helper;

import com.example.githubservice.config.UserConfig;
import com.example.githubservice.dto.Response;
import com.google.gson.Gson;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;

@Service
public class ResponseHelper {

    public Response convertPATResponseToObject(String PAT) {
        Gson gson = new Gson();
        Response response = gson.fromJson(PAT, Response.class);
        return response;
    }

    public String getUserNameFromResponse(String token) {
        String url = "https://api.github.com/user";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "token " + UserConfig.PERSONAL_ACCESS_TOKEN);
        headers.set("Accept", "application/vnd.github.v3+json");
        HttpEntity entity = new HttpEntity(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity response = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
        LinkedHashMap<String, ?> body = (LinkedHashMap<String, ?>) response.getBody();
        String name = (String) body.get("login");
        return name;
    }

}
