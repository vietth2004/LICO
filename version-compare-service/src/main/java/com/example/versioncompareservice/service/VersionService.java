package com.example.versioncompareservice.service;

import com.example.versioncompareservice.model.Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VersionService {

    Response getCompare (MultipartFile[] files);

    Response getCompare (List<String> files);
}
