package com.example.fileservice.controller;

import com.example.fileservice.model.FileRequest;
import com.example.fileservice.model.FileResponse;
import com.example.fileservice.service.FileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/file-service/")
public class FileController {

    private final FileService fileService;


    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/file")
    public FileResponse getFileContent(@RequestBody FileRequest fileRequest) throws IOException {
//        System.out.println(fileRequest.getAddress());
        return fileService.readFile(fileRequest.getAddress());
    }

    @GetMapping("/current-directory")
    public String getCurrentDirectory() {
        return Paths.get(".").toAbsolutePath().normalize().toString();
    }
}
