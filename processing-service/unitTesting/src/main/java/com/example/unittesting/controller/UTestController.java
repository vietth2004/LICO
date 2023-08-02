package com.example.unittesting.controller;

import com.example.unittesting.Sevice.UTestService;
import com.example.unittesting.Sevice.project.ProjectService;
import com.example.unittesting.model.Request;
import com.example.unittesting.util.JwtUtils;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;


@SpringBootApplication
@RestController
@RequestMapping("/api/unit-testing-service")
public class UTestController {
   private final UTestService utestService;

    public UTestController(UTestService utestService, ProjectService projectService, JwtUtils jwtUtils) {
        this.utestService = utestService;

    }


    @GetMapping("/is-running")
    public String running() {
        return "Hi there, I am still alive";
    }
    @PostMapping("/process")
    public ResponseEntity<Object> Process(@RequestParam(name="parser") List<String> parserList,
                                              @RequestBody MultipartFile file,
                                              @RequestParam(name="user", required = false, defaultValue = "anonymous") String user,
                                              @RequestParam(name="project", required = false, defaultValue = "tmp-prj") String project) throws IOException {
        if (file != null) {
            String path = utestService.buildProject(parserList, file, user, project);

            Object result = utestService.build(path);
            path += "\\tmp-prjt.json";
            return ResponseEntity.status(HttpStatus.OK).body("Success");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi xảy ra trong quá trình xử lý yêu cầu: ");
    }

    @GetMapping(value = "/view-tree/{nameProject:.+}")
    public ResponseEntity<Object> NodeTree(@PathVariable String nameProject) {

        try {
            File file = new File("project/anonymous/tmp-prj/" + nameProject + "/tmp-prjt.json");
            if (!file.exists()) {
                // Xử lý khi tệp không tồn tại
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Đường dẫn không tồn tại!");
            } else {
                JSONParser jsonParser = new JSONParser();
                FileReader fileReader = new FileReader(file.getAbsolutePath());
                Object analysisFile;
                try {
                    Object obj = jsonParser.parse(fileReader);
                    analysisFile = obj;
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                // Trả về kết quả phân tích từ tệp JSON
                return ResponseEntity.ok(analysisFile);
            }
        } catch (IOException e) {
            // Xử lý ngoại lệ và trả về phản hồi lỗi
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi xảy ra trong quá trình xử lý yêu cầu: " + e.getMessage());
        }
    }
    @PostMapping("/source-code")
    public ResponseEntity<Object> NodeByPath (@RequestBody Request request) throws IOException {
        try {
            File file = new File(request.getPath());
            String path = file.getAbsolutePath();
            if (!file.exists()) {
                // Xử lý khi tệp không tồn tại
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } else {
                path += "\\tmp-prjt.json";
                Object result = utestService.build(file.getAbsolutePath());
                return ResponseEntity.ok(result);
            }
        } catch (IOException e) {
            // Xử lý ngoại lệ và trả về phản hồi lỗi
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi xảy ra trong quá trình xử lý yêu cầu: " + e.getMessage());
        }

    }

}
