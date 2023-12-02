package com.example.strutsservice.service;

import com.example.strutsservice.analyzer.ActionDependencyAnalyzer;
import com.example.strutsservice.analyzer.ResultDepsAnalyzer;
import com.example.strutsservice.analyzer.StrutAnalyzer;
import com.example.strutsservice.ast.dependency.Dependency;
import com.example.strutsservice.ast.node.JavaNode;
import com.example.strutsservice.dom.Node;
import com.example.strutsservice.utils.communicator.Request;
import com.example.strutsservice.utils.communicator.Response;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class StrutsServiceImpl implements StrutsService{


    @Override
    public Response getDependency(Request request) {

        List<Dependency> dependencies = new ArrayList<>();
        StrutAnalyzer strutAnalyzer;

        List<JavaNode> javaNodes = request.getJavaNodes();
        List<Node> xmlNodes = request.getXmlNodes();
        List<Node> jspNodes = request.getJspNodes();

        // Java - Action - Result Dependency
        strutAnalyzer = new ActionDependencyAnalyzer();
        dependencies.addAll(strutAnalyzer.analyze(javaNodes, jspNodes, xmlNodes));

        return new Response(dependencies);
    }


}
