package com.example.gitservice.utils;

import org.apache.commons.io.FileDeleteStrategy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DirectoryUtils {

    public static boolean checkIfDirExist(String path) {
        return Files.exists(Path.of(path)) ? true : false;
    }

    public static boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }

    public static void deleteDir(File dir) {
        File[] files = dir.listFiles();
        if(files != null) {
            for (final File file : files) {
                deleteDir(file);
            }
        }
        dir.delete();
    }

    public static void delete(String path) throws IOException {
        File fin = new File(path);

        for (File file : fin.listFiles()) {
            FileDeleteStrategy.FORCE.delete(file);
        }
    }

}
