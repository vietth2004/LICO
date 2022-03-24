package com.example.ciaservice.service;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.ciaservice.ast.Dependency;
import com.example.ciaservice.ast.utility.Utility;
import com.example.ciaservice.model.Response;

import org.springframework.stereotype.Service;

@Service
public class CiaServiceImpl implements CiaService{

    @Override
    public Response calculate(List<Dependency> dependencies, Integer totalNodes) {
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

        return Utility.convertMapToNodes(nodes);
    }

}
