package com.example.versioncompareservice.service;

import com.example.versioncompareservice.model.Response;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class VersionServiceImpl implements VersionService{

    @Override
    public Response getCompare(MultipartFile[] files) {
        return new Response();
    }

    @Override
    public Response getCompare(List<String> files) {
        return new Response();
    }

}
