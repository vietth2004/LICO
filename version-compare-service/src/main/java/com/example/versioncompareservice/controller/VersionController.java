package com.example.versioncompareservice.controller;

import com.example.versioncompareservice.model.Response;
import com.example.versioncompareservice.model.Version;
import com.example.versioncompareservice.service.FileStorageService;
import com.example.versioncompareservice.service.VersionService;
import mrmathami.cia.java.JavaCiaException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
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
    public Response versionCompareByFile(@RequestBody List<MultipartFile> files) throws JavaCiaException, IOException {
        return versionService.getCompare(files);
    }


    @PostMapping("/byPath")
    public Response versionCompareByPath(@RequestBody Version path) throws JavaCiaException, IOException {
        return versionService.getCompare(path);
    }


}
