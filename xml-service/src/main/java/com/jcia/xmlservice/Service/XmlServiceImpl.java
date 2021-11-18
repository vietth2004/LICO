package com.jcia.xmlservice.Service;

import com.jcia.xmlservice.Dom.Node;
import com.jcia.xmlservice.Parser.XmlFileParser;
import com.jcia.xmlservice.Utils.Exception.JciaNotFoundException;
import com.jcia.xmlservice.Utils.Helper.FileHelper;
import com.jcia.xmlservice.Utils.Helper.StringHelper;
import com.jcia.xmlservice.Utils.Log.ClientLevel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class XmlServiceImpl implements XmlService {

    private Logger logger = LogManager.getLogger(XmlServiceImpl.class);

    public XmlServiceImpl(){}

    @Override
    public List<Node> parseProjectWithPath(String folderPath) throws IOException {
        XmlFileParser xmlFileParser = new XmlFileParser();
        List<Node> nodes = new ArrayList<>();

        Path path = Paths.get(folderPath);
        List<Path> paths = FileHelper.listFiles(path);
        paths.forEach(x -> {
            if(StringHelper.SUPPORTED_EXTENSIONS.contains(FileHelper.getFileExtension(x.toString()))){
                try {
                    Node parsedNode = xmlFileParser.parse(x.toString());
                    if(parsedNode != null){
                        nodes.add(parsedNode);
                    }
                } catch (JciaNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        logger.log(ClientLevel.CLIENT, nodes.stream().toArray().length);
        return nodes;
    }

    @Override
    public List<Node> parseProjectWithFile(MultipartFile file) throws IOException {
        return null;
    }
}
