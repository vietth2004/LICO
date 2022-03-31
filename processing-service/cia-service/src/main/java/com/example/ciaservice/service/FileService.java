package com.example.ciaservice.service;

import com.example.ciaservice.model.Response;

import javax.servlet.http.HttpServletResponse;

public interface FileService {
    void exportToCsv(Response data, HttpServletResponse response);
    void exportToExcel(Response data, HttpServletResponse response);
}
