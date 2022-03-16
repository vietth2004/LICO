package com.example.gitservice.service;

import com.example.gitservice.ast.node.JavaNode;
import com.example.gitservice.ast.utility.Utility;
import com.example.gitservice.payload.versioncompare.Response;
import com.example.gitservice.payload.versioncompare.Version;
import com.example.gitservice.utils.Utils;
import com.netflix.discovery.shared.Pair;
import mrmathami.cia.java.JavaCiaException;
import mrmathami.cia.java.jdt.ProjectBuilder;
import mrmathami.cia.java.jdt.project.builder.parameter.BuildInputSources;
import mrmathami.cia.java.jdt.project.builder.parameter.JavaBuildParameter;
import mrmathami.cia.java.jdt.project.tree.node.AbstractNode;
import mrmathami.cia.java.project.JavaProjectSnapshot;
import mrmathami.cia.java.project.JavaProjectSnapshotComparison;
import mrmathami.cia.java.tree.node.JavaMethodNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.example.gitservice.constant.DependencyCountTableConstant.DEPENDENCY_IMPACT_TABLE;
import static com.example.gitservice.constant.DependencyCountTableConstant.DEPENDENCY_WEIGHT_TABLE;

@Service
public class VersionCompare {

    private final Logger logger = LoggerFactory.getLogger(VersionCompare.class);

    public Response compare(Version version) throws IOException, JavaCiaException {
        final Path inputPathA = Path.of(version.getOldVersion());
        final BuildInputSources inputSourcesA = new BuildInputSources(inputPathA);
        Utils.getFileList(inputSourcesA.createModule("core", inputPathA), inputPathA);


        final Path inputPathB = Path.of(version.getNewVersion());
        final BuildInputSources inputSourcesB = new BuildInputSources(inputPathB);
        Utils.getFileList(inputSourcesB.createModule("core", inputPathB), inputPathB);

        logger.info(inputSourcesA.getPath().toString());
        logger.info(inputSourcesB.getPath().toString());
        logger.info("\n");

        final JavaProjectSnapshot projectSnapshotA = ProjectBuilder.createProjectSnapshot("JSON-java-before",
                DEPENDENCY_WEIGHT_TABLE, inputSourcesA, Set.of(new JavaBuildParameter(List.of(), true)));

        final JavaProjectSnapshot projectSnapshotB = ProjectBuilder.createProjectSnapshot("JSON-java-after",
                DEPENDENCY_WEIGHT_TABLE, inputSourcesB, Set.of(new JavaBuildParameter(List.of(), true)));

        JavaProjectSnapshotComparison snapshotComparison = ProjectBuilder.createProjectSnapshotComparison(
                "compare", projectSnapshotB, projectSnapshotA, DEPENDENCY_IMPACT_TABLE);

//        List<JavaNode> changedNodes = Utility.convertJavaNodePairSet(snapshotComparison.getChangedNodes(), "changed");
//        List<JavaNode> addedNodes = Utility.convertJavaNodeSet(snapshotComparison.getAddedNodes(), "added");
//        List<JavaNode> deletedNodes = Utility.convertJavaNodeSet(snapshotComparison.getRemovedNodes(), "deleted");
//        List<JavaNode> unchangedNodes = Utility.convertJavaNodePairSet(snapshotComparison.getUnchangedNodes(), "unchanged");

        List<JavaNode> changedNodes = new ArrayList<>();
        List<JavaNode> addedNodes = new ArrayList<>();
        List<JavaNode> deletedNodes = Utility.convertJavaNodeSet(snapshotComparison.getRemovedNodes(), "removed");
        List<Pair<Integer, JavaNode>> removedNodes = new ArrayList<>();
        List<JavaNode> unchangedNodes = new ArrayList<>();

        JavaNode rootNode = new JavaNode((AbstractNode) projectSnapshotB.getRootNode(), true);

        applyCompare(rootNode, changedNodes, addedNodes, removedNodes, unchangedNodes, snapshotComparison);

//        rootNode = Utils.convertNode(rootNode, changedNodes, addedNodes);

        return new Response(changedNodes, deletedNodes, addedNodes, rootNode);
    }

