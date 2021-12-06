package com.example.xmlservice.utils;

import com.example.xmlservice.ast.annotation.JavaAnnotation;
import com.example.xmlservice.ast.annotation.MemberValuePair;
import com.example.xmlservice.ast.node.JavaNode;
import com.example.xmlservice.dom.Bean.JsfBeanNode;
import com.example.xmlservice.dom.Bean.XmlBeanInjectionNode;
import com.example.xmlservice.dom.Node;
import com.example.xmlservice.dom.Xml.XmlFileNode;
import com.example.xmlservice.dom.Xml.XmlTagNode;
import com.example.xmlservice.utils.Helper.FileHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class NodeUtils {

    private static final Logger logger = LogManager.getLogger(NodeUtils.class);

    /**
     * Recalculate id in all xml nodes by total javaNods
     * @param javaTotalNodesId
     * @param nodes
     */
    public static void reCalculateXmlNodesId(int javaTotalNodesId, List<Node> nodes){
        nodes.forEach(node -> {
            int id = node.getId();
            node.setId(id += javaTotalNodesId);
            if(node.getChildren().size() > 0) {
                reCalculateXmlNodesId(javaTotalNodesId, node.getChildren());
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
                System.out.println("Daucatmoi");
            }
        }

        return nodes;
    }

    /**
     * find all bean by 2 annotation @Named and @ManagedBean
     * @param javaNodes
     * @return
     */
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

    /**
     * find all Injected javaNode
     * (defined by 2 annotation @ManagedProperty and @Inject)
     * @param javaNodes
     * @return
     */
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

    /**
     * find bean name in annotation using regex pattern of name: #{...}
     * @param node
     * @return
     */
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

    /**
     * find name of bean injected to javaNode
     * (defined by 2 annotations: @ManagedProperty and @Inject)
     * @param node
     * @return
     */
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

    /**
     * Check if node is bean
     * @param node
     * @param criteria (@Named, @ManagedBean, @Inject, @ManagedProperty)
     * @return
     */
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

    /**
     * Get all jsf bean nodes
     * If annotation has no attribute
     * Then bean's name is set to camel case of class
     * @param javaNode
     * @return
     */
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

    /**
     * filter all .xhtml file
     * @param nodes
     * @return
     */
    public static List<Node> xhtmlNodeFilter(List<Node> nodes) {
        return nodes
                .stream()
                .filter(node -> FileHelper
                        .getFileExtension(node.getName())
                        .equals("xhtml"))
                .collect(Collectors.toList());
    }

    /**
     * filter all children XmlTagNode of XmlFileNode
     * @param xmlFileNodes
     * @return
     */
    public static List<Node> getChildrenLevel1XmlFileNode(List<Node> xmlFileNodes) {
        List<Node> nodes = new ArrayList<>();
        for(Node node : xmlFileNodes) {
            if(node instanceof XmlFileNode) {
                nodes.addAll(node.getChildren());
            }
        }
        return nodes;
    }

    /**
     * filter all value match pattern #{...} by using  regex
     * @param node
     * @return
     */
    public static Set<XmlBeanInjectionNode> filterTagNode(Node node) {
        Set<XmlBeanInjectionNode> nodes = new HashSet<>();
        Pattern pattern = Pattern.compile("\\#\\{(.*?)}");
        if(node instanceof XmlTagNode) {

            /**
             * analyze injected bean in node's attributes
             */
            for(String value : ((XmlTagNode) node).getAttributes().values())  {
                Matcher matcher = pattern.matcher(value);
                if(matcher.matches()){
                    XmlBeanInjectionNode beanInjectionNode = new XmlBeanInjectionNode();
                    beanInjectionNode.setBeanInjection(value.replaceAll("[^a-zA-Z0-9.\\[\\]]", ""));
                    beanInjectionNode.setValue(node);
                    nodes.add(beanInjectionNode);
                }
            }

            /**
             * analyze injected bean in node's content
             */
            if(((XmlTagNode) node).getContent() != null){
                Matcher matcherContent = pattern.matcher(((XmlTagNode) node).getContent());
                if (matcherContent.matches()) {
                    XmlBeanInjectionNode beanInjectionNode = new XmlBeanInjectionNode();
                    beanInjectionNode.setBeanInjection(((XmlTagNode) node).getContent().replaceAll("[^a-zA-Z0-9.\\[\\]]", ""));
                    beanInjectionNode.setValue(node);
                    nodes.add(beanInjectionNode);
                }
            }
            for(Node child : node.getChildren()) {
                nodes.addAll(filterTagNode(child));
            }
        }
        return nodes;
    }

    /**
     * get all custom beans config from faces-config.xml file
     * @param tagNode
     * @param nodes
     * @return
     */
    public static Set<JsfBeanNode> filterBeanFromFacesConfig(Node tagNode, List<JavaNode> nodes) {
        Set<JsfBeanNode> jsfBeanNodes = new HashSet<>();

        if(tagNode instanceof XmlTagNode) {
            if(((XmlTagNode) tagNode).getTagName().equals("resource-bundle")) {
                JsfBeanNode beanNode = new JsfBeanNode();
                beanNode.setValue(prepareBeanNodeValue(tagNode, nodes));
                beanNode.setBeanName(prepareBeanNodeName(tagNode));
                jsfBeanNodes.add(beanNode);
            }
            for(Node child : tagNode.getChildren()) {
                jsfBeanNodes.addAll(filterBeanFromFacesConfig(child, nodes));
            }
        }
        return jsfBeanNodes;
    }

    /**
     * Get java node by name
     * @param nodes
     * @param name
     * @return
     */
    public static JavaNode findJavaNodeByName(List<JavaNode> nodes, String name) {
        List<JavaNode> result = nodes.stream().filter(
                node -> node.getUniqueName().equals(name)
        ).collect(Collectors.toList());
        if(!result.isEmpty()) return result.get(0);
        //TODO: Need to fix this one, some bean has not defined with uniquename, so I cant get bean for it
        return new JavaNode();
    }

    /**
     * get value for beanNode
     * value from base-name tagNode, child of resource-bundle tagNode
     * @param node
     * @param nodes
     * @return
     */
    public static JavaNode prepareBeanNodeValue(Node node, List<JavaNode> nodes) {
        JavaNode beanNode = new JavaNode();
        for(Node child : node.getChildren()) {
            if (child instanceof XmlTagNode) {
                String tagName = ((XmlTagNode) child).getTagName();
                if(tagName.equals("base-name")) {
                    JavaNode value = findJavaNodeByName(nodes, ((XmlTagNode) child).getContent());
                    return value;
                }
            }
        }
        return null;
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
