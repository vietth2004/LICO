package com.example.parserservice.util.worker;

import com.example.parserservice.model.Path;
import com.example.parserservice.model.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class Writer {

    public static void write(Path filePath, Response response, String project) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writeValue(new File(filePath.getPath() + "/" + project + ".json"), response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
