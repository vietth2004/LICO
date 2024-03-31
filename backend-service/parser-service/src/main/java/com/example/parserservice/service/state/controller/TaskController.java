package com.example.parserservice.service.state.controller;

import com.example.parserservice.service.project.ProjectService;
import com.example.parserservice.service.state.model.Greeting;
import com.example.parserservice.service.state.model.HelloMessage;
import com.example.parserservice.service.state.model.Unzip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class TaskController {
    @Autowired
    private ProjectService projectService;

    @MessageMapping("/unzip")
    @SendTo("/topic/extractedSize")
    public Unzip getExtractedSize() throws Exception{
//        Thread.sleep(1000); // simulated delay
        return new Unzip(projectService.isUnzipProgress(), projectService.getExtractedSize());
    }
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }
}