    public void applyCompare(JavaNode rootNode,
                             List<JavaNode> changedNodes,
                             List<JavaNode> addedNodes,
                             List<Pair<Integer, JavaNode>> deletedNodes,
                             List<JavaNode> unchangedNodes,
                             JavaProjectSnapshotComparison snapshotComparison) {
        int projectSize = snapshotComparison.getPreviousSnapshot().getRootNode().getAllNodes().size();
        List<Pair<Integer, Integer>> changedNodesBind = new ArrayList<>();

        //add unchanged nodes
        for(mrmathami.utils.Pair<mrmathami.cia.java.tree.node.JavaNode, mrmathami.cia.java.tree.node.JavaNode> javaNode
                : snapshotComparison.getUnchangedNodes()) {
            if(javaNode.getA() instanceof JavaMethodNode && javaNode.getB() instanceof JavaMethodNode) {
                if(!((JavaMethodNode) javaNode.getA()).getReturnType().equals(((JavaMethodNode) javaNode.getB()).getReturnType())) {
                    changedNodes.add(new JavaNode(javaNode.getA(), "changed"));
                }
            } else {
                unchangedNodes.add(new JavaNode(javaNode.getA(), "unchanged"));
            }
        }

        //add changed nodes
        for(mrmathami.utils.Pair<mrmathami.cia.java.tree.node.JavaNode, mrmathami.cia.java.tree.node.JavaNode> javaNode
                : snapshotComparison.getChangedNodes()) {
            changedNodes.add(new JavaNode(javaNode.getA(), "changed"));
            changedNodesBind.add(new Pair<>(javaNode.getA().getId(), javaNode.getB().getId()));
        }

        //add added nodes
        for(mrmathami.cia.java.tree.node.JavaNode javaNode : snapshotComparison.getAddedNodes()) {
            addedNodes.add(new JavaNode(javaNode, "added"));
        }

        //add removed nodes
        for(mrmathami.cia.java.tree.node.JavaNode javaNode : snapshotComparison.getRemovedNodes()) {
            logger.info(javaNode.getUniqueName());
            logger.info("Parent: " + javaNode.getParent().getUniqueName() + ": " + javaNode.getParent().getId());
            logger.info("\n");
            deletedNodes.add(new Pair<>(javaNode.getParent().getId(), new JavaNode(javaNode, "deleted")));
        }

        //bind changed nodes and added nodes to nodes tree
        Utils.convertNode(rootNode, changedNodes, addedNodes);

        //bind deleted nodes to nodes tree
        bindRemovedNode(rootNode, deletedNodes, changedNodesBind, projectSize);
    }

    private void bindRemovedNode(JavaNode rootNode,
                                 List<Pair<Integer, JavaNode>> deletedNodes,
                                 List<Pair<Integer, Integer>> changedNodesBind,
                                 int size) {
        int bindId = size;
        for (Pair<Integer, JavaNode> node : deletedNodes) {
            int parentId = node.first();
            int newParentId;
            for(Pair<Integer, Integer> bind : changedNodesBind) {
                if(parentId == bind.second()) {
                    newParentId = bind.first();
                    addNode(rootNode, node.second(), newParentId, bindId);
                }
            }
        }
    }

    private void addNode(JavaNode rootNode, JavaNode addedNode, int parentId, int bindId) {
        if(rootNode.getId() == parentId) {
            bindId(addedNode, bindId);
            rootNode.addChildren(addedNode);
        } else {
            for (Object javaNode : rootNode.getChildren()) {
                if (javaNode instanceof JavaNode) {
                    addNode((JavaNode) javaNode, addedNode, parentId, bindId);
                }
            }

        }
    }

    private void bindId(JavaNode addedNode, int bindId) {
        addedNode.setId(bindId + addedNode.getId());
        addedNode.setStatus("deleted");

        for(Object childNode : addedNode.getChildren()) {
            if(childNode instanceof JavaNode) {
                bindId((JavaNode) childNode, bindId);
            }
        }
    }

}
