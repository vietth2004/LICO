package com.example.springservice.dependency;

import com.example.springservice.ast.node.JavaNode;
import com.example.springservice.dom.Node;

import java.util.List;

public interface DependencyService {

    List<Dependency> getDependencies(List<JavaNode> javaNodeList, List<Node> xmlTagNodes);

}
