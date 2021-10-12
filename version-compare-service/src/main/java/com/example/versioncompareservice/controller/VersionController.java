package com.example.versioncompareservice.controller;

import com.example.versioncompareservice.model.Response;
import com.example.versioncompareservice.model.Version;
import com.example.versioncompareservice.service.FileStorageService;
import com.example.versioncompareservice.service.VersionService;
import mrmathami.cia.java.JavaCiaException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class VersionController {

    private final VersionService versionService;

    private final FileStorageService fileStorageService;

    public VersionController(VersionService versionService, FileStorageService fileStorageService) {
        this.versionService = versionService;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("api/version-compare/byFile")
    public Response versionCompareByFile(@RequestBody MultipartFile[] files) {
        List<String> fileNames = new ArrayList<>();

        for(MultipartFile file : files) {
            String fileName = fileStorageService.storeFile(file);
            System.out.println(fileName);
            fileNames.add(fileName);
        }

        return versionService.getCompare(fileNames);
    }


    @PostMapping("api/version-compare/byPath")
    public Response versionCompareByPath(@RequestBody Version path) throws JavaCiaException, IOException {
        return versionService.getCompare(path);
    }

}
