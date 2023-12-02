package com.example.xmlservice.service;

import com.example.xmlservice.dom.Node;
import com.example.xmlservice.dom.Xml.XmlFileNode;
import com.example.xmlservice.parser.XmlFileParser;
import com.example.xmlservice.utils.Exception.JciaNotFoundException;
import com.example.xmlservice.utils.Helper.FileHelper;
import com.example.xmlservice.utils.Helper.StringHelper;
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
import java.util.concurrent.Future;

@Service
public class XmlServiceImpl implements XmlService {

    private final ExecutorService THREADPOOL_FIXED_SIZE = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private XmlFileParser xmlFileParser = new XmlFileParser();

    @Override
    public List<Node> parseProjectWithPath(String folderPath) throws IOException, ExecutionException, InterruptedException {
        List<Node> xmlNodes = new ArrayList<>();
        Path path = Paths.get(folderPath);
        List<Path> paths = FileHelper.listFiles(path);

        /**
         * Submit callable task to ThreadPool
         */
        List<Future<Node>> xmlNodeFutures = new ArrayList<>();
        paths.forEach(x -> {
            if(StringHelper.SUPPORTED_EXTENSIONS.contains(FileHelper.getFileExtension(x.toString()))){
                Future<Node> future = null;
                future = THREADPOOL_FIXED_SIZE.submit(new XmlFileParser(x.toString()));
                xmlNodeFutures.add(future);
            }
        });

        for(Future<Node> future : xmlNodeFutures) {
            Node parsedNode = future.get();
            xmlNodes.add(parsedNode);
        }

        return xmlNodes;
    }


    @Override
    public List<Node> parseProjectWithFile(MultipartFile file) throws IOException {
        return null;
    }
}
