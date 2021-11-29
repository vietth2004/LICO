package com.example.xmlservice.utils;

import com.example.xmlservice.ast.annotation.JavaAnnotation;
import com.example.xmlservice.ast.annotation.MemberValuePair;
import com.example.xmlservice.ast.node.JavaNode;
import com.example.xmlservice.dom.Bean.JsfBeanNode;
import com.example.xmlservice.dom.Node;
import com.example.xmlservice.dom.Xml.XmlFileNode;
import com.example.xmlservice.dom.Xml.XmlTagNode;
import com.example.xmlservice.utils.Helper.FileHelper;
import com.example.xmlservice.utils.Log.ClientLevel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NodeUtils {

    private static final Logger logger = LogManager.getLogger(NodeUtils.class);

    public static void reCalculateXmlNodesId(int javaTotalNodesId, List<Node> nodes){
        nodes.forEach(node -> {
            int id = node.getId();
            node.setId(id += javaTotalNodesId);
            if(node.getChildren().size() > 0) {
                reCalculateXmlNodesId(javaTotalNodesId, node.getChildren());
            }
        });
    }

    public static List<JavaNode> flatRootNode(JavaNode rootNode) {
        List<JavaNode> nodes = new ArrayList<>();
        nodes.add(rootNode);

        if(rootNode.getChildren().size() > 0) {
            for(Object child : rootNode.getChildren()) {
                JavaNode childNode = null;
                System.out.println("Daucatmoi");
            }
        }

        return nodes;
    }

    public static List<JavaNode> findAllBean(List<JavaNode> javaNodes) {
        List<JavaNode> jsfBeans = new ArrayList<>();
        javaNodes.forEach(
                node -> {
                    if(checkNodeIsBean(node, "Named"))
                        jsfBeans.add(node);
                    if(checkNodeIsBean(node, "ManagedBean"))
                        jsfBeans.add(node);
                }
        );
        return jsfBeans;
    }

    public static List<JavaNode> findAllBeanInjection(List<JavaNode> javaNodes) {
        List<JavaNode> jsfBeanInjection = new ArrayList<>();
        javaNodes.forEach(
                node -> {
                    if(checkNodeIsBean(node, "Inject"))
                        jsfBeanInjection.add(node);
                    if(checkNodeIsBean(node, "ManagedProperty"))
                        jsfBeanInjection.add(node);
                }
        );
        return jsfBeanInjection;
    }

    public static String findBeanName(JavaNode node) {
        if(node.getAnnotatesWithValue().size() > 0) {
            for(Object obj : node.getAnnotatesWithValue()) {
                if(obj instanceof JavaAnnotation) {
                    if(((JavaAnnotation) obj).getMemberValuePair().size() > 0) {
                        for(Object pair : ((JavaAnnotation) obj).getMemberValuePair()) {
                            if(pair instanceof MemberValuePair) {
                                if(((MemberValuePair) pair).getKey().equals("name")){
                                    return ((MemberValuePair) pair).getValue().replaceAll("[^a-zA-Z0-9]", "");
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public static String findBeanInjectionName(JavaNode node) {
        for(Object obj : node.getAnnotatesWithValue()){
            if(obj instanceof JavaAnnotation) {
                if(((JavaAnnotation) obj).getMemberValuePair().size() > 0) {
                    for(MemberValuePair pair : ((JavaAnnotation) obj).getMemberValuePair()) {
                        if (pair.getKey().equals("value")) {
                            return pair.getValue().replaceAll("[^a-zA-Z0-9]", "");
                        }
                    }
                }
            }
        }
        return null;
    }

    public static boolean checkNodeIsBean(JavaNode node, String criteria) {
        if (node.getAnnotatesWithValue().size() > 0) {
            for(Object obj : node.getAnnotatesWithValue()) {
                if(obj instanceof JavaAnnotation) {
                    if(((JavaAnnotation) obj).getName().equals(criteria))
                        return true;
                }
            }
        }
        return false;
    }

    public static List<JsfBeanNode> getAllJsfBeanNode(List<JavaNode> javaNode) {
        List<JavaNode> jsfBeans = findAllBean(javaNode);
        List<JsfBeanNode> jsfBeanMap = new ArrayList<>();
        jsfBeans.forEach(
                node -> {
                    JsfBeanNode bean = new JsfBeanNode();
                    bean.setValue(node);
                    if(findBeanName(node) != null)
                        bean.setBeanName(findBeanName(node));
                    else
                        bean.setBeanName(Character.toLowerCase(node.getSimpleName().charAt(0)) + node.getSimpleName().substring(1));
                    jsfBeanMap.add(bean);
                }
        );
        return jsfBeanMap;
    }

    public static List<Node> xhtmlNodeFilter(List<Node> nodes) {
        return nodes
                .stream()
                .filter(node -> FileHelper
                        .getFileExtension(node.getName())
                        .equals("xhtml"))
                .collect(Collectors.toList());
    }



}
