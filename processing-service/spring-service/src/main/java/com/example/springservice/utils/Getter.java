package com.example.springservice.utils;

import com.example.springservice.ast.dependency.Pair;
import com.example.springservice.ast.node.JavaNode;
import com.example.springservice.dependency.Dependency;

import java.util.List;

public class Getter {

    public static Integer getInheritanceNode(List<Pair> javaNodes) {
        Integer id = -1;

        for(Pair obj : javaNodes) {
            if(obj.getDependency().getINHERITANCE() > 0) {
                return obj.getNode().getId();
            }
        }

        return id;
    }
}
