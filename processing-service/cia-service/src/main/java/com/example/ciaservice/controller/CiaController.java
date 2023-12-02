package com.example.ciaservice.controller;


import com.example.ciaservice.model.Request;
import com.example.ciaservice.model.Response;
import com.example.ciaservice.service.CiaService;
import com.example.ciaservice.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/cia-service/")
public class CiaController {

    private final CiaService ciaService;

    @Autowired
    private FileService fileService;

    public CiaController(CiaService ciaService) {
        this.ciaService = ciaService;
    }

    @PostMapping("/calculate")
    public Response calculateNodeWeight(@RequestBody Request request) {
        return ciaService.calculate(request.getDependencies(), request.getTotalNodes());
    }

    @GetMapping("/calculate/export/{type}")
    public void exportNodeWeightToFile(@RequestBody Request request, @PathVariable String type, HttpServletResponse response) {
        Response nodeWeight = ciaService.calculate(request.getDependencies(), request.getTotalNodes());
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = dateFormatter.format(new Date());

        if (type.equals("csv")) {
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", "attachment; filename=\"response" + currentDateTime + ".csv\"");
            fileService.exportToCsv(nodeWeight, response);
        } else if (type.equals("excel")) {
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", "attachment; filename=\"response" + currentDateTime + ".xlsx\"");
            fileService.exportToExcel(nodeWeight, response);
        }
    }

    @PostMapping("/impact")
    public Response findImpact(@RequestBody Request request) {
        return ciaService.findImpact(request.getJavaNodes(), request.getDependencies(), request.getTotalNodes(), request.getChangedNodes());
    }

    @GetMapping("/impact/export/{type}")
    public void exportImpactToFile(@RequestBody Request request, @PathVariable String type, HttpServletResponse response) {
        Response impact = ciaService.findImpact(request.getJavaNodes(), request.getDependencies(), request.getTotalNodes(), request.getChangedNodes());
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = dateFormatter.format(new Date());

        if (type.equals("csv")) {
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", "attachment; filename=\"response" + currentDateTime + ".csv\"");
            fileService.exportToCsv(impact, response);
        } else if (type.equals("excel")) {
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", "attachment; filename=\"response" + currentDateTime + ".xlsx\"");
            fileService.exportToExcel(impact, response);
        }

    }

}
