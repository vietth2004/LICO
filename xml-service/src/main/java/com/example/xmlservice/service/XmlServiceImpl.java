package com.example.xmlservice.service;

import com.example.xmlservice.dom.Node;
import com.example.xmlservice.parser.XmlFileParser;
import com.example.xmlservice.utils.Exception.JciaNotFoundException;
import com.example.xmlservice.utils.Helper.FileHelper;
import com.example.xmlservice.utils.Helper.StringHelper;
import com.example.xmlservice.utils.Log.ClientLevel;
import com.example.xmlservice.ast.dependency.Dependency;
import com.example.xmlservice.ast.node.JavaNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    @Override
    public List<Dependency> analyzeDependency(JavaNode javaNode, List<Node> xmlNodes) {
        List<Dependency> dependencies = new ArrayList<>();
        dependencies.addAll(analyzeDependencyBetweenBeans(javaNode));
        dependencies.addAll(analyzeDependencyFromBeanToView(javaNode, xmlNodes));
        dependencies.addAll(analyzeDependencyFromControllerToView(javaNode, xmlNodes));
        return dependencies;
    }

    public List<Dependency> analyzeDependencyBetweenBeans(JavaNode node){
        List<Dependency> dependencies = new ArrayList<>();

        return dependencies;
    }

    public List<Dependency> analyzeDependencyFromBeanToView(JavaNode node, List<Node> xmlNodes){
        List<Dependency> dependencies = new ArrayList<>();
        return dependencies;
    }

    public List<Dependency> analyzeDependencyFromControllerToView(JavaNode node, List<Node> xmlNodes){
        List<Dependency> dependencies = new ArrayList<>();
        return dependencies;
    }



}
