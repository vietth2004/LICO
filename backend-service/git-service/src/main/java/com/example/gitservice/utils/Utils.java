package com.example.gitservice.utils;

import com.example.gitservice.ast.dependency.Dependency;
import com.example.gitservice.ast.dependency.Pair;
import com.example.gitservice.ast.node.JavaNode;
import com.example.gitservice.ast.utility.Utility;
import com.example.gitservice.dto.cia.CiaRequest;
import com.example.gitservice.dto.cia.CiaResponse;
import com.example.gitservice.dto.parser.Request;
import com.example.gitservice.dto.parser.Resource;
import com.example.gitservice.dto.parser.Response;
import mrmathami.annotations.Nonnull;
import mrmathami.cia.java.jdt.project.ProjectSnapshotComparison;
import mrmathami.cia.java.jdt.project.builder.parameter.BuildInputSources;
import mrmathami.cia.java.project.JavaSourceFileType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Utils {

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    private Utils() {
    }

    public static void getFileList(@Nonnull BuildInputSources.InputModule inputModule, @Nonnull Path dir)
            throws IOException {
        try (final DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (final Path path : stream) {
                if (path.toFile().isDirectory()) {
                    getFileList(inputModule, path);
                } else if (path.getFileName().toString().toLowerCase(Locale.ROOT).endsWith(".java")) {
                    inputModule.createFile(path, JavaSourceFileType.JAVA);
                }
            }
        }
    }

    public List<JavaNode> getAddedNodes(ProjectSnapshotComparison projectSnapshotComparison) {
        List<JavaNode> addedNodes = new ArrayList<>();

        addedNodes = Utility.convertJavaNodeSet(projectSnapshotComparison.getAddedNodes());

        return addedNodes;
    }

    public static JavaNode convertNode(JavaNode rootNode,
                                       List<JavaNode> changedNodes,
                                       List<JavaNode> addedNodes) {

        for (JavaNode javaNode : changedNodes) {
            changeStatus(rootNode, javaNode, "changed");
        }

        for (JavaNode javaNode : addedNodes) {
            changeStatus(rootNode, javaNode, "added");
        }

        return rootNode;
    }

    private static void changeStatus(JavaNode javaNode, JavaNode statusNode, String status) {
        if (javaNode.getUniqueName().equals(statusNode.getUniqueName())) {
            logger.info(status);
            logger.info(javaNode.getUniqueName() + " " + javaNode.getId());
            logger.info(statusNode.getUniqueName() + " " + statusNode.getId());
            logger.info("\n");
            javaNode.setStatus(status);
        } else {
            for (Object childNode : javaNode.getChildren()) {
                if (childNode instanceof JavaNode) {
                    changeStatus((JavaNode) childNode, statusNode, status);
                }
            }
        }
    }

    public static Response getResponse(List<String> parserList, Request request, String path) {
        JavaNode javaNode = request.getRootNode();
        List javaNodes = request.getAllNodes();

        List<Dependency> dependencies = request.getAllDependencies();

        for (String parser : parserList) {
            if (Resource.PARSER.contains(parser)) {
                dependencies = wrapDependency(dependencies, getDependencies(parser, javaNodes), "SPRING");
            }
        }

        wrapRootNode(javaNode, dependencies);

        List nodes = getNodesWeight(dependencies, javaNodes.size());

        return new Response(javaNode, javaNodes.size(), javaNodes, dependencies, path);
    }

    public static List getNodesWeight(List dependencies, Integer size) {

        RestTemplate restTemplate = new RestTemplate();

        CiaResponse ciaResponse = restTemplate.postForObject(
                "http://localhost:6001/api/cia-service/calculate", //cia-service
                new CiaRequest(dependencies, size),
                CiaResponse.class);

        return ciaResponse.getNodes();
    }

    public static List<Dependency> wrapDependency(List<Dependency> dependencies, List<Dependency> frameworkDependencies, String type) {

        for (Dependency dependency : frameworkDependencies) {
            for (Dependency base : dependencies) {
                if (isDependency(base, dependency)) {
                    if (type.equals("SPRING")) {
                        base.getType().setSPRING(dependency.getType().getSPRING());
                    }
                }
            }
        }

        return dependencies;
    }

    public static Boolean isDependency(Dependency base, Dependency dependency) {
        if (base.getCalleeNode().equals(dependency.getCalleeNode())
                && base.getCallerNode().equals(dependency.getCallerNode())) {
            return true;
        }
        return false;
    }

    public static List getDependencies(String parser, List javaNodes) {
        RestTemplate restTemplate = new RestTemplate();
        List dependencies = new ArrayList();
        if (parser.equals("spring-parser")) {

            Request springs = restTemplate.postForObject(
                    "http://localhost:7003/api/spring-service/dependency/spring", //spring-service
                    javaNodes,
                    Request.class);

            dependencies.addAll(springs.getAllDependencies());
        }
        return dependencies;
    }

    private static void wrapRootNode(JavaNode javaNode, List<Dependency> dependencies) {
        javaNode.setDependencyTo(getDependencyTo(javaNode, javaNode.getDependencyTo(), dependencies));
        javaNode.setDependencyFrom(getDependencyFrom(javaNode, javaNode.getDependencyFrom(), dependencies));

        for (Object childNode : javaNode.getChildren()) {
            if (childNode instanceof JavaNode) {
                wrapRootNode((JavaNode) childNode, dependencies);
            }
        }
    }

    public static List<Pair> getDependencyTo(JavaNode javaNode, List<Pair> nodeDependency, List<Dependency> dependencies) {
        List<Pair> dependenciesTempList = new ArrayList<>();
        for (Pair pair : nodeDependency) {
            for (Dependency dependency : dependencies) {
                if (javaNode.getId().equals(dependency.getCallerNode())
                        && pair.getNode().getId().equals(dependency.getCalleeNode())) {
                    pair.setDependency(dependency.getType());
                }
            }
            dependenciesTempList.add(pair);
        }
        return dependenciesTempList;
    }

    public static List<Pair> getDependencyFrom(JavaNode javaNode, List<Pair> nodeDependency, List<Dependency> dependencies) {
        List<Pair> dependenciesTempList = new ArrayList<>();
        for (Pair pair : nodeDependency) {
            for (Dependency dependency : dependencies) {
                if (javaNode.getId().equals(dependency.getCalleeNode())
                        && pair.getNode().getId().equals(dependency.getCallerNode())) {
                    pair.setDependency(dependency.getType());
                }
            }
            dependenciesTempList.add(pair);
        }
        return dependenciesTempList;
    }

}
