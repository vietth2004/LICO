package com.example.versioncompareservice.service;

import com.example.versioncompareservice.ast.node.JavaNode;
import com.example.versioncompareservice.ast.utility.Utility;
import com.example.versioncompareservice.model.Response;
import com.example.versioncompareservice.model.Version;
import com.example.versioncompareservice.utils.JwtUtils;
import com.example.versioncompareservice.utils.Utils;
import com.netflix.discovery.shared.Pair;
import mrmathami.cia.java.JavaCiaException;
import mrmathami.cia.java.jdt.ProjectBuilder;
import mrmathami.cia.java.jdt.project.builder.parameter.BuildInputSources;
import mrmathami.cia.java.jdt.project.builder.parameter.JavaBuildParameter;
import mrmathami.cia.java.jdt.project.tree.node.AbstractNode;
import mrmathami.cia.java.project.JavaProjectSnapshot;
import mrmathami.cia.java.project.JavaProjectSnapshotComparison;
import mrmathami.cia.java.tree.dependency.JavaDependency;
import mrmathami.cia.java.tree.dependency.JavaDependencyWeightTable;
import mrmathami.cia.java.tree.node.JavaMethodNode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class VersionServiceImpl implements VersionService{

    final FileStorageService fileStorageService;

    final JwtUtils jwtUtils;

    public static final JavaDependencyWeightTable DEPENDENCY_WEIGHT_TABLE = JavaDependencyWeightTable.of(Map.of(
            JavaDependency.USE, 1.0,
            JavaDependency.MEMBER, 1.0,
            JavaDependency.INHERITANCE, 4.0,
            JavaDependency.INVOCATION, 4.0,
            JavaDependency.OVERRIDE, 1.0
    ));
    public static final JavaDependencyWeightTable DEPENDENCY_IMPACT_TABLE = JavaDependencyWeightTable.of(Map.of(
            JavaDependency.USE, 0.4,
            JavaDependency.MEMBER, 0.2,
            JavaDependency.INHERITANCE, 0.3,
            JavaDependency.INVOCATION, 0.3,
            JavaDependency.OVERRIDE, 0.3
    ));

    public VersionServiceImpl(FileStorageService fileStorageService, JwtUtils jwtUtils) {
        this.fileStorageService = fileStorageService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Response getCompare(List<MultipartFile> files, String user, String project) throws JavaCiaException, IOException {
        //Create Version
        Version version = new Version();
        String userPath = user;

        if(!userPath.equals("anonymous")){
            userPath = jwtUtils.extractUsername(user);
        }

        //Save file
        String oldVersion = fileStorageService.storeFile(files.get(0), userPath, project);
        String newVersion = fileStorageService.storeFile(files.get(1), userPath, project);

        version.setOldVersion("./project/" + userPath + "/" + project + "/" + oldVersion + ".project");
        version.setNewVersion("./project/" + userPath + "/" + project + "/" + newVersion + ".project");

        return getCompare(version);
    }

    @Override
    public Response getCompare(Version files) throws JavaCiaException, IOException {

        //Old version path
        final Path inputPathA = Path.of(files.getOldVersion());
        final BuildInputSources inputSourcesA = new BuildInputSources(inputPathA);
        Utils.getFileList(inputSourcesA.createModule("core", inputPathA), inputPathA);

        //New version path
        final Path inputPathB = Path.of(files.getNewVersion());
        final BuildInputSources inputSourcesB = new BuildInputSources(inputPathB);
        Utils.getFileList(inputSourcesB.createModule("core", inputPathB), inputPathB);

        //Compare two version
        final JavaProjectSnapshot projectSnapshotA = ProjectBuilder.createProjectSnapshot("JSON-java-before",
                DEPENDENCY_WEIGHT_TABLE, inputSourcesA, Set.of(new JavaBuildParameter(List.of(), true)));

        final JavaProjectSnapshot projectSnapshotB = ProjectBuilder.createProjectSnapshot("JSON-java-after",
                DEPENDENCY_WEIGHT_TABLE, inputSourcesB, Set.of(new JavaBuildParameter(List.of(), true)));

        JavaProjectSnapshotComparison snapshotComparison = ProjectBuilder.createProjectSnapshotComparison(
                "compare", projectSnapshotB, projectSnapshotA, DEPENDENCY_IMPACT_TABLE);

        //Node initialization
        List<JavaNode> changedNodes = new ArrayList<>();
        List<JavaNode> addedNodes = new ArrayList<>();
        List<JavaNode> deletedNodes = Utility.convertJavaNodeSet(snapshotComparison.getRemovedNodes(), "removed");
        List<Pair<Integer, JavaNode>> removedNodes = new ArrayList<>();
        List<JavaNode> unchangedNodes = new ArrayList<>();
        JavaNode rootNode = new JavaNode((AbstractNode) projectSnapshotB.getRootNode(), true);

        //Bind to Tree Node
        applyCompare(rootNode, changedNodes, addedNodes, removedNodes, unchangedNodes, snapshotComparison);

        return new Response(changedNodes, deletedNodes, addedNodes, rootNode);
    }

    @Override
    public Response getCompare(List<MultipartFile> files) throws JavaCiaException, IOException {
        return null;
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
                String oldNode = ((JavaMethodNode) javaNode.getA()).getReturnType().getDescription();
                String newNode = ((JavaMethodNode) javaNode.getB()).getReturnType().getDescription();
//
//                System.out.println();
//
//                System.out.println(oldNode.toString());
//                System.out.println(newNode.toString());

                if(!oldNode.equals(newNode)) {
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
//            System.out.println(javaNode.getUniqueName());
//            System.out.println("Parent: " + javaNode.getParent().getUniqueName() + ": " + javaNode.getParent().getId());
//            System.out.println();
            deletedNodes.add(new Pair<>(javaNode.getParent().getId(), new JavaNode(javaNode, "deleted")));
        }

        //bind changed nodes and added nodes to nodes tree
        Utils.convertNode(rootNode, changedNodes, addedNodes);

        //bind deleted nodes to nodes tree
        bindRemovedNode(rootNode, deletedNodes, changedNodesBind, projectSize);
    }


    //Node binding
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
