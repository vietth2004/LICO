package com.example.ciaservice.service;

import com.example.ciaservice.ast.Dependency;
import com.example.ciaservice.model.Response;

import java.util.List;

public interface CiaService {

    public Response calculate (List<Dependency> dependencies);
}
