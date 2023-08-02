package com.example.unittesting.Sevice;

import com.example.unittesting.Sevice.project.ProjectService;
import com.example.unittesting.model.Response;
import com.example.unittesting.util.JwtUtils;
import com.example.unittesting.util.worker.Getter;
import com.example.unittesting.util.worker.Writer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service

public class UTestServiceImpl implements UTestService {
    final ProjectService projectService;

    final JwtUtils jwtUtils;

    public UTestServiceImpl(ProjectService projectService, JwtUtils jwtUtils) {
        this.projectService = projectService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public ResponseEntity<Object> build(String path) throws IOException {
        Response response = new Response();
        response = Getter.getResponse(path);
        Writer.write(path, response, "tmp-prjt");
        return new ResponseEntity<>(HttpStatus.OK);
    }
    public String buildProject(List<String> parser, MultipartFile file, String user, String project) throws IOException{
        String userPath = user;
        if (!userPath.equals("anonymous")) {
            userPath = jwtUtils.extractUsername(user);
        }
        long storeStartTime = System.currentTimeMillis();
        String fileName = projectService.storeFile(file, userPath, project);
        return fileName;
    }
}