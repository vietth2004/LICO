package com.example.jsfservice.controller;

import com.example.jsfservice.ast.dependency.Dependency;
import com.example.jsfservice.ast.node.JavaNode;
import com.example.jsfservice.dom.Node;
import com.example.jsfservice.dom.Properties.PropertiesFileNode;
import com.example.jsfservice.dto.Request;
import com.example.jsfservice.dto.Response;
import com.example.jsfservice.dto.java.JavaResponse;
import com.example.jsfservice.dto.parser.ParserResponse;
import com.example.jsfservice.service.PropertiesService;
import com.example.jsfservice.service.XmlService;
import com.example.jsfservice.utils.JavaUtils;
import com.example.jsfservice.utils.NodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/jsf-service/")
public class JsfController {

    private final Logger logger = LoggerFactory.getLogger(JsfController.class);

    public List<Node> xmlNodes = new ArrayList<>();
    public List<PropertiesFileNode> propFileNodes = new ArrayList<>();

    @Autowired
    private XmlService xmlService;

    @Autowired
    private PropertiesService propService;

    @Autowired
    JavaUtils javaUtils;

    /**
     * Parse to xml nodes by sending project path
     * @param folderPath
     * @param javaNode
     * @return
     * @throws IOException
     */
    @PostMapping("/pathParse")
    public Response parseProjectByPath(@RequestBody Request folderPath, @RequestParam int javaNode) throws IOException, ExecutionException, InterruptedException {
        long before = System.nanoTime();
        logger.info("Run into API: /pathParse");
        logger.info("Start building xml tree...");
        List<Node> xmlNodes = xmlService.parseProjectWithPath(folderPath.getPath());
        logger.info("Done parsing xml tree...");
        List<PropertiesFileNode> propFileNodes = propService.parseProjectWithPath(folderPath.getPath());
        long after =  System.nanoTime();
        logger.info("Parsing xml nodes done in " + (after - before)/1000000 + " ms!");
        this.xmlNodes = new ArrayList<>();
        this.propFileNodes = new ArrayList<>();
        this.xmlNodes.addAll(xmlNodes);
        this.propFileNodes.addAll(propFileNodes);
        logger.info("Recalculating xmlNode id by javaNode id...");
        NodeUtils.reCalculateXmlNodesId(javaNode, this.xmlNodes);
        return new Response(xmlNodes);
    }

    /**
     * Analyze dependencies from java node to xml nodes
     * @param request
     * @return
     */
    @PostMapping("/dependency")
    public ResponseEntity<List<Dependency>> analyzeDependency(@RequestBody List<JavaNode> request) throws ExecutionException, InterruptedException {
        List<Dependency> dependencies = new ArrayList<>();
        long before = System.nanoTime();
        logger.info("Run into API: /dependency");
        logger.info("Analyzing dependency...");
        dependencies.addAll(xmlService.analyzeDependency(request, this.xmlNodes));
//        dependencies.addAll(propService.analyzeDependencies(xmlNodes, this.propFileNodes));
        long after = System.nanoTime();
        logger.info("Done analyzing dependency...");
        logger.info("Number of dependencies: " + dependencies.size());
        logger.info("Analyzing dependencies done in " + (after - before)/1000000 + " ms!");
        return new ResponseEntity<List<Dependency>>(dependencies, HttpStatus.OK);
    }

    @PostMapping("/analyze")
    public ResponseEntity<ParserResponse> analyzeProject(@RequestBody Request req) throws IOException, ExecutionException, InterruptedException {
        long before = System.nanoTime();
        logger.info("Run into API: /analyze");
        logger.info("Analyzing dependency...");
        JavaResponse javaNode = javaUtils.getJavaNode(req);
        List<Node> xmlNodes = xmlService.parseProjectWithPath(req.getPath());
        List<PropertiesFileNode> propFileNodes = propService.parseProjectWithPath(req.getPath());
        List<Dependency> dependencies = new ArrayList<>();
        dependencies.addAll(xmlService.analyzeDependency(javaNode.getAllNodes(), xmlNodes));
        dependencies.addAll(propService.analyzeDependencies(xmlNodes, propFileNodes));
        ParserResponse response = new ParserResponse(xmlNodes, dependencies);
        long after = System.nanoTime();
        logger.info("Done analyzing dependency...");
        logger.info("Number of dependencies: " + dependencies.size());
        logger.info("Analyzing dependencies done in " + (after - before)/1000000 + " ms!");
        return new ResponseEntity<ParserResponse>((ParserResponse) null, HttpStatus.OK);
    }

}
