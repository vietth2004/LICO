package com.example.unittesting.controller;

import com.example.unittesting.Sevice.UTestService;

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

    @GetMapping("/is-running")
    public String running() {
        return "Hi there, I am still alive";
    }

    @GetMapping(value = "/unit")
    @Operation(
            summary = "This is API auto run full Concolic ",
            description = "Đây là API có input (targetId, nameProject) gửi về backend và phân tích method có" +
                    "id là targetId thuộc projecet có tên là nameProject và chạy tự động kiểm thử đơn vị",
            parameters = {@io.swagger.v3.oas.annotations.Parameter(name = "targetId", description = "Id của hàm mà người dùng muốn lấy thông tin chi tiết", example = "15"),
                    @io.swagger.v3.oas.annotations.Parameter(name = "nameProject", description = "Tên của Project chứa hàm người dùng muốn lấy thông tin", example = "test.zip.project")}
    )
    public ResponseEntity<Object> getUnitTest(@RequestParam int targetId, @RequestParam String nameProject) throws IOException {
        return ResponseEntity.ok(utestService.getRunFullConcolic(targetId, nameProject));
    }

}