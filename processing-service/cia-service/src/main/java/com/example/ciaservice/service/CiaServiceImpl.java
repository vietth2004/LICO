package com.example.ciaservice.service;



import java.util.*;

import com.example.ciaservice.Utility.Getter;
import com.example.ciaservice.Utility.Searcher;
import com.example.ciaservice.ast.Dependency;
import com.example.ciaservice.ast.JavaNode;
import com.example.ciaservice.ast.Node;
import com.example.ciaservice.ast.utility.Utility;
import com.example.ciaservice.model.Response;

import org.springframework.stereotype.Service;

@Service
public class CiaServiceImpl implements CiaService{

    @Override
    public Response calculate(List<Dependency> dependencies, Integer totalNodes) {
        Response response = new Response();
        Map<Integer, Integer> nodes = new HashMap<>(totalNodes);

        for(int i = 0; i < totalNodes; ++i) {
            nodes.put(i, 0);
        }

        for(Dependency dependency : dependencies) {
            Integer calleeNodeId = dependency.getCalleeNode();
            nodes.put(calleeNodeId, nodes.getOrDefault(calleeNodeId, 0) + Utility.calculateWeight(dependency.getType()));
        }

        for(Dependency dependency : dependencies) {
            if(dependency.getType().getMEMBER().equals(1)) {
                Integer calleeNodeId = dependency.getCalleeNode();
                Integer callerNodeId = dependency.getCallerNode();
                nodes.put(callerNodeId, nodes.getOrDefault(callerNodeId, 0) + nodes.getOrDefault(calleeNodeId, 0));
            }
        }

        response = Utility.convertMapToNodes(nodes);

        return response;
    }

    @Override
    public Response findImpact(List<JavaNode> javaNodes, List<Dependency> dependencies, Integer totalNodes, List<Integer> changedNodes) {
        List<Node> nodes = calculate(dependencies, totalNodes).getNodes();
        Set<Node> affectedNodes = new HashSet<>();

        for(Integer javaNode : changedNodes) {
            JavaNode changedNode = Searcher.findJavaNode(javaNodes, javaNode);
            Getter.gatherImpactFromDependencies(nodes, javaNodes, totalNodes, changedNode, affectedNodes, 3);
        }

        Response response = Utility.convertSetToNodes(affectedNodes);

        return response;
    }



}
