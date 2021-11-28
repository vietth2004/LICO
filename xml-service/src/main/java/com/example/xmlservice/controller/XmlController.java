package com.example.xmlservice.controller;

import com.example.xmlservice.ast.dependency.Dependency;
import com.example.xmlservice.config.MQConfig;
import com.example.xmlservice.dto.JavaNodeRequest;
import com.example.xmlservice.dom.Node;
import com.example.xmlservice.dto.Request;
import com.example.xmlservice.dto.Response;
import com.example.xmlservice.service.XmlService;
import com.example.xmlservice.utils.Log.ClientLevel;
import com.example.xmlservice.utils.NodeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class XmlController {

    private Logger logger = LogManager.getLogger(XmlController.class);

    public static int JAVA_TOTAL_NODES;
    public List<Node> xmlNodes = new ArrayList<>();

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private XmlService xmlService;

    public XmlController() {
        JAVA_TOTAL_NODES = 0;
    }

    @PostMapping("/api/pathParse")
    public Response parseProjectByPath(@RequestBody Request folderPath) throws IOException {
        long before = System.nanoTime();
        List<Node> nodes = xmlService.parseProjectWithPath(folderPath.getPath());
        long after =  System.nanoTime();
        logger.log(ClientLevel.CLIENT, "Parsing done in " + (after - before)/1000000 + " ms!");
        xmlNodes.addAll(nodes);
        rabbitTemplate.convertAndSend(MQConfig.EXCHANGE, MQConfig.ROUTING_KEY, folderPath);
        return new Response(nodes);
    }

    @PostMapping("/api/dependency")
    public ResponseEntity<List<Dependency>> analyzeDependency(@RequestBody JavaNodeRequest request) {
        JAVA_TOTAL_NODES = request.getTotalNodes();
        List<Dependency> dependencies = new ArrayList<>();
        try {
            NodeUtils.reCalculateXmlNodesId(JAVA_TOTAL_NODES, xmlNodes);
            dependencies.addAll(xmlService.analyzeDependency(request.getRootNode(), xmlNodes));
            logger.log(ClientLevel.CLIENT, "Need to run /api/pathParse first");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<Dependency>>(dependencies, HttpStatus.OK);
    }

    //Test message queue
    @RabbitListener(queues = MQConfig.QUEUE)
    public void getDependency() {
        logger.log(ClientLevel.CLIENT, "Consumer");
    }

}
