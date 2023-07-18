package com.example.unittesting.Sevice;

import com.example.unittesting.model.Response;
import com.example.unittesting.util.worker.Getter;
import com.example.unittesting.util.worker.Writer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service

public class UTestServiceImpl implements UTestService {
    @Override
    public ResponseEntity<Response> build(String path) throws IOException {
        Response response = new Response();
        response = Getter.getResponse(path);
        Writer.write(path, response, "tmp-prjt");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}