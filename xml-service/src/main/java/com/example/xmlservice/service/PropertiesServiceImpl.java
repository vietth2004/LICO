package com.example.xmlservice.service;

import com.example.xmlservice.ast.dependency.Dependency;
import com.example.xmlservice.ast.dependency.DependencyCountTable;
import com.example.xmlservice.dom.Bean.PropsBeanNode;
import com.example.xmlservice.dom.Bean.XmlBeanInjectionNode;
import com.example.xmlservice.dom.Node;
import com.example.xmlservice.dom.Properties.PropertiesFileNode;
import com.example.xmlservice.parser.PropertiesFileParser;
import com.example.xmlservice.utils.Helper.FileHelper;
import com.example.xmlservice.utils.Log.ClientLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static com.example.xmlservice.utils.NodeUtils.*;

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
                    String beanName = beanNode.getBeanName();
                    if(beanName.equals(beanInjectionName)) {
                        logger.info("Bean: {} call injectedBean: {} with value {}", beanNode.getValue().getName(), injectionNode.getValue().getAbsolutePath(), beanName);
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
}
