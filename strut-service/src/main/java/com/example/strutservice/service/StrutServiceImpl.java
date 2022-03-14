package com.example.strutservice.service;

import com.example.strutservice.analyzer.*;
import com.example.strutservice.ast.dependency.Dependency;
import com.example.strutservice.ast.node.JavaNode;
import com.example.strutservice.dom.Jsp.JspFileNode;
import com.example.strutservice.dom.Node;
import com.example.strutservice.dom.Xml.XmlFileNode;
import com.example.strutservice.parser.StrutsJspParser;
import com.example.strutservice.parser.XmlFileParser;
import com.example.strutservice.utils.Exception.JciaException;
import com.example.strutservice.utils.Exception.JciaNotFoundException;
import com.example.strutservice.utils.Helper.FileHelper;
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
public class StrutServiceImpl implements StrutService{

    private final ExecutorService THREADPOOL_FIXED_SIZE = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private StrutsJspParser strutsJspParser = new StrutsJspParser();

    @Override
    public List<Node> parseProjectWithPath(String folderPath) throws IOException, ExecutionException, InterruptedException {

        Path path = Paths.get(folderPath);
        List<Path> paths = FileHelper.listFiles(path);
        List<Node> nodes = new ArrayList<>();

        paths.forEach(p -> {

            if(p.toString().endsWith(".jsp")) {
                Node jspNode = new JspFileNode();
                jspNode.setName(new File(p.toString()).getName());
                jspNode.setAbsolutePath(p.toString());
                nodes.add(strutsJspParser.parse(jspNode));
            }
        });



        return nodes;
    }


    @Override
    public List<Node> parseProjectWithFile(MultipartFile file) throws IOException {
        return null;
    }

    @Override
    public List<Dependency> analyzeDependency(List<JavaNode> javaNode, List<Node> strutsNodes) throws ExecutionException, InterruptedException {
        List<Dependency> dependencies = new ArrayList<>();
        StrutAnalyzer strutAnalyzer;

        //Struts Action Dependency
        strutAnalyzer = new ActionDependencyAnalyzer();
        List<Dependency> strutActionDeps = strutAnalyzer.analyze(javaNode, strutsNodes);
        dependencies.addAll(strutActionDeps);

        //Struts Dependency
//        List<Dependency> strutDeps = strutAnalyzer.analyze(javaNode, strutsNodes);
//        dependencies.addAll(strutDeps);

        //Struts Interceptor Dependency
        strutAnalyzer = new InterceptorDepsAnalyzer();
        List<Dependency> strutInterceptorDeps = strutAnalyzer.analyze(javaNode, strutsNodes);
        dependencies.addAll(strutInterceptorDeps);

        //Struts Interceptor Stack Dependency
        strutAnalyzer = new InterceptorStackDepsAnalyzer();
        List<Dependency> strutInterceptorStackDeps = strutAnalyzer.analyze(javaNode, strutsNodes);
        dependencies.addAll(strutInterceptorStackDeps);

        //Struts Jsp Dependency
        strutAnalyzer = new JspDepsAnalyzer();
        List<Dependency> strutJspDeps = strutAnalyzer.analyze(javaNode, strutsNodes);
        dependencies.addAll(strutJspDeps);

        //Struts Package Dependency
        strutAnalyzer = new PackageDepsAnalyzer();
        List<Dependency> strutPackageDeps = strutAnalyzer.analyze(javaNode, strutsNodes);
        dependencies.addAll(strutPackageDeps);

        //Struts Result Dependency
        strutAnalyzer = new ResultDepsAnalyzer();
        List<Dependency> strutResultDeps = strutAnalyzer.analyze(javaNode, strutsNodes);
        dependencies.addAll(strutResultDeps);

        //Struts ResultType Dependency
        strutAnalyzer = new ResultTypeDepsAnalyzer();
        List<Dependency> strutResultTypeDeps = strutAnalyzer.analyze(javaNode, strutsNodes);
        dependencies.addAll(strutResultTypeDeps);


        return dependencies;
    }
}
