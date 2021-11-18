package com.jcia.xmlservice.Controller;

import com.jcia.xmlservice.Dom.Node;
import com.jcia.xmlservice.Service.XmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class XmlController {

    @Autowired
    private XmlService xmlService;

    @PostMapping("/api/pathParse")
    public ResponseEntity<?> parseProjectByPath(@RequestBody String folderPath) throws IOException {
        List<Node> nodes = xmlService.parseProjectWithPath(folderPath);
        return new ResponseEntity<Object>(nodes,HttpStatus.OK);
    }

}
