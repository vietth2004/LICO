package com.example.xmlservice.service;

import com.example.xmlservice.ast.dependency.Dependency;
import com.example.xmlservice.ast.dependency.DependencyCountTable;
import com.example.xmlservice.ast.node.JavaNode;
import com.example.xmlservice.dom.Bean.JsfBeanInjectionNode;
import com.example.xmlservice.dom.Bean.JsfBeanNode;
import com.example.xmlservice.dom.Bean.XmlBeanInjectionNode;
import com.example.xmlservice.dom.Node;
import com.example.xmlservice.parser.XmlFileParser;
import com.example.xmlservice.utils.Helper.FileHelper;
import com.example.xmlservice.utils.Helper.StringHelper;
import com.example.xmlservice.utils.Log.ClientLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static com.example.xmlservice.utils.NodeUtils.*;

@Service
public class XmlServiceImpl implements XmlService {

    private final Logger logger = LoggerFactory.getLogger(XmlServiceImpl.class);
    private final ExecutorService THREADPOOL_FIXED_SIZE = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public XmlServiceImpl(){}

    @Override
    public List<Node> parseProjectWithPath(String folderPath) throws IOException, ExecutionException, InterruptedException {
        List<Node> xmlNodes = new ArrayList<>();
        Path path = Paths.get(folderPath);
        List<Path> paths = FileHelper.listFiles(path);

        /**
         * Submit callable task to ThreadPool
         */
        List<Future<Node>> xmlNodeFutures = new ArrayList<>();
        paths.forEach(x -> {
            if(StringHelper.SUPPORTED_EXTENSIONS.contains(FileHelper.getFileExtension(x.toString()))){
                    Future<Node> future = null;
                    future = THREADPOOL_FIXED_SIZE.submit(new XmlFileParser(x.toString()));
                    xmlNodeFutures.add(future);
            }
        });

        for(Future<Node> future : xmlNodeFutures) {
            Node parsedNode = future.get();
            xmlNodes.add(parsedNode);
        }

        return xmlNodes;
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
    public List<Dependency> analyzeDependency(List<JavaNode> javaNode, List<Node> xmlNodes) throws ExecutionException, InterruptedException {
        List<Dependency> dependencies = new ArrayList<>();
        CompletableFuture dep1 = CompletableFuture.supplyAsync(() -> analyzeDependencyBetweenBeans(javaNode));
        CompletableFuture dep2 = CompletableFuture.supplyAsync(() -> analyzeDependencyFromBeanToView(javaNode, xmlNodes));
        CompletableFuture dep3 = CompletableFuture.supplyAsync(() -> analyzeDependencyFromControllerToView(javaNode, xmlNodes));
        dependencies.addAll((Collection<? extends Dependency>) dep1.get());
        dependencies.addAll((Collection<? extends Dependency>) dep2.get());
        dependencies.addAll((Collection<? extends Dependency>) dep3.get());
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
     * injected bean has pattern #{...}
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
                        logger.info("Bean: {} call injectedBean: {} with value {}", beanNode.getValue().getSimpleName(), injectionNode.getValue().getAbsolutePath(), beanName);
                        dependencies.add(new Dependency(
                                injectionNode.getValue().getId(),
                                beanNode.getValue().getId(),
                                new DependencyCountTable(0,0,0,0,0, 1)
                        ));
                    }
                } else {
                    /**
                     * analyze dependencies if bean has pattern #{abc}
                     */
                    String beanInjectionName = injectionNode.getBeanInjection();
                    String beanName = beanNode.getBeanName();
                    if(beanName.equals(beanInjectionName)) {
                        logger.info("Bean: {} call injectedBean: {} with value {}", beanNode.getValue().getSimpleName(), injectionNode.getValue().getAbsolutePath(), beanName);
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
                        logger.info("Bean: {} call injectedBean: {} with value {}", beanNode.getValue().getSimpleName(), injectionNode.getValue().getAbsolutePath(), beanName);
                        if(beanNode.getValue().getId() == null)
                            continue;
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
