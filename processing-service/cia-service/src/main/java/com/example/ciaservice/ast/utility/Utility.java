package com.example.ciaservice.ast.utility;

import com.example.ciaservice.ast.DependencyCountTable;
import com.example.ciaservice.ast.Node;
import com.example.ciaservice.model.Response;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Utility {

    public static Integer calculateWeight(DependencyCountTable dependencyCountTable) {
        Integer weight =
                dependencyCountTable.getINHERITANCE()*4
                + dependencyCountTable.getINVOCATION()*4
                + dependencyCountTable.getMEMBER()
                + dependencyCountTable.getOVERRIDE()
                + dependencyCountTable.getSPRING()
                + dependencyCountTable.getUSE()
                + dependencyCountTable.getJSF()
                + dependencyCountTable.getSTRUTS()
                ;
        return weight;
    }

    public static Response convertMapToNodes(Map<Integer, Integer> nodes) {
        List<Node> nodeList = new ArrayList<>();

        for(Integer id : nodes.keySet()) {
            nodeList.add(new Node(id, nodes.get(id)));
        }

        return new Response(nodeList);
    }
}
