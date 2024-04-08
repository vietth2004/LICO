package com.example.parserservice.service.state.controller;

import com.example.parserservice.service.ParserServiceImpl;
import com.example.parserservice.service.project.ProjectService;
import com.example.parserservice.service.state.model.Greeting;
import com.example.parserservice.service.state.model.HelloMessage;
import com.example.parserservice.service.state.model.Parser;
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
    @Autowired
    private ParserServiceImpl parserService;
    @MessageMapping("/unzip")
    @SendTo("/topic/extractedSize")
    public Unzip getExtractedSize() throws Exception{
//        Thread.sleep(1000); // simulated delay
        Unzip unZip =new Unzip(projectService.isUnzipProgress(), projectService.getExtractedSize());
        //System.out.println(unZip.isProgress());
         //System.out.println(unZip.getExtractedSize());
         return (unZip);

    }
    @MessageMapping("/parser")
    @SendTo("/topic/parserProgress")
    public Parser getParserProgress() throws Exception{
//        Thread.sleep(1000); // simulated delay
        System.out.println("isEndProgress"+parserService.isEndProgress() +"isJavaServer" +parserService.isJavaServer()+"isXmlServer"+ parserService.isXmlServer()+"isJspServer" + parserService.isJspServer()+"isPropServer"+ parserService.isPropServer());
        Parser parser = new Parser(parserService.isEndProgress(), parserService.isJavaServer(), parserService.isXmlServer(), parserService.isJspServer(), parserService.isPropServer());
        System.out.println("ABC"+parser.isEndProgress());
        return (parser);

    }
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        Greeting greeting = new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
        System.out.println(greeting);
        return (greeting);
    }
}
