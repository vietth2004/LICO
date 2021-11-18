package com.example.xmlservice.Service;

import com.example.xmlservice.Dom.Node;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface XmlService {
    List<Node> parseProjectWithPath(String path) throws IOException;
    List<Node> parseProjectWithFile(MultipartFile file) throws IOException;
}
