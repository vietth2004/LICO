package com.example.parserservice.service.project;

import com.example.parserservice.service.project.config.FileStorageProperties;
import com.example.parserservice.service.project.exception.FileStorageException;
import com.example.parserservice.util.JwtUtils;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class ProjectService {

    private final Path fileStorageLocation;

    private final JwtUtils jwtUtils;

    @Autowired
    public ProjectService(FileStorageProperties fileStorageProperties, JwtUtils jwtUtils) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        this.jwtUtils = jwtUtils;

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file, String user, String project) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // Init file path
        String filePath = "./project/" + user + "/" + project +  "/" + fileName;
        String folderPath = "./project/" + user + "/" + project +  "/" + fileName + ".project";
//        String folderPath = "D:\\" + fileName;  "anonymous/" +

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            new File("./project/" + user + "/" + project).mkdirs();

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(user + "/" + project + "/" + fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            System.out.println(filePath + " " + folderPath);
//            javaUnzipFile(filePath, folderPath);
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

    public String javaUnzipFile(String Filepath, String DestinationFolderPath) {
        try {
            String fileZip = Filepath;
            File destDir = new File(DestinationFolderPath);
            byte[] buffer = new byte[1024];
            ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                // ...
                while (zipEntry != null) {
                    File newFile = newFile(destDir, zipEntry);
                    if (zipEntry.isDirectory()) {
                        if (!newFile.isDirectory() && !newFile.mkdirs()) {
                            throw new IOException("Failed to create directory " + newFile);
                        }
                    } else {
                        // fix for Windows-created archives
                        File parent = newFile.getParentFile();
                        if (!parent.isDirectory() && !parent.mkdirs()) {
                            throw new IOException("Failed to create directory " + parent);
                        }

                        // write file content
                        FileOutputStream fos = new FileOutputStream(newFile);
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                        fos.close();
                    }
                    zipEntry = zis.getNextEntry();
                }
            }
            zis.closeEntry();
            zis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DestinationFolderPath;
    }

    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }
}
