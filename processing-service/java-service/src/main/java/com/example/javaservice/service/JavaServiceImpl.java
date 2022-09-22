package com.example.javaservice.service;

import com.example.javaservice.ast.utility.Utility;
import com.example.javaservice.utility.Utils;
import mrmathami.cia.java.JavaCiaException;
import mrmathami.cia.java.jdt.ProjectBuilder;
import mrmathami.cia.java.jdt.project.builder.parameter.BuildInputSources;
import mrmathami.cia.java.jdt.project.builder.parameter.JavaBuildParameter;
import mrmathami.cia.java.project.JavaProjectSnapshot;
import mrmathami.cia.java.tree.dependency.JavaDependency;
import mrmathami.cia.java.tree.dependency.JavaDependencyWeightTable;
import mrmathami.cia.java.tree.node.JavaRootNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class JavaServiceImpl implements JavaService{

    @Autowired
    private ProjectService projectService;

    public JavaServiceImpl() {
    }

    public static final JavaDependencyWeightTable DEPENDENCY_WEIGHT_TABLE = JavaDependencyWeightTable.of(Map.of(
            JavaDependency.USE, 1.0,
            JavaDependency.MEMBER, 1.0,
            JavaDependency.INHERITANCE, 4.0,
            JavaDependency.INVOCATION, 4.0,
            JavaDependency.OVERRIDE, 1.0
    ));

    public JavaRootNode parseProject(String path) throws JavaCiaException, IOException {
        final Path inputPath = Path.of(path);
        final BuildInputSources inputSources = new BuildInputSources(inputPath);
        Utils.getFileList(inputSources.createModule("core", inputPath), inputPath);
        final JavaProjectSnapshot projectSnapshot = ProjectBuilder.createProjectSnapshot("before",
                DEPENDENCY_WEIGHT_TABLE, inputSources, Set.of(new JavaBuildParameter(List.of(), true)));
        JavaRootNode javaRootNode = projectSnapshot.getRootNode();
        return javaRootNode;
    }

    public JavaRootNode parseProjectWithFile(MultipartFile file) throws JavaCiaException, IOException {
        String fileName = projectService.storeFile(file);
        String path = "./project/" + "anonymous/" + fileName + ".project";
        return parseProject(path);
    }

}
