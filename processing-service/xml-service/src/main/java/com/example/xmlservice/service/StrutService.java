package com.example.xmlservice.service;

import com.example.xmlservice.dom.Node;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface StrutService {
    List<Node> parseProjectWithPath(String path) throws IOException, ExecutionException, InterruptedException;
    List<Node> parseProjectWithFile(MultipartFile file) throws IOException;
}
