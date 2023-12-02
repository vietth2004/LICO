package com.example.jsfservice.service;

import com.example.jsfservice.ast.dependency.Dependency;
import com.example.jsfservice.dom.Node;
import com.example.jsfservice.dom.Properties.PropertiesFileNode;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface PropertiesService {

    List<PropertiesFileNode> parseProjectWithPath(String path) throws IOException, ExecutionException, InterruptedException;
    List<Dependency> analyzeDependencies(List<Node> xmlNodes, List<PropertiesFileNode> propertiesFileNodes);

}
