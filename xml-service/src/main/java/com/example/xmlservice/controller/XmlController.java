package com.example.xmlservice.controller;

import com.example.xmlservice.ast.dependency.Dependency;
import com.example.xmlservice.ast.node.JavaNode;
import com.example.xmlservice.dom.Node;
import com.example.xmlservice.dto.Request;
import com.example.xmlservice.dto.Response;
import com.example.xmlservice.service.XmlService;
import com.example.xmlservice.utils.Log.ClientLevel;
import com.example.xmlservice.utils.NodeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class XmlController {

    private Logger logger = LogManager.getLogger(XmlController.class);

    public List<Node> xmlNodes = new ArrayList<>();

    @Autowired
    private XmlService xmlService;

    @PostMapping("/api/pathParse")
    public Response parseProjectByPath(@RequestBody Request folderPath, @RequestParam int javaNode) throws IOException {
        long before = System.nanoTime();
        List<Node> nodes = xmlService.parseProjectWithPath(folderPath.getPath());
        long after =  System.nanoTime();
        logger.log(ClientLevel.CLIENT, "Parsing done in " + (after - before)/1000000 + " ms!");
        xmlNodes = new ArrayList<>();
        xmlNodes.addAll(nodes);
        NodeUtils.reCalculateXmlNodesId(javaNode, xmlNodes);
        return new Response(nodes);
    }

    @PostMapping("/api/dependency")
    public ResponseEntity<List<Dependency>> analyzeDependency(@RequestBody List<JavaNode> request) {
        List<Dependency> dependencies = new ArrayList<>();
        dependencies.addAll(xmlService.analyzeDependency(request, xmlNodes));
        logger.log(ClientLevel.CLIENT, "Number of dependencies: " + dependencies.size());
        return new ResponseEntity<List<Dependency>>(dependencies, HttpStatus.OK);
    }

}
