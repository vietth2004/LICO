package com.example.ciaservice.service;

import com.example.ciaservice.ast.Dependency;
import com.example.ciaservice.model.Response;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CiaServiceImpl implements CiaService{

    @Override
    public Response calculate(List<Dependency> dependencies) {
        return new Response();
    }

}
