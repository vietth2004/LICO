package com.example.xmlservice.service;

import com.example.xmlservice.ast.dependency.Dependency;
import com.example.xmlservice.dom.Node;
import com.example.xmlservice.dom.Properties.PropertiesFileNode;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface PropertiesService {

    List<PropertiesFileNode> parseProjectWithPath(String path) throws IOException, ExecutionException, InterruptedException;

    List<Dependency> analyzeDependencies(List<Node> xmlNodes, List<PropertiesFileNode> propertiesFileNodes);

}
