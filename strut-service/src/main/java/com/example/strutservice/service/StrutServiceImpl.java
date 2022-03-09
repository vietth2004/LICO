package com.example.strutservice.service;

import com.example.strutservice.analyzer.StrutAnalyzer;
import com.example.strutservice.ast.dependency.Dependency;
import com.example.strutservice.ast.node.JavaNode;
import com.example.strutservice.dom.Jsp.JspFileNode;
import com.example.strutservice.dom.Node;
import com.example.strutservice.parser.StrutsJspParser;
import com.example.strutservice.utils.Converter;
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

        List<Node> xmlNodes = new ArrayList<>();
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

        List<com.example.strutservice.ast.node.Node> nodeList = Converter.convertStrutsNodesToNodes(nodes);

        return nodes;
    }

    @Override
    public List<com.example.strutservice.ast.node.Node> parseProject(String folderPath) throws IOException {

        List<Node> xmlNodes = new ArrayList<>();
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

        List<com.example.strutservice.ast.node.Node> nodeList = Converter.convertStrutsNodesToNodes(nodes);

        return nodeList;
    }

    @Override
    public List<Node> parseProjectWithFile(MultipartFile file) throws IOException {
        return null;
    }

    @Override
    public List<Dependency> analyzeDependency(List<JavaNode> javaNode, List<Node> xmlNodes) throws ExecutionException, InterruptedException {
        List<Dependency> dependencies = new ArrayList<>();

        //Struts Action Dependency
        List<Dependency> strutActionDeps = StrutAnalyzer.actionDependencyAnalyzer(javaNode, xmlNodes);
        dependencies.addAll(strutActionDeps);

        //Struts Dependency
        List<Dependency> strutDeps = StrutAnalyzer.strutsDependencyAnalyzer(javaNode, xmlNodes);
        dependencies.addAll(strutDeps);

        //Struts Interceptor Dependency
        List<Dependency> strutInterceptorDeps = StrutAnalyzer.strutInterceptorDepsAnalyzer(javaNode, xmlNodes);
        dependencies.addAll(strutInterceptorDeps);

        //Struts Interceptor Stack Dependency
        List<Dependency> strutInterceptorStackDeps = StrutAnalyzer.strutInterceptorStackDepsAnalyzer(javaNode, xmlNodes);
        dependencies.addAll(strutInterceptorStackDeps);

        //Struts Jsp Dependency
        List<Dependency> strutJspDeps = StrutAnalyzer.strutJspDepsAnalyzer(javaNode, xmlNodes);
        dependencies.addAll(strutJspDeps);

        //Struts Package Dependency
        List<Dependency> strutPackageDeps = StrutAnalyzer.strutPackageDepsAnalyzer(javaNode, xmlNodes);
        dependencies.addAll(strutPackageDeps);

        //Struts Result Dependency
        List<Dependency> strutResultDeps = StrutAnalyzer.strutResultDepsAnalyzer(javaNode, xmlNodes);
        dependencies.addAll(strutResultDeps);

        //Struts ResultType Dependency
        List<Dependency> strutResultTypeDeps = StrutAnalyzer.strutResultTypeDepsAnalyzer(javaNode, xmlNodes);
        dependencies.addAll(strutResultTypeDeps);


        return dependencies;
    }
}
