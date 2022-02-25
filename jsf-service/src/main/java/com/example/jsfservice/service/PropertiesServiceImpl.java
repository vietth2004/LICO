package com.example.jsfservice.service;

import com.example.jsfservice.ast.dependency.Dependency;
import com.example.jsfservice.ast.dependency.DependencyCountTable;
import com.example.jsfservice.dom.Bean.PropsBeanNode;
import com.example.jsfservice.dom.Bean.XmlBeanInjectionNode;
import com.example.jsfservice.dom.Node;
import com.example.jsfservice.dom.Properties.PropertiesFileNode;
import com.example.jsfservice.dom.Properties.PropertiesNode;
import com.example.jsfservice.parser.PropertiesFileParser;
import com.example.jsfservice.utils.Helper.FileHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static com.example.jsfservice.utils.NodeUtils.*;

@Service
public class PropertiesServiceImpl implements PropertiesService{

    private final Logger logger = LoggerFactory.getLogger(PropertiesServiceImpl.class);

    @Override
    public List<PropertiesFileNode> parseProjectWithPath(String folderPath) throws IOException, ExecutionException, InterruptedException {
        List<PropertiesFileNode> propsFileNodes = new ArrayList<>();
        final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        Path path = Paths.get(folderPath);
        List<Path> paths = FileHelper.listFiles(path);

        /**
         * Submit callable task to ThreadPool
         */
        List<Future<PropertiesFileNode>> propNodeFuture = new ArrayList<>();
        paths.forEach(x -> {
            if(FileHelper.getFileExtension(x.toString()).equals("properties")){
                Future<PropertiesFileNode> future = null;
                future = THREAD_POOL.submit(new PropertiesFileParser(x.toString()));
                propNodeFuture.add(future);
            }
        });

        for(Future<PropertiesFileNode> future : propNodeFuture) {
            PropertiesFileNode parsedNode = future.get();
            propsFileNodes.add(parsedNode);
        }

        return propsFileNodes;
    }

    @Override
    public List<Dependency> analyzeDependencies(List<Node> xmlNodes, List<PropertiesFileNode> propertiesFileNodes) {

        List<Dependency> dependencies = new ArrayList<>();
        List<PropsBeanNode> beanNodes = new ArrayList<>();

        List<PropertiesNode> sortedPropertiesNode = propertiesFileNodes
                .stream()
                .flatMap(e -> e.getProperties().stream())
                .collect(Collectors.toList())
                .stream()
                .sorted(Comparator.comparing(node -> node.getId()))
                .collect(Collectors.toList());

        /**
         * Get all bean nodes
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

        /**
         * Get all first children from faces-config file
         * unify all nodes to XmlTagNode
         */
        for(Node node : getChildrenLevel1XmlFileNode(faceConfig)) {
            beanNodes.addAll(filterPropBeanFromFacesConfig(node, propertiesFileNodes));
        }

        for(XmlBeanInjectionNode injectionNode : injectionNodes) {
            for(PropsBeanNode beanNode : beanNodes) {
                /**
                 * analyze dependencies if custom bean config
                 */
                if(injectionNode.getBeanInjection().contains("[")) {
                    String beanInjectionName = injectionNode.getBeanInjection().split("\\[")[0];
                    String injectedValue = injectionNode.getBeanInjection().split("\\[")[1].replace("]", "");
                    String beanName = beanNode.getBeanName();
                    for(PropertiesNode prop : beanNode.getValue().getProperties()) {
                        if(prop.getName().equals(injectedValue) && beanName.equals(beanInjectionName)) {
                        logger.info("Bean: {} call injectedBean: {} with value {}", beanNode.getBeanName(), injectionNode.getBeanInjection(), beanName);
                        dependencies.add(new Dependency(
                                prop.getId(),
                                beanNode.getValue().getId(),
                                new DependencyCountTable(0,0,0,0,0, 1)
                        ));
                        }
                    }
//                    if(beanName.equals(beanInjectionName)) {
//                        logger.info("Bean: {} call injectedBean: {} with value {}", beanNode.getValue().getName(), injectionNode.getValue().getAbsolutePath(), beanName);
//                        dependencies.add(new Dependency(
//                                injectionNode.getValue().getId(),
//                                beanNode.getValue().getId(),
//                                new DependencyCountTable(0,0,0,0,0, 1)
//                        ));
//                    }
                }
            }
        }

        return dependencies;
    }
}
