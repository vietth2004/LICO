package com.example.strutsservice.analyzer;

import com.example.strutsservice.ast.dependency.Dependency;
import com.example.strutsservice.ast.node.JavaNode;
import com.example.strutsservice.dom.Node;
import com.example.strutsservice.dom.Xml.XmlTagNode;

import java.util.ArrayList;
import java.util.List;

public class ActionDependencyAnalyzer implements StrutAnalyzer{

    @Override
    public List<Dependency> analyze(List<JavaNode> javaNodes, List<Node> strutsNodes, List<Node> xmlNodes) {
        List<Dependency> strutActionDeps = new ArrayList<>();

        for(Node xmlNode : xmlNodes) {
            findDenpendency(javaNodes, xmlNode, strutActionDeps);
        }

        return strutActionDeps;
    }

    public void findDenpendency(List<JavaNode> javaNodes, Node xmlNode, List<Dependency> strutActionDeps){
//        for(XmlTagNode xmlTagNode : xmlNode.getChildren()) {
//            if(xmlTagNode.getTagName().equals()){
//
//            }
//        }
    }
}
