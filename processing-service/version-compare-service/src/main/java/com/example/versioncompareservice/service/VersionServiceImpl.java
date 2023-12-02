package com.example.versioncompareservice.service;

import com.example.versioncompareservice.ast.dependency.Dependency;
import com.example.versioncompareservice.ast.node.JavaNode;
import com.example.versioncompareservice.ast.node.NodeWeight;
import com.example.versioncompareservice.ast.utility.Utility;
import com.example.versioncompareservice.dom.Node;
import com.example.versioncompareservice.model.Response;
import com.example.versioncompareservice.model.Version;
import com.example.versioncompareservice.service.analyzer.AddedNodeAnalyzer;
import com.example.versioncompareservice.service.analyzer.ChangedNodeAnalyzer;
import com.example.versioncompareservice.service.analyzer.DeletedNodeAnalyzer;
import com.example.versioncompareservice.utils.CompareUtils;
import com.example.versioncompareservice.utils.Getter;
import com.example.versioncompareservice.utils.JwtUtils;
import com.example.versioncompareservice.utils.Requester;
import com.example.versioncompareservice.utils.Utils;
import com.example.versioncompareservice.utils.Wrapper;
import com.netflix.discovery.shared.Pair;
import mrmathami.cia.java.JavaCiaException;
import mrmathami.cia.java.jdt.ProjectBuilder;
import mrmathami.cia.java.jdt.project.builder.parameter.BuildInputSources;
import mrmathami.cia.java.jdt.project.builder.parameter.JavaBuildParameter;
import mrmathami.cia.java.jdt.tree.node.AbstractNode;
import mrmathami.cia.java.project.JavaProjectSnapshot;
import mrmathami.cia.java.project.JavaProjectSnapshotComparison;
import mrmathami.cia.java.tree.dependency.JavaDependency;
import mrmathami.cia.java.tree.dependency.JavaDependencyWeightTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class VersionServiceImpl implements VersionService {

    @Autowired
    CompareUtils compareUtils;

    private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

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

        if (!userPath.equals("anonymous")) {
            userPath = jwtUtils.extractUsername(user);
        }

        //Save file
        String oldVersion = fileStorageService.storeFile(files.get(0), userPath, project);
        String newVersion = fileStorageService.storeFile(files.get(1), userPath, project);

        version.setOldVersion("./project/" + userPath + "/" + project + "/" + oldVersion + ".project");
        version.setNewVersion("./project/" + userPath + "/" + project + "/" + newVersion + ".project");

        return getCompare(version);
    }

    public Response getCompare(MultipartFile file, String user, String project, String oldPath) throws JavaCiaException, IOException {
        String userPath = user;
        Version version = new Version();

        if (!userPath.equals("anonymous")) {
            userPath = jwtUtils.extractUsername(user);
        }
        String newVersion = fileStorageService.storeFile(file, userPath, project);
        version.setNewVersion("./project/" + userPath + "/" + project + "/" + newVersion + ".project");
        version.setOldVersion(oldPath);

        return getCompare(version);
    }

    ;

    @Override
    public Response getCompare(Version files) throws JavaCiaException, IOException {

        //Old version path
        final Path inputPathA = Path.of(files.getOldVersion());
        final BuildInputSources inputSourcesA = new BuildInputSources(inputPathA);
        Utils.getFileList(inputSourcesA.createModule("core", inputPathA), inputPathA);

        //New version path
        final Path inputPathB = Path.of(files.getNewVersion());
        System.out.println(files.getNewVersion());
        final BuildInputSources inputSourcesB = new BuildInputSources(inputPathB);
        Utils.getFileList(inputSourcesB.createModule("core", inputPathB), inputPathB);

        //Compare two version
        final JavaProjectSnapshot projectSnapshotA = ProjectBuilder.createProjectSnapshot("JSON-java-before",
                DEPENDENCY_WEIGHT_TABLE, inputSourcesA, Set.of(new JavaBuildParameter(List.of(), false)));

        final JavaProjectSnapshot projectSnapshotB = ProjectBuilder.createProjectSnapshot("JSON-java-after",
                DEPENDENCY_WEIGHT_TABLE, inputSourcesB, Set.of(new JavaBuildParameter(List.of(), false)));

        JavaProjectSnapshotComparison snapshotComparison = ProjectBuilder.createProjectSnapshotComparison(
                "compare", projectSnapshotB, projectSnapshotA, DEPENDENCY_IMPACT_TABLE);

        //Node initialization
        int projectSize = snapshotComparison.getPreviousSnapshot().getRootNode().getAllNodes().size();
        List<JavaNode> changedNodes = new ArrayList<>();
        List<JavaNode> addedNodes = new ArrayList<>();
        List<JavaNode> deletedNodes = Utility.convertJavaNodeSet(snapshotComparison.getRemovedNodes(), "deleted", projectSize, files.getOldVersion());
        List<Pair<Integer, JavaNode>> removedNodes = new ArrayList<>();
        List<JavaNode> unchangedNodes = new ArrayList<>();
        String path = files.getNewVersion();

        JavaNode rootNode = new JavaNode((AbstractNode) projectSnapshotB.getRootNode(),
                true,
                path);

        //Bind to Tree Node
        Wrapper.applyCompare(rootNode, changedNodes, addedNodes, removedNodes, unchangedNodes, snapshotComparison, files);

        //Find impact
        List<Dependency> dependencies = Getter.getDependency(rootNode);
        List<JavaNode> javaNodes = Utility.convertToAllNodes((List<AbstractNode>) projectSnapshotB.getRootNode().getAllNodes());
        Set<NodeWeight> nodeWeights = Requester.getImpactedNodes(javaNodes, changedNodes, dependencies);
        nodeWeights.addAll(Requester.getImpactedNodes(javaNodes, addedNodes, dependencies));

        /**
         * Compare xml nodes
         */
        com.example.versioncompareservice.utils.communicator.Response oldXml = Requester.getXmlNodes(files.getOldVersion());
        com.example.versioncompareservice.utils.communicator.Response newXml = Requester.getXmlNodes(files.getNewVersion());

        Future<List<Node>> addedNodeFuture, deletedNodesFuture;
        Future<Set<Node>> changedNodesFuture, unchangedNodesFuture;
        addedNodeFuture = THREAD_POOL.submit(new AddedNodeAnalyzer(oldXml.getXmlNodes(), newXml.getXmlNodes()));
        deletedNodesFuture = THREAD_POOL.submit(new DeletedNodeAnalyzer(oldXml.getXmlNodes(), newXml.getXmlNodes()));
        changedNodesFuture = THREAD_POOL.submit(new ChangedNodeAnalyzer(oldXml.getXmlNodes(), newXml.getXmlNodes()));

        List xmlAddedNodes = new ArrayList<>();
        List xmlDeletedNodes = new ArrayList<>();
        Set xmlChangedNodes = new HashSet<>();

        try {
            xmlAddedNodes = addedNodeFuture.get();
            xmlDeletedNodes = deletedNodesFuture.get();
            xmlChangedNodes = changedNodesFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(path);
        com.example.versioncompareservice.model.Path filePath = new com.example.versioncompareservice.model.Path(path);
        Response response = new Response(changedNodes, deletedNodes, addedNodes, List.of(xmlChangedNodes), xmlDeletedNodes, xmlAddedNodes, dependencies, nodeWeights, rootNode);
        return response;
    }

    @Override
    public Response getCompare(List<MultipartFile> files) throws JavaCiaException, IOException {
        return null;
    }

}
