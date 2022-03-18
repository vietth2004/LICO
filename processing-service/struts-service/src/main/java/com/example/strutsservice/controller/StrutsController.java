package com.example.strutsservice.controller;


import com.example.strutsservice.service.StrutsService;
import com.example.strutsservice.utils.communicator.Request;
import com.example.strutsservice.utils.communicator.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/struts-service")
public class StrutsController {

    @Autowired
    private StrutsService strutsService;

    @PostMapping("/dependency/struts")
    public Response getStrutsDependency(@RequestBody Request request) {
        return strutsService.getDependency(request);
    }

    @PostMapping("/print")
    public String test() {
        return "lmao";
    }
}
