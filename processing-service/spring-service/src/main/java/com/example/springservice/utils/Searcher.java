package com.example.springservice.utils;

import com.example.springservice.ast.node.JavaNode;

import java.util.List;

public class Searcher {

    public static JavaNode searchJavaNode(Integer id, List<JavaNode> javaNodes) {
        JavaNode javaNode = new JavaNode();


        for (JavaNode obj : javaNodes) {
            if(obj.getId().equals(id)) {
                javaNode = obj;
            }
        }


        return javaNode;
    }
}
