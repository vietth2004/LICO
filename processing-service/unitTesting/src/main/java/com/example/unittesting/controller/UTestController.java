package com.example.unittesting.controller;

import com.example.unittesting.Sevice.UTestService;
import com.example.unittesting.UnitTestingApplication;
import core.testGeneration.TestGeneration;
import core.testResult.result.autoTestResult.TestResult;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.io.*;
import java.text.DecimalFormat;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

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

        DecimalFormat df = new DecimalFormat("#.##");
        List<Double> memorySamples = Collections.synchronizedList(new ArrayList<>());
        AtomicBoolean isRunning = new AtomicBoolean(true);

        Thread monitorThread = new Thread(() -> {
            Runtime runtime = Runtime.getRuntime();
            while (isRunning.get()) {
                // Đo RAM hiện tại (MB)
                long usedMemory = runtime.totalMemory() - runtime.freeMemory();
                double usedMB = usedMemory / (1024.0 * 1024.0);

                memorySamples.add(usedMB);

                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        monitorThread.start();

        long startTime = System.currentTimeMillis();

        System.out.println(" bắt đầu đo đạc");

        try {
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

            ResponseEntity<Object> tmp = ResponseEntity.ok(utestService.runAutomationTest(targetId, nameProject, coverage));
            int ok = 1;
            return tmp;
        } catch (Exception e) {
            throw new  RuntimeException(e);
        } finally {
            isRunning.set(false);
            monitorThread.interrupt();
            long endTime = System.currentTimeMillis();

            double sum = 0;
            for (Double sample : memorySamples) {
                sum += sample;
            }

            double averageMemory = 0;
            if (memorySamples.isEmpty()) {
                long used = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                averageMemory = used / (1024.0 * 1024.0);
                System.out.println("Chạy quá nhanh, lấy ram tức thời.");
            } else {
                averageMemory = sum / memorySamples.size();
            }

            System.out.println("\n==========================================");
            System.out.println("BÁO CÁO HIỆU NĂNG");
            System.out.println("==========================================");
            System.out.println("Số mẫu đã đo    : " + memorySamples.size() + " lần");
            System.out.println("RAM Trung bình  : " + df.format(averageMemory) + " MB");

            if (!memorySamples.isEmpty()) {
                double maxMem = Collections.max(memorySamples);
                System.out.println("RAM Đỉnh: " + df.format(maxMem) + " MB");
            }

            System.out.println("Tổng thời gian: " + df.format((endTime - startTime) / 1000.0) + " s");
            System.out.println("==========================================\n");
        }
    }

    @GetMapping(value = "/regressionTest")
    @Operation(
            parameters = {@io.swagger.v3.oas.annotations.Parameter(name = "nameProject"),
                    @io.swagger.v3.oas.annotations.Parameter(name = "coverageType")}
    )
    public ResponseEntity<Object> getRegressionTest(@RequestParam String nameProject, @RequestParam String coverageType) throws IOException {
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

        return ResponseEntity.ok(utestService.runRegressionTest(nameProject, coverage));
    }

    @GetMapping(value = "/multiple-unit")
    public ResponseEntity<Object> getUnitTests(@RequestParam List<Integer> targetIds, @RequestParam String nameProject, @RequestParam String coverageType) throws IOException {
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
        List<Object> results = new ArrayList<>();
        for (int targetId : targetIds) {
            Object result = utestService.runAutomationTest(targetId, nameProject, coverage);
            results.add(result);
        }
        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/unit-test-file")
    public ResponseEntity<Object> getUnitTestFile(@RequestParam List<Integer> targetIds, @RequestParam String nameProject, @RequestParam String coverageType) throws IOException {
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
        List<Object> results = new ArrayList<>();
        for (int targetId : targetIds) {
            Object result = utestService.runAutomationTestFile(targetId, nameProject, coverage);
            results.add(result);
        }
        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/unit-test-project")
    public ResponseEntity<Object> getUnitTestProject(@RequestParam String nameProject, @RequestParam String coverageType) throws IOException {
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
        return ResponseEntity.ok(utestService.runAutomationTestProject(nameProject, coverage));
    }

    @GetMapping(value = "/unit-test-all")
    public ResponseEntity<Object> getUnitTestAll(@RequestParam List<Integer> targetIds, @RequestParam String nameProject, @RequestParam String coverageType) throws IOException {
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
        return ResponseEntity.ok(utestService.runAutomationTestAll(targetIds, nameProject, coverage));
    }
}