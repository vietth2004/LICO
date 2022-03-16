package com.example.xmlservice.service;

import com.example.xmlservice.dom.Node;
import com.example.xmlservice.dom.Xml.XmlFileNode;
import com.example.xmlservice.parser.XmlFileParser;
import com.example.xmlservice.utils.Exception.JciaNotFoundException;
import com.example.xmlservice.utils.Helper.FileHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class XmlServiceImpl implements XmlService {

    private final ExecutorService THREADPOOL_FIXED_SIZE = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private XmlFileParser xmlFileParser = new XmlFileParser();

    @Override
    public List<Node> parseProjectWithPath(String folderPath) throws IOException, ExecutionException, InterruptedException {

        Path path = Paths.get(folderPath);
        List<Path> paths = FileHelper.listFiles(path);
        List<Node> nodes = new ArrayList<>();

        paths.forEach(p -> {

            if(p.toString().endsWith(".xml")) {
                Node xmlNode = new XmlFileNode();
                xmlNode.setName(new File(p.toString()).getName());
                xmlNode.setAbsolutePath(p.toString());
                try {
                    nodes.add(xmlFileParser.parse(xmlNode));
                } catch (JciaNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });


        return nodes;
    }


    @Override
    public List<Node> parseProjectWithFile(MultipartFile file) throws IOException {
        return null;
    }
}
