package com.example.jspservice.controller;

import com.example.jspservice.dom.Node;
import com.example.jspservice.dto.Request;
import com.example.jspservice.service.StrutService;
import com.example.jspservice.utils.Converter;
import com.example.jspservice.utils.communicator.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/jsp-service/")
public class JspController {

    @Autowired
    private StrutService strutService;

//    List<Node> strutNodes = new ArrayList<>();

    @PostMapping("/pathParse/new")
    public ResponseEntity pathParse(@RequestBody Request request) {
        List<Node> jspNodes = new ArrayList<>();
        List<com.example.jspservice.ast.node.Node> nodes = new ArrayList<>();
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

    @PostMapping("/pathParse/old")
    public ResponseEntity oldPathParse(@RequestBody Request request) {
        List<Node> jspNodes = new ArrayList<>();
        try {
            List<Node> strutNodes = strutService.parseProjectWithPath(request.getPath());
            jspNodes.addAll(strutNodes);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(new Response(jspNodes));
    }

//    @PostMapping("/dependency")
//    public ResponseEntity getDependency(@RequestBody List<JavaNode> request,
//                                        @RequestParam String path) {
//        List<Dependency> dependencies = new ArrayList<>();
//        try {
//            List<Node> strutNodes = strutService.parseProjectWithPath(path);
//            dependencies.addAll(strutService.analyzeDependency(request, strutNodes));
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.ok(dependencies);
//    }

}
