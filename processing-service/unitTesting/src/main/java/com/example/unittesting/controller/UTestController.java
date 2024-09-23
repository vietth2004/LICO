package com.example.unittesting.controller;

import com.example.unittesting.Sevice.UTestService;

import com.example.unittesting.UnitTestingApplication;
import core.testGeneration.TestGeneration;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.*;

@SpringBootApplication
@RestController
@RequestMapping("/api/unit-testing-service")
public class UTestController {
    private final UTestService utestService;

    public UTestController(UTestService utestService) {
        this.utestService = utestService;
    }

    @GetMapping("/restartService")
    public void restartService() {
        UnitTestingApplication.restart();
    }

    @GetMapping(value = "/unit")
    @Operation(
            summary = "This is API auto run full Concolic ",
            description = "Đây là API có input (targetId, nameProject) gửi về backend và phân tích method có" +
                    "id là targetId thuộc projecet có tên là nameProject và chạy tự động kiểm thử đơn vị",
            parameters = {@io.swagger.v3.oas.annotations.Parameter(name = "targetId", description = "Id của hàm mà người dùng muốn lấy thông tin chi tiết", example = "15"),
                    @io.swagger.v3.oas.annotations.Parameter(name = "nameProject", description = "Tên của Project chứa hàm người dùng muốn lấy thông tin", example = "test.zip.project"),
                    @io.swagger.v3.oas.annotations.Parameter(name = "coverageType", description = "Loại độ phủ cần kiểm thử")}
    )
    public ResponseEntity<Object> getUnitTest(@RequestParam int targetId, @RequestParam String nameProject, @RequestParam String coverageType) throws IOException {
        TestGeneration.Coverage coverage;

        switch (coverageType) {
            case "statement":
                coverage = TestGeneration.Coverage.STATEMENT;
                break;
            case "branch":
                coverage = TestGeneration.Coverage.BRANCH;
                break;
            case "path":
                coverage = TestGeneration.Coverage.PATH;
                break;
            case "mcdc":
                coverage = TestGeneration.Coverage.MCDC;
                break;
            default:
                throw new RuntimeException("Invalid coverage type");
        }

        return ResponseEntity.ok(utestService.runAutomationTest(targetId, nameProject, coverage));
    }

}