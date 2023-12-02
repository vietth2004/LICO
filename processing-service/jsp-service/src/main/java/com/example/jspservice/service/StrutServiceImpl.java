package com.example.jspservice.service;

//import com.example.jspservice.analyzer.*;

import com.example.jspservice.dom.Jsp.JspFileNode;
import com.example.jspservice.dom.Node;
import com.example.jspservice.parser.StrutsJspParser;
import com.example.jspservice.requester.XmlRequest;
import com.example.jspservice.utils.Helper.FileHelper;
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
public class StrutServiceImpl implements StrutService {

    private final ExecutorService THREADPOOL_FIXED_SIZE = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private StrutsJspParser strutsJspParser = new StrutsJspParser();
    private XmlRequest xmlRequest = new XmlRequest();

    @Override
    public List<Node> parseProjectWithPath(String folderPath) throws IOException, ExecutionException, InterruptedException {

        Path path = Paths.get(folderPath);
        List<Path> paths = FileHelper.listFiles(path);
        List<Node> nodes = new ArrayList<>();

        paths.forEach(p -> {

            if (p.toString().endsWith(".jsp")) {
                Node jspNode = new JspFileNode();
                jspNode.setName(new File(p.toString()).getName());
                jspNode.setAbsolutePath(p.toString());
                nodes.add(strutsJspParser.parse(jspNode));
            }
        });

//        nodes.addAll(xmlRequest.getXmlNode(folderPath));

        return nodes;
    }


    @Override
    public List<Node> parseProjectWithFile(MultipartFile file) throws IOException {
        return null;
    }

//    @Override
//    public List<Dependency> analyzeDependency(List<JavaNode> javaNode, List<Node> strutsNodes) throws ExecutionException, InterruptedException {
//        List<Dependency> dependencies = new ArrayList<>();
//        StrutAnalyzer strutAnalyzer;
//
//        //Struts Action Dependency
//        strutAnalyzer = new ActionDependencyAnalyzer();
//        List<Dependency> strutActionDeps = strutAnalyzer.analyze(javaNode, strutsNodes);
//        dependencies.addAll(strutActionDeps);
//
//        //Struts Dependency
////        List<Dependency> strutDeps = strutAnalyzer.analyze(javaNode, strutsNodes);
////        dependencies.addAll(strutDeps);
//
//        //Struts Interceptor Dependency
//        strutAnalyzer = new InterceptorDepsAnalyzer();
//        List<Dependency> strutInterceptorDeps = strutAnalyzer.analyze(javaNode, strutsNodes);
//        dependencies.addAll(strutInterceptorDeps);
//
//        //Struts Interceptor Stack Dependency
//        strutAnalyzer = new InterceptorStackDepsAnalyzer();
//        List<Dependency> strutInterceptorStackDeps = strutAnalyzer.analyze(javaNode, strutsNodes);
//        dependencies.addAll(strutInterceptorStackDeps);
//
//        //Struts Jsp Dependency
//        strutAnalyzer = new JspDepsAnalyzer();
//        List<Dependency> strutJspDeps = strutAnalyzer.analyze(javaNode, strutsNodes);
//        dependencies.addAll(strutJspDeps);
//
//        //Struts Package Dependency
//        strutAnalyzer = new PackageDepsAnalyzer();
//        List<Dependency> strutPackageDeps = strutAnalyzer.analyze(javaNode, strutsNodes);
//        dependencies.addAll(strutPackageDeps);
//
//        //Struts Result Dependency
//        strutAnalyzer = new ResultDepsAnalyzer();
//        List<Dependency> strutResultDeps = strutAnalyzer.analyze(javaNode, strutsNodes);
//        dependencies.addAll(strutResultDeps);
//
//        //Struts ResultType Dependency
//        strutAnalyzer = new ResultTypeDepsAnalyzer();
//        List<Dependency> strutResultTypeDeps = strutAnalyzer.analyze(javaNode, strutsNodes);
//        dependencies.addAll(strutResultTypeDeps);
//
//        return dependencies;
//    }
//
//    @Override
//    public List<Dependency> analyzeDependency(List<JavaNode> javaNode, List<Node> xmlNodes, List<Node> strutsNode) throws ExecutionException, InterruptedException {
//        return null;
//    }
}
