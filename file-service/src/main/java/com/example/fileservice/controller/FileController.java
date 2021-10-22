package com.example.fileservice.controller;

import com.example.fileservice.model.FileRequest;
import com.example.fileservice.model.FileResponse;
import com.example.fileservice.service.FileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class FileController {

    private final FileService fileService;


    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("api/file")
    public FileResponse getFileContent(@RequestBody FileRequest fileRequest) throws IOException {
        String content = fileService.readFile(fileRequest.getAddress());
        return new FileResponse(content);
    }
}
