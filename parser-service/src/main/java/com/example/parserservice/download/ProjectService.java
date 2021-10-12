package com.example.parserservice.download;

import com.example.parserservice.download.config.FileStorageProperties;
import com.example.parserservice.download.exception.FileStorageException;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class ProjectService {

    private final Path fileStorageLocation;

    @Autowired
    public ProjectService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String filePath = "./project/" + fileName;
        String folderPath = "./project/" + "anonymous/" + fileName + ".project";
//        String folderPath = "D:\\" + fileName;  "anonymous/" +

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            System.out.println(filePath + " " + folderPath);
            unzipFile(filePath, folderPath);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public String unzipFile(String Filepath, String DestinationFolderPath) {
        try {
            ZipFile zipFile = new ZipFile(Filepath);
            List fileHeaders = zipFile.getFileHeaders();
            for(int i=0;i<fileHeaders.size();i++) {
                FileHeader fileHeader=(FileHeader) fileHeaders.get(i);
                String fileName = fileHeader.getFileName();
                if (fileName.contains("\\")) {
                    fileName=fileName.replace("\\","\\\\");
                    String[] Folders=fileName.split("\\\\");
                    StringBuilder newFilepath = new StringBuilder();
                    newFilepath.append(DestinationFolderPath);
                    for (int j=0;j<Folders.length-1;j++){
                        newFilepath.append(File.separator);
                        newFilepath.append(Folders[j]);
                    }
                    zipFile.extractFile(fileHeader, Folders[Folders.length-1], newFilepath.toString(), null);
                }else {
                    zipFile.extractFile(fileHeader,DestinationFolderPath);
                }
            }
        } catch (ZipException e) {
            e.printStackTrace();
        }
        return DestinationFolderPath;
    }
}
