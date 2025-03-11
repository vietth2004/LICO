package com.example.versioncompareservice.controller;

import com.example.versioncompareservice.model.Response;
import com.example.versioncompareservice.model.Version;
import com.example.versioncompareservice.service.FileStorageService;
import com.example.versioncompareservice.service.VersionService;
import mrmathami.cia.java.JavaCiaException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/version-compare-service/")
public class VersionController {

    private final VersionService versionService;

    private final FileStorageService fileStorageService;

    public VersionController(VersionService versionService, FileStorageService fileStorageService) {
        this.versionService = versionService;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/byFile")
    public Response versionCompareByFile(@RequestBody List<MultipartFile> files,
                                         @RequestParam(name="project", required = false, defaultValue = "tmp-prj") String project,
                                         @RequestParam(name="user", required = false, defaultValue = "anonymous") String user)
            throws JavaCiaException, IOException {
        System.out.println(project);
        long startRunTestTime = System.nanoTime();

        Response response = versionService.getCompare(files, user, project);

        long endRunTestTime = System.nanoTime();
        double runTestDuration = (endRunTestTime - startRunTestTime) / 1000000.0;
        System.out.println(runTestDuration);
        return response;
    }

    @PostMapping("/byPath")
    public Response versionCompareByPath(@RequestBody Version path,
                                         @RequestParam(name="user", required = false, defaultValue = "anonymous") String user)
            throws JavaCiaException, IOException {
        System.out.println(path.getNewVersion());
        System.out.println(path.getOldVersion());
        return versionService.getCompare(path);
    }

    @PostMapping("/newVersion")
    public Response newVersionCompare(@RequestBody MultipartFile file,
                                      @RequestParam(name="user", required = false, defaultValue = "anonymous") String user,
                                      @RequestParam(name="project", required = false, defaultValue = "tmp-prj") String project,
                                      @RequestParam(name="oldPath") String oldPath) throws JavaCiaException, IOException {
        System.out.println(project);
        return versionService.getCompare(file, user, project, oldPath);
    }

    @PostMapping("/hello")
    public String versionCompareByPath() throws JavaCiaException, IOException {
        return "hello";
    }



}
