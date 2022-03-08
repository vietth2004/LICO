package com.example.strutservice.controller;

import com.example.strutservice.ast.dependency.Dependency;
import com.example.strutservice.ast.node.JavaNode;
import com.example.strutservice.dom.Node;
import com.example.strutservice.dto.Request;
import com.example.strutservice.service.StrutService;
import com.example.strutservice.utils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/strut-service/")
public class StrutController {

    @Autowired
    private StrutService strutService;

//    List<Node> strutNodes = new ArrayList<>();

    @PostMapping("/pathParse")
    public ResponseEntity pathParse(@RequestBody Request request) {
        List<Node> jspNodes = new ArrayList<>();
        List<com.example.strutservice.ast.node.Node> nodes = new ArrayList<>();
        try {
            List<Node> strutNodes = strutService.parseProjectWithPath(request.getPath());
            jspNodes.addAll(strutNodes);
            nodes = Converter.convertStrutsNodesToNodes(jspNodes);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(nodes);
    }

    @PostMapping("/dependency")
    public ResponseEntity getDependency(@RequestBody List<JavaNode> request,
                                        @RequestParam String path) {
        List<Dependency> dependencies = new ArrayList<>();
        try {
            List<Node> strutNodes = strutService.parseProjectWithPath(path);
            dependencies.addAll(strutService.analyzeDependency(request, strutNodes));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(dependencies);
    }

}
