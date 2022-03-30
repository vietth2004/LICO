package com.example.springservice.utils.worker;

import com.example.springservice.ast.annotation.JavaAnnotation;
import com.example.springservice.ast.node.JavaNode;
import com.example.springservice.ast.type.JavaType;
import com.example.springservice.dom.Node;
import com.example.springservice.resource.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Checker {


    public static List<Node> convertSpringXmlNodes(List<com.example.springservice.dom.Node> xmlTagNodes) {
        List<com.example.springservice.dom.Node> springXmlNodes = new ArrayList<>();


        return springXmlNodes;
    }

    public static Boolean containSpringAnnotations(JavaNode javaNode, List<String> conditionState) {
        for(JavaAnnotation javaAnnotation : javaNode.getAnnotates()) {
            for(String condition : conditionState) {
                if(javaAnnotation.getName().contains(condition)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Boolean isSpringInterface(JavaNode javaNode, List<String> interfaceList) {

        if(javaNode.getEntityClass().equals("JavaInterfaceNode")) {
            for(JavaType extendInterface : javaNode.getExtendInterfaces()) {
                for(String interfaceName : interfaceList) {
                    if(extendInterface.getDescribe().contains(interfaceName)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isSpring(List<String> listAttributes) {

        for(String attribute : listAttributes) {
            if(Resource.SPRING_CONFIGURATION.contains(attribute)) {
                return true;
            }
        }

        return false;
    }

}
