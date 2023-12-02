package com.example.javaservice.utility;

//import com.example.javaservice.javacia.java.project.JavaSourceFileType;
//import com.example.javaservice.javacia.jdt.project.builder.parameter.BuildInputSources;
import mrmathami.annotations.Nonnull;
import mrmathami.cia.java.jdt.project.builder.parameter.BuildInputSources;
import mrmathami.cia.java.project.JavaSourceFileType;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

public final class Utils {
    private Utils() {
    }

    public static void getFileList(@Nonnull BuildInputSources.InputModule inputModule, @Nonnull Path dir)
            throws IOException {
        try (final DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (final Path path : stream) {
                if (path.toFile().isDirectory()) {
                    getFileList(inputModule, path);
                } else if (path.getFileName().toString().toLowerCase(Locale.ROOT).endsWith(".java")) {
                    inputModule.createFile(path, JavaSourceFileType.JAVA);
                }
            }
        }
    }
}
