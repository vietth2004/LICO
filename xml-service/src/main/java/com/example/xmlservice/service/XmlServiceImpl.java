package com.example.xmlservice.service;

import com.example.xmlservice.ast.dependency.Dependency;
import com.example.xmlservice.ast.dependency.DependencyCountTable;
import com.example.xmlservice.ast.node.JavaNode;
import com.example.xmlservice.dom.Bean.JsfBeanInjectionNode;
import com.example.xmlservice.dom.Bean.JsfBeanNode;
import com.example.xmlservice.dom.Bean.XmlBeanInjectionNode;
import com.example.xmlservice.dom.Node;
import com.example.xmlservice.parser.XmlFileParser;
import com.example.xmlservice.utils.Exception.JciaNotFoundException;
import com.example.xmlservice.utils.Helper.FileHelper;
import com.example.xmlservice.utils.Helper.StringHelper;
import com.example.xmlservice.utils.Log.ClientLevel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.xmlservice.utils.NodeUtils.*;

@Service
public class XmlServiceImpl implements XmlService {

    private final Logger logger = LogManager.getLogger(XmlServiceImpl.class);

    public XmlServiceImpl(){}

    @Override
    public List<Node> parseProjectWithPath(String folderPath) throws IOException {
        XmlFileParser xmlFileParser = new XmlFileParser();
        List<Node> nodes = new ArrayList<>();

        Path path = Paths.get(folderPath);
        List<Path> paths = FileHelper.listFiles(path);
        paths.forEach(x -> {
            if(StringHelper.SUPPORTED_EXTENSIONS.contains(FileHelper.getFileExtension(x.toString()))){
                try {
                    Node parsedNode = xmlFileParser.parse(x.toString());
                    if(parsedNode != null){
                        nodes.add(parsedNode);
                    }
                } catch (JciaNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        logger.log(ClientLevel.CLIENT, nodes.toArray().length);
        return nodes;
    }

    /**
     * No need to do this thing
     * @param file (.zip file format)
     * @return arrays of xml nodes
     * @throws IOException
     */
    @Override
    public List<Node> parseProjectWithFile(MultipartFile file) throws IOException {
        return null;
    }

    @Override
    public List<Dependency> analyzeDependency(List<JavaNode> javaNode, List<Node> xmlNodes) {
        List<Dependency> dependencies = new ArrayList<>();
        dependencies.addAll(analyzeDependencyBetweenBeans(javaNode));
        dependencies.addAll(analyzeDependencyFromBeanToView(javaNode, xmlNodes));
        dependencies.addAll(analyzeDependencyFromControllerToView(javaNode, xmlNodes));
        return dependencies;
    }

    /**
     * get all dependencies from java bean to injected java bean node
     * @param nodes
     * @return
     */
    public List<Dependency> analyzeDependencyBetweenBeans(List<JavaNode> nodes){
        List<Dependency> dependencies = new ArrayList<>();
        List<JsfBeanNode> jsfBeanMap = getAllJsfBeanNode(nodes);

        List<JavaNode> jsfBeansInjection = findAllBeanInjection(nodes);
        List<JsfBeanInjectionNode> jsfBeanInjectionMap = new ArrayList<>();
        jsfBeansInjection.forEach(
                node -> {
                    JsfBeanInjectionNode beanInjection = new JsfBeanInjectionNode();
                    beanInjection.setValue(node);
                    if(findBeanInjectionName(node) != null)
                        beanInjection.setBeanInjection(findBeanInjectionName(node));
                    else
                        beanInjection.setBeanInjection(Character.toLowerCase(node.getSimpleName().charAt(0)) + node.getSimpleName().substring(1));
                    jsfBeanInjectionMap.add(beanInjection);
                }
        );

        for(JsfBeanNode beanNode : jsfBeanMap) {
            for(JsfBeanInjectionNode injectionNode : jsfBeanInjectionMap) {
                if(injectionNode.getBeanInjection().equals(beanNode.getBeanName())) {
                    logger.log(ClientLevel.CLIENT, "Bean inject bean: " + beanNode.getValue().getSimpleName() + " ==> " + injectionNode.getValue().getQualifiedName());
                    dependencies.add(new Dependency(
                            injectionNode.getValue().getId(),
                            beanNode.getValue().getId(),
                            new DependencyCountTable(0,0,0,0,0, 1)
                    ));
                }
            }
        }

        return dependencies;
    }

    /**
     * get all dependencies from java bean to xhtml fileNode
     * injected bean has pattern like #{...}
     * @param javaNode
     * @param xmlNodes
     * @return
     */
    public List<Dependency> analyzeDependencyFromBeanToView(List<JavaNode> javaNode, List<Node> xmlNodes){
        List<Dependency> dependencies = new ArrayList<>();
        List<JsfBeanNode> beanNodes = getAllJsfBeanNode(javaNode);

        /**
         * Get all javaBean nodes
         */
        List<Node> xhtmlNodes = xhtmlNodeFilter(xmlNodes);
        List<Node> allChildren = getChildrenLevel1XmlFileNode(xhtmlNodes);
        List<XmlBeanInjectionNode> injectionNodes = new ArrayList<>();
        for(Node child : allChildren) {
            injectionNodes.addAll(filterTagNode(child));
        }

        /**
         * Get all custom bean from faces-config.xml file
         */
        List<Node> faceConfig = xmlNodes
                .stream()
                .filter(node -> node.getName().equals("faces-config.xml"))
                .collect(Collectors.toList());

        for(Node node : getChildrenLevel1XmlFileNode(faceConfig)) {
            beanNodes.addAll(filterBeanFromFacesConfig(node, javaNode));
        }

        /**
         * traversal function to analyze dependencies
         */
        for(XmlBeanInjectionNode injectionNode : injectionNodes) {
            for(JsfBeanNode beanNode : beanNodes) {

                /**
                 * analyze dependencies if bean has pattern #{abc.def()}
                 */
                if(injectionNode.getBeanInjection().contains(".")) {
                    String beanInjectionName = injectionNode.getBeanInjection().split("\\.")[0];
                    String beanName = beanNode.getBeanName();
                    if(beanName.equals(beanInjectionName)) {
                        logger.log(ClientLevel.CLIENT, "Bean inject xhtml: " + beanNode.getValue().getSimpleName() + " ==> " + injectionNode.getValue().getAbsolutePath());
                        dependencies.add(new Dependency(
                                injectionNode.getValue().getId(),
                                beanNode.getValue().getId(),
                                new DependencyCountTable(0,0,0,0,0, 1)
                        ));
                    }
                } else {
                    /**
                     * analyze dependencies if bean has pattern like #{abc}
                     */
                    String beanInjectionName = injectionNode.getBeanInjection();
                    String beanName = beanNode.getBeanName();
                    if(beanName.equals(beanInjectionName)) {
                        logger.log(ClientLevel.CLIENT, "Bean inject xhtml: " + beanNode.getValue().getSimpleName() + " ==> " + injectionNode.getValue().getAbsolutePath());
                        dependencies.add(new Dependency(
                                injectionNode.getValue().getId(),
                                beanNode.getValue().getId(),
                                new DependencyCountTable(0,0,0,0,0, 1)
                        ));
                    }
                }

                /**
                 * analyze dependencies if custom bean config
                 */
                if(injectionNode.getBeanInjection().contains("[")) {
                    String beanInjectionName = injectionNode.getBeanInjection().split("\\[")[0];
                    String beanName = beanNode.getBeanName();
                    if(beanName.equals(beanInjectionName)) {
                        logger.log(ClientLevel.CLIENT, "Bean config in faces inject xhtml: " + beanNode.getValue().getSimpleName() + " ==> " + injectionNode.getValue().getAbsolutePath());
                        dependencies.add(new Dependency(
                                injectionNode.getValue().getId(),
                                beanNode.getValue().getId(),
                                new DependencyCountTable(0,0,0,0,0, 1)
                        ));
                    }
                }
            }
        }

        return dependencies;
    }

    /**
     * get all dependencies from javaNode
     * MVC pattern
     * @param node
     * @param xmlNodes
     * @return
     */
    public List<Dependency> analyzeDependencyFromControllerToView(List<JavaNode> node, List<Node> xmlNodes){
        List<Dependency> dependencies = new ArrayList<>();
        return dependencies;
    }

}
