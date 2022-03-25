package com.example.springservice.dependency;

import com.example.springservice.ast.node.JavaNode;
import com.example.springservice.dom.Xml.XmlTagNode;

import java.util.List;

public interface DependencyService {

    public List<Dependency> getAllDependency(List<JavaNode> javaNodeList, List<XmlTagNode> xmlTagNodes);

}
