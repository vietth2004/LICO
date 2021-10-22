package com.example.fileservice.service;


import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

@Service
public class FileServiceImpl implements FileService{

    public String readFile(String address) throws IOException {
        String content = new String();
        try {

            File myObj = new File(address);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                content += data + "\\n ";
            }
            myReader.close();
            return content;
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return "Error";
        }
    }
}
