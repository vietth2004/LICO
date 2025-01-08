package com.example.versioncompareservice.utils.worker;

import com.example.versioncompareservice.model.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class Writer {

    public static void write(String folderPath, Response response, String project) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writeValue(new File(folderPath + "/" + project + ".json"), response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
