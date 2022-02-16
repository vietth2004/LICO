package com.example.strutservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/strut-service/")
public class StrutController {

    @PostMapping("/pathParse")
    public ResponseEntity pathParse() {
        return ResponseEntity.ok("pathParse");
    }

    @PostMapping("/dependency")
    public ResponseEntity getDependency() {
        return ResponseEntity.ok("pathParse");
    }

}
