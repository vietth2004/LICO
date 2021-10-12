package com.example.versioncompareservice.service;

import com.example.versioncompareservice.model.Response;
import com.example.versioncompareservice.model.Version;
import mrmathami.cia.java.JavaCiaException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VersionService {

    Response getCompare (MultipartFile[] files) throws JavaCiaException, IOException;

    Response getCompare (List<MultipartFile> files) throws JavaCiaException, IOException;

    Response getCompare (Version files) throws JavaCiaException, IOException;
}
