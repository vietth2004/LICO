package com.example.versioncompareservice.utils;

import com.example.versioncompareservice.ast.annotation.JavaAnnotation;
import com.example.versioncompareservice.ast.node.JavaNode;
import com.example.versioncompareservice.dom.Node;
import com.example.versioncompareservice.dom.Properties.PropertiesFileNode;
import com.example.versioncompareservice.dom.Xml.XmlTagNode;
import com.example.versioncompareservice.utils.Helper.FileHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ParserUtils {

    /**
     * Recalculate id in all xml nodes by total javaNods
     * @param javaTotalNodesId
     * @param nodes
     */
    public static void reCalculateXmlNodesId(int javaTotalNodesId, List<Node> nodes){
        nodes.forEach(node -> {
            int id = node.getId();
            node.setId(id += javaTotalNodesId);
            if(node.getNodeChildren().size() > 0) {
                reCalculateXmlNodesId(javaTotalNodesId, node.getNodeChildren());
            }
        });
    }

    /**
     * flat nested java rootNode to array of java node
     * @param rootNode
     * @return
     */
    public static List<JavaNode> flatRootNode(JavaNode rootNode) {
        List<JavaNode> nodes = new ArrayList<>();
        nodes.add(rootNode);

        if(rootNode.getChildren().size() > 0) {
            for(Object child : rootNode.getChildren()) {
                JavaNode childNode = null;
            }
        }

        return nodes;
    }

    /**
     * Check if node is bean
     * @param node
     * @param criteria (@Named, @ManagedBean, @Inject, @ManagedProperty)
     * @return
     */
    public static boolean checkNodeIsBean(JavaNode node, String criteria) {
        if (node.getAnnotates() != null) {
            for(Object obj : node.getAnnotates()) {
                if(obj instanceof JavaAnnotation) {
                    if(((JavaAnnotation) obj).getName().endsWith(criteria))
                        return true;
                }
            }
        }
        return false;
    }

    /**
     * filter all children XmlTagNode of XmlFileNode
     * @param xmlFileNodes
     * @return
     */
    public static List<Node> getChildrenLevel1XmlFileNode(List<Node> xmlFileNodes) {
        List<Node> nodes = new ArrayList<>();
        for(Node node : xmlFileNodes) {
            if(node instanceof Node) {
                nodes.addAll(node.getNodeChildren());
            }
        }
        return nodes;
    }

    /**
     * Get java node by name
     * @param nodes
     * @param name
     * @return
     */
    public static JavaNode findJavaNodeByName(List<JavaNode> nodes, String name) {
        List<JavaNode> result = nodes
                .stream()
                .filter(node -> node.getUniqueName().equals(name))
                .collect(Collectors.toList());
        if(!result.isEmpty()) return result.get(0);
        //TODO: Need to fix this one, some bean has not defined with uniquename, so I cant get bean for it
        return null;
    }

    /**
     * find PropertiesFileNode by passed name
     * @param propsFileNodes
     * @param name
     * @return
     */
    public static PropertiesFileNode findPropsFileNodeByName(List<PropertiesFileNode> propsFileNodes, String name) {
        List<PropertiesFileNode> result = propsFileNodes
                .stream()
                .filter(node -> node.getFullyQualifiedName().contains(name))
                .collect(Collectors.toList());
        if(!result.isEmpty()) return result.get(0);
        return new PropertiesFileNode();
    }

    /**
     * get name for beanNode
     * value from var tagNode, child of resource-bundle tagNode
     * @param node
     * @return
     */
    public static String prepareBeanNodeName(Node node) {
        for(Node child : node.getChildren()) {
            if(child instanceof XmlTagNode) {
                if(((XmlTagNode) child).getTagName().equals("var")) {
                    return ((XmlTagNode) child).getContent();
                }
            }
        }
        return null;
    }

}
