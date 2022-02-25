package com.example.strutservice.service;

import com.example.strutservice.ast.dependency.Dependency;
import com.example.strutservice.ast.node.JavaNode;
import com.example.strutservice.dom.Jsp.JspFileNode;
import com.example.strutservice.dom.Node;
import com.example.strutservice.parser.StrutsJspParser;
import com.example.strutservice.utils.Helper.FileHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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

        return nodes;
    }

    @Override
    public List<Node> parseProjectWithFile(MultipartFile file) throws IOException {
        return null;
    }

    @Override
    public List<Dependency> analyzeDependency(List<JavaNode> javaNode, List<Node> xmlNodes) throws ExecutionException, InterruptedException {
        List<Dependency> dependencies = new ArrayList<>();
        List<Dependency> strutActionDeps;
        List<Dependency> strutDeps;
        List<Dependency> strutInterceptorDeps;
        List<Dependency> strutInterceptorStackDeps;
        List<Dependency> strutJspDeps;
        List<Dependency> strutPackageDeps;
        List<Dependency> strutResultDeps;
        List<Dependency> strutResultTypeDeps;
        return dependencies;
    }
}
