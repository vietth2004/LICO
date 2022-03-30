package com.example.parserservice.util.worker;

import com.example.parserservice.ast.dependency.Dependency;
import com.example.parserservice.ast.dependency.OrientedDependency;
import com.example.parserservice.ast.dependency.OrientedPair;
import com.example.parserservice.ast.dependency.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Converter {

    public static List<OrientedDependency> convertToOrientedDependencies(List<Dependency> dependencies) {
        Map<Integer, List<OrientedPair>> orientedDependencyMap = new HashMap<>();


        for(Dependency dependency : dependencies) {
            if(orientedDependencyMap.containsKey(dependency.getCallerNode())){
                orientedDependencyMap
                        .get(dependency.getCallerNode())
                        .add(new OrientedPair(dependency.getCalleeNode(), dependency.getType()));
            } else {
                List<OrientedPair> orientedPairs = new ArrayList<>();
                orientedPairs.add(new OrientedPair(dependency.getCalleeNode(), dependency.getType()));

                orientedDependencyMap
                        .put(dependency.getCallerNode(), orientedPairs);
            }
        }

        List<OrientedDependency> orientedDependencies = new ArrayList<>();

        for(Integer id : orientedDependencyMap.keySet()) {
            orientedDependencies.add(new OrientedDependency(id, orientedDependencyMap.get(id)));
        }


        return orientedDependencies;
    }
}
