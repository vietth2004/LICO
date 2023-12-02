package com.example.xmlservice.controller;

import com.example.xmlservice.dom.Node;
import com.example.xmlservice.dom.Properties.PropertiesFileNode;
import com.example.xmlservice.dto.Request;
import com.example.xmlservice.service.PropertiesService;
import com.example.xmlservice.service.XmlService;
import com.example.xmlservice.utils.communicator.PropResponse;
import com.example.xmlservice.utils.communicator.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/prop-service/")
public class PropertiesController {

    @Autowired
    private PropertiesService propService;

    @PostMapping("/pathParse/old")
    public ResponseEntity oldPathParse(@RequestBody Request request) {
        List<PropertiesFileNode> propNodes = new ArrayList<>();
        try {
            List<PropertiesFileNode> parsedNodes = propService.parseProjectWithPath(request.getPath());
            propNodes.addAll(parsedNodes);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(new PropResponse(propNodes));
    }

}
