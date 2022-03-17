package com.example.xmlservice.controller;

import com.example.xmlservice.dom.Node;
import com.example.xmlservice.dto.Request;
import com.example.xmlservice.service.XmlService;
import com.example.xmlservice.utils.Converter;
import com.example.xmlservice.utils.communicator.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/xml-service/")
public class XmlController {

    @Autowired
    private XmlService strutService;

//    List<Node> strutNodes = new ArrayList<>();

    @PostMapping("/pathParse/new")
    public ResponseEntity pathParse(@RequestBody Request request) {
        List<Node> jspNodes = new ArrayList<>();
        List<com.example.xmlservice.ast.node.Node> nodes = new ArrayList<>();
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

}
