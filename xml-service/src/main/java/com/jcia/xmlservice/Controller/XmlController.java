package com.jcia.xmlservice.Controller;

import com.jcia.xmlservice.Dom.Node;
import com.jcia.xmlservice.Parser.XmlFileParser;
import com.jcia.xmlservice.Utils.Exception.JciaNotFoundException;
import com.jcia.xmlservice.Utils.Helper.FileHelper;
import com.jcia.xmlservice.Utils.Helper.StringHelper;
import com.jcia.xmlservice.Utils.Log.ClientLevel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
public class XmlController {

    private Logger logger = LogManager.getLogger(XmlController.class);

    @PostMapping("/api/xml-parser")
    public ResponseEntity<?> getXmlTree(@RequestBody String folderPath) throws IOException {
        XmlFileParser xmlFileParser = new XmlFileParser();
        List<Node> nodes = new ArrayList<>();

//        folderPath = "C:/Users/thanh/Documents/jcia-ezc";

        Path path = Paths.get(folderPath);
        List<Path> paths = FileHelper.listFiles(path);
        paths.forEach(x -> {
            if(StringHelper.SUPPORTED_EXTENSIONS.contains(FileHelper.getFileExtension(x.toString()))){
                try {
                    Node parsedNode = xmlFileParser.parse(x.toString());
                    if(parsedNode != null){
//                    Map<String, Node> elem = new HashMap<>();
//                    elem.put(getFileExtension(parsedNode.getAbsolutePath()), parsedNode);
//                    nodes.add(elem);
                        nodes.add(parsedNode);
                    }
                } catch (JciaNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        logger.log(ClientLevel.CLIENT, nodes.stream().toArray().length);

        return new ResponseEntity<Object>(nodes,HttpStatus.OK);
    }

}
