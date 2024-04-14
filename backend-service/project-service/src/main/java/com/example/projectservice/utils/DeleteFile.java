package com.example.projectservice.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class DeleteFile {
    public static void deleteFolder(String path) {
        File folder = new File(path);
        if (!folder.exists()) {
            return;
        }
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFolder(file.getAbsolutePath());
                } else {
                    file.delete();
                }
            }
        }
        folder.delete();
    }
    public static void deleteZipFile(String filePath) throws IOException {
        String filePathZip = getZipFilePath(filePath);
        File file = new File(filePathZip);
        if (file.exists() && file.isFile() && filePathZip.endsWith(".zip")) {
            Files.deleteIfExists(file.toPath());
            System.out.println("Zip file deleted successfully.");
        } else {
            System.out.println("File is not a valid zip file.");
        }
    }
    public static String getZipFilePath(String path) {
        File file = new File(path);
        String zipFilePath = null;
            String fileName = file.getName();
            if (fileName.endsWith(".zip.project")) {
                zipFilePath = path.substring(0, path.lastIndexOf(".zip.project")) + ".zip";
            }
        return zipFilePath;
    }
}
