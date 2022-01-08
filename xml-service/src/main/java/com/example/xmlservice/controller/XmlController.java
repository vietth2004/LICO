package com.example.xmlservice.controller;

import com.example.xmlservice.ast.dependency.Dependency;
import com.example.xmlservice.ast.node.JavaNode;
import com.example.xmlservice.dom.Node;
import com.example.xmlservice.dom.Properties.PropertiesFileNode;
import com.example.xmlservice.dto.Request;
import com.example.xmlservice.dto.Response;
import com.example.xmlservice.service.PropertiesService;
import com.example.xmlservice.service.XmlService;
import com.example.xmlservice.utils.Log.ClientLevel;
import com.example.xmlservice.utils.NodeUtils;
import org.apache.logging.log4j.LogManager;
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
@RequestMapping("/api/xml-service/")
public class XmlController {

    private final Logger logger = LoggerFactory.getLogger(XmlController.class);

    public List<Node> xmlNodes = new ArrayList<>();
    public List<PropertiesFileNode> propFileNodes = new ArrayList<>();

    @Autowired
    private XmlService xmlService;

    @Autowired
    private PropertiesService propService;

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
        dependencies.addAll(propService.analyzeDependencies(xmlNodes, this.propFileNodes));
        long after = System.nanoTime();
        logger.info("Done analyzing dependency...");
        logger.info("Number of dependencies: " + dependencies.size());
        logger.info("Analyzing dependencies done in " + (after - before)/1000000 + " ms!");
        return new ResponseEntity<List<Dependency>>(dependencies, HttpStatus.OK);
    }

}
