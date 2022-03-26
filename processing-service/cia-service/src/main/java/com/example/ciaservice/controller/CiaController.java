package com.example.ciaservice.controller;


import com.example.ciaservice.model.Request;
import com.example.ciaservice.model.Response;
import com.example.ciaservice.service.CiaService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cia-service/")
public class CiaController {

    private final CiaService ciaService;

    public CiaController(CiaService ciaService) {
        this.ciaService = ciaService;
    }

    @PostMapping("/calculate")
    public Response calculateNodeWeight(@RequestBody Request request) {
        return ciaService.calculate(request.getAllDependencies(), request.getTotalNodes());
    }

    @PostMapping("/impact")
    public Response findImpact(@RequestBody Request request) {
        return ciaService.findImpact(request.getJavaNodes(), request.getAllDependencies(), request.getTotalNodes(), request.getChangedNodes());
    }
}
