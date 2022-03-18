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
                findStruts(javaNodes, xmlNode, strutActionDeps, jspNodes);
            }
        }

        return strutActionDeps;
    }

    public void findStruts(List<JavaNode> javaNodes, Node xmlNode, List<Dependency> strutActionDeps, List<Node> jspNodes){
        for(XmlTagNode xmlTagNode : xmlNode.getChildren()) {
            if(xmlTagNode.getTagName().equals("struts")){
                findPackage(javaNodes, xmlTagNode, strutActionDeps, jspNodes);
            }
        }
    }

    public void findPackage(List<JavaNode> javaNodes, XmlTagNode xmlNode, List<Dependency> strutActionDeps, List<Node> jspNodes) {
        for(XmlTagNode xmlTagNode : xmlNode.getChildren()) {
            if(xmlTagNode.getTagName().equals("package")) {
                findAction(javaNodes, xmlTagNode, strutActionDeps, jspNodes);
            }

        }
    }

    public void findAction(List<JavaNode> javaNodes, XmlTagNode xmlNode, List<Dependency> strutActionDeps, List<Node> jspNodes) {
        for(XmlTagNode xmlTagNode : xmlNode.getChildren()) {

            if(xmlTagNode.getTagName().equals("action")) {
                findJavaActionDependency(javaNodes, xmlTagNode, strutActionDeps);
                findResult(xmlTagNode, strutActionDeps, jspNodes);
            }

        }
    }

    public void findJavaActionDependency(List<JavaNode> javaNodes, XmlTagNode xmlNode, List<Dependency> strutActionDeps) {
        String javaNodeQualifiedName = xmlNode.getAttributes().get("class");

        for(JavaNode javaNode : javaNodes) {
            if(javaNode.getQualifiedName().equals(javaNodeQualifiedName)) {
                strutActionDeps.add(new Dependency(xmlNode.getId(), javaNode.getId(), new DependencyCountTable(0,0,0,0,0,1)));
            }
        }
    }

    public void findResult(XmlTagNode xmlNode, List<Dependency> strutActionDeps, List<Node> jspNodes) {
        for(XmlTagNode xmlTagNode : xmlNode.getChildren()) {
            if(xmlTagNode.getTagName().equals("result")) {
                findJspResultDependency(xmlTagNode, strutActionDeps, jspNodes);
            }
        }
    }

    public void findJspResultDependency(XmlTagNode xmlNode, List<Dependency> strutActionDeps, List<Node> jspNodes) {
        String jspNode = xmlNode.getContent();

        for(Node node : jspNodes) {
            if(node.getName().equals(jspNode)) {
                strutActionDeps.add(new Dependency(xmlNode.getId(), node.getId(), new DependencyCountTable(0,0,0,0,0,1)));
            }
        }
    }
}
