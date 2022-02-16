package com.example.strutservice.service;

import com.example.strutservice.ast.dependency.Dependency;
import com.example.strutservice.ast.node.JavaNode;
import com.example.strutservice.dom.Node;
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

    @Override
    public List<Node> parseProjectWithPath(String foldePath) throws IOException, ExecutionException, InterruptedException {

        List<Node> xmlNodes = new ArrayList<>();
        Path path = Paths.get(foldePath);
        List<Path> paths = FileHelper.listFiles(path);
        Set<File> subDirs = FileHelper.listAllSubDirs(new File(path.toString()));

        return null;
    }

    @Override
    public List<Node> parseProjectWithFile(MultipartFile file) throws IOException {
        return null;
    }

    @Override
    public List<Dependency> analyzeDependency(List<JavaNode> javaNode, List<Node> xmlNodes) throws ExecutionException, InterruptedException {
        return null;
    }
}
