package com.example.strutservice.controller;

import com.example.strutservice.dom.Node;
import com.example.strutservice.dto.Request;
import com.example.strutservice.service.StrutService;
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
@RequestMapping("/api/strut-service/")
public class StrutController {

    @Autowired
    private StrutService strutService;

    @PostMapping("/pathParse")
    public ResponseEntity pathParse(@RequestBody Request request) {
        List<Node> jspNodes = new ArrayList<>();
        try {
            jspNodes.addAll(strutService.parseProjectWithPath(request.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(jspNodes);
    }

    @PostMapping("/dependency")
    public ResponseEntity getDependency() {
        return ResponseEntity.ok("pathParse");
    }

}
