package com.example.versioncompareservice.service;

import com.example.versioncompareservice.ast.node.JavaNode;
import com.example.versioncompareservice.ast.utility.Utility;
import com.example.versioncompareservice.model.Response;
import com.example.versioncompareservice.model.Version;
import com.example.versioncompareservice.utils.Utils;
import mrmathami.cia.java.JavaCiaException;
import mrmathami.cia.java.jdt.ProjectBuilder;
import mrmathami.cia.java.jdt.project.builder.parameter.BuildInputSources;
import mrmathami.cia.java.jdt.project.builder.parameter.JavaBuildParameter;
import mrmathami.cia.java.jdt.tree.node.AbstractNode;
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

    public VersionServiceImpl(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @Override
    public Response getCompare(MultipartFile[] files) {
        return new Response();
    }

    @Override
    public Response getCompare(List<MultipartFile> files) throws JavaCiaException, IOException {
        Version version = new Version();

//        for (MultipartFile file : files) {
//            if(file.getOriginalFilename().contains("new")) {
//                String fileName = fileStorageService.storeFile(file);
//                version.setNewVersion("./project/anonymous/compare/" + fileName + "-project");
//            } else {
//                String fileName = fileStorageService.storeFile(file);
//                version.setOldVersion("./project/anonymous/compare/" + fileName + "-project");
//            }
//        }

        String oldVersion = fileStorageService.storeFile(files.get(0));
        String newVersion = fileStorageService.storeFile(files.get(1));
        version.setOldVersion("./project/anonymous/compare/" + oldVersion + "-project");
        version.setNewVersion("./project/anonymous/compare/" + newVersion + "-project");

        return getCompare(version);
    }

    @Override
    public Response getCompare(Version files) throws JavaCiaException, IOException {

        final Path inputPathA = Path.of(files.getOldVersion());
        final BuildInputSources inputSourcesA = new BuildInputSources(inputPathA);
        Utils.getFileList(inputSourcesA.createModule("core", inputPathA), inputPathA);


        final Path inputPathB = Path.of(files.getNewVersion());
        final BuildInputSources inputSourcesB = new BuildInputSources(inputPathB);
        Utils.getFileList(inputSourcesB.createModule("core", inputPathB), inputPathB);

        System.out.println(inputSourcesA.getPath().toString());
        System.out.println(inputSourcesB.getPath().toString());
        System.out.println();

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
        List<JavaNode> deletedNodes = new ArrayList<>();
        List<JavaNode> unchangedNodes = new ArrayList<>();

        applyCompare(changedNodes, addedNodes, deletedNodes, unchangedNodes, snapshotComparison);

        JavaNode rootNode = new JavaNode((AbstractNode) projectSnapshotB.getRootNode(), true);

        rootNode = Utils.convertNode(rootNode, changedNodes, addedNodes, deletedNodes, unchangedNodes);

        return new Response(changedNodes, deletedNodes, addedNodes, rootNode);
    }

    @Override
    public JavaNode GetCompareRootNode(Version files) throws JavaCiaException, IOException {

//        final Path inputPathA = Path.of(files.getOldVersion());
//        final BuildInputSources inputSourcesA = new BuildInputSources(inputPathA);
//        Utils.getFileList(inputSourcesA.createModule("core", inputPathA), inputPathA);
//
//        final Path inputPathB = Path.of(files.getNewVersion());
//        final BuildInputSources inputSourcesB = new BuildInputSources(inputPathB);
//        Utils.getFileList(inputSourcesB.createModule("core", inputPathB), inputPathB);
//
//        final JavaProjectSnapshot projectSnapshotA = ProjectBuilder.createProjectSnapshot("JSON-java-before",
//                DEPENDENCY_WEIGHT_TABLE, inputSourcesA, Set.of(new JavaBuildParameter(List.of(), true)));
//
//        final JavaProjectSnapshot projectSnapshotB = ProjectBuilder.createProjectSnapshot("JSON-java-after",
//                DEPENDENCY_WEIGHT_TABLE, inputSourcesB, Set.of(new JavaBuildParameter(List.of(), true)));
//
//        JavaProjectSnapshotComparison snapshotComparison = ProjectBuilder.createProjectSnapshotComparison(
//                "compare", projectSnapshotB, projectSnapshotA, DEPENDENCY_IMPACT_TABLE);
//
//        List<JavaNode> changedNodes = Utility.convertJavaNodePairSet(snapshotComparison.getChangedNodes());
//        List<JavaNode> addedNodes = Utility.convertJavaNodeSet(snapshotComparison.getAddedNodes());
//        List<JavaNode> deletedNodes = Utility.convertJavaNodeSet(snapshotComparison.getRemovedNodes());
//
//        JavaNode rootNode = new JavaNode(snapshotComparison.getCurrentSnapshot().getRootNode());
//        return rootNode;

        return new JavaNode();
    }

    public void applyCompare(List<JavaNode> changedNodes,
                             List<JavaNode> addedNodes,
                             List<JavaNode> deletedNodes,
                             List<JavaNode> unchangedNodes,
                             JavaProjectSnapshotComparison snapshotComparison) {
        for(mrmathami.utils.Pair<mrmathami.cia.java.tree.node.JavaNode, mrmathami.cia.java.tree.node.JavaNode> javaNode
                : snapshotComparison.getUnchangedNodes()) {
            if(javaNode.getA() instanceof JavaMethodNode && javaNode.getB() instanceof JavaMethodNode) {
                if(((JavaMethodNode) javaNode.getA()).getReturnType() != ((JavaMethodNode) javaNode.getB()).getReturnType()) {
                    changedNodes.add(new JavaNode(javaNode.getA(), "changed"));
                }
            } else {
                unchangedNodes.add(new JavaNode(javaNode.getA(), "unchanged"));
            }
        }

        for(mrmathami.utils.Pair<mrmathami.cia.java.tree.node.JavaNode, mrmathami.cia.java.tree.node.JavaNode> javaNode
                : snapshotComparison.getChangedNodes()) {
            changedNodes.add(new JavaNode(javaNode.getA(), "changed"));
        }


        for(mrmathami.cia.java.tree.node.JavaNode javaNode : snapshotComparison.getAddedNodes()) {
            addedNodes.add(new JavaNode(javaNode, "added"));
        }

        for(mrmathami.cia.java.tree.node.JavaNode javaNode : snapshotComparison.getRemovedNodes()) {
            deletedNodes.add(new JavaNode(javaNode, "deleted"));
        }
    }

}
