package com.example.gitservice.thread;

import com.example.gitservice.utils.DirectoryUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
public class ZipFolderThread implements Runnable{

    private String sourceDirPath;
    private String zipFilePath;

    public ZipFolderThread(String sourceDirPath, String zipFilePath) {
        this.sourceDirPath = sourceDirPath;
        this.zipFilePath = zipFilePath;
    }

    @Override
    public void run() {
        log.info("Zipping folder {} into {}", this.sourceDirPath, this.zipFilePath);
        DirectoryUtils.deleteDir(new File(this.zipFilePath));
        Path p = null;
        try {
            p = Files.createFile(Paths.get(this.zipFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Path pp = Paths.get(this.sourceDirPath);
        try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(p));
             Stream<Path> paths = Files.walk(pp)) {
            paths
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        ZipEntry zipEntry = new ZipEntry(pp.relativize(path).toString());
                        try {
                            zs.putNextEntry(zipEntry);
                            Files.copy(path, zs);
                            zs.closeEntry();
                        } catch (IOException e) {
                            System.err.println(e);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("Done zipping folder {}", this.sourceDirPath);
    }
}
