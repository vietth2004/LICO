package com.example.gitservice.utils;

import com.example.gitservice.ast.node.JavaNode;
import com.example.gitservice.ast.utility.Utility;
import mrmathami.annotations.Nonnull;
import mrmathami.cia.java.jdt.project.ProjectSnapshotComparison;
import mrmathami.cia.java.jdt.project.builder.parameter.BuildInputSources;
import mrmathami.cia.java.project.JavaSourceFileType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        for(JavaNode javaNode : changedNodes) {
            changeStatus(rootNode, javaNode, "changed");
        }

        for(JavaNode javaNode : addedNodes) {
            changeStatus(rootNode, javaNode, "added");
        }

        return rootNode;
    }

    private static void changeStatus(JavaNode javaNode, JavaNode statusNode, String status) {
        if(javaNode.getUniqueName().equals(statusNode.getUniqueName())) {
            logger.info(status);
            logger.info(javaNode.getUniqueName() + " " + javaNode.getId());
            logger.info(statusNode.getUniqueName() + " " + statusNode.getId());
            logger.info("\n");
            javaNode.setStatus(status);
        } else {
            for(Object childNode: javaNode.getChildren()) {
                if(childNode instanceof JavaNode) {
                    changeStatus((JavaNode) childNode, statusNode, status);
                }
            }
        }
    }

}
