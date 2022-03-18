package com.example.strutsservice.analyzer;

import com.example.strutsservice.ast.dependency.Dependency;
import com.example.strutsservice.ast.dependency.DependencyCountTable;
import com.example.strutsservice.ast.node.JavaNode;
import com.example.strutsservice.dom.Node;
import com.example.strutsservice.dom.Xml.XmlTagNode;

import java.util.ArrayList;
import java.util.List;

public class ActionDependencyAnalyzer implements StrutAnalyzer{

    @Override
    public List<Dependency> analyze(List<JavaNode> javaNodes, List<Node> jspNodes, List<Node> xmlNodes) {
        List<Dependency> strutActionDeps = new ArrayList<>();

        for(Node xmlNode : xmlNodes) {
            if(xmlNode != null){
                findStruts(javaNodes, xmlNode, strutActionDeps);
            }
        }

        return strutActionDeps;
    }

    public void findStruts(List<JavaNode> javaNodes, Node xmlNode, List<Dependency> strutActionDeps){
        for(XmlTagNode xmlTagNode : xmlNode.getChildren()) {
            if(xmlTagNode.getTagName().equals("struts")){
                findPackage(javaNodes, xmlTagNode, strutActionDeps);
            }
        }
    }

    public void findPackage(List<JavaNode> javaNodes, XmlTagNode xmlNode, List<Dependency> strutActionDeps) {
        for(XmlTagNode xmlTagNode : xmlNode.getChildren()) {
            if(xmlTagNode.getTagName().equals("package")) {
                findAction(javaNodes, xmlTagNode, strutActionDeps);
            }

        }
    }

    public void findAction(List<JavaNode> javaNodes, XmlTagNode xmlNode, List<Dependency> strutActionDeps) {
        for(XmlTagNode xmlTagNode : xmlNode.getChildren()) {

            if(xmlTagNode.getTagName().equals("action")) {
                findDependency(javaNodes, xmlTagNode, strutActionDeps);
            }

        }
    }

    public void findDependency(List<JavaNode> javaNodes, XmlTagNode xmlNode, List<Dependency> strutActionDeps) {
        String javaNodeQualifiedName = xmlNode.getAttributes().get("class");

        for(JavaNode javaNode : javaNodes) {
            if(javaNode.getQualifiedName().equals(javaNodeQualifiedName)) {
                strutActionDeps.add(new Dependency(javaNode.getId(), xmlNode.getId(), new DependencyCountTable(0,0,0,0,0,1)));
            }
        }
    }
}
