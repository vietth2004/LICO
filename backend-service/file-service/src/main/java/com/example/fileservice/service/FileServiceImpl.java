package com.example.fileservice.service;


import com.example.fileservice.model.FileResponse;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class FileServiceImpl implements FileService{

    public FileResponse readFile(String address) {
        String content = new String();
        List<String> fileContent = new ArrayList<>();

        try {
            File myObj = new File(address);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                fileContent.add(data);
                content += data + "\\n ";
            }

            myReader.close();
            return new FileResponse(content, fileContent);

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return new FileResponse("Error");
        }
    }
}
