package com.jcia.xmlservice.Utils.Helper;

import java.util.Arrays;
import java.util.List;

public class StringHelper {

    public static List<String> SUPPORTED_EXTENSIONS = Arrays.asList("html", "htm", "xml", "xhtml", "jsp");

    public static String strip(String input) {
        return input.trim();
    }

    public static String normalizeJavaClassPath(String javaClassPath) {
        String className = javaClassPath.substring(javaClassPath.lastIndexOf(".") + 1, javaClassPath.length());
        return javaClassPath.replace(".", "/") + ".java/" + className;
    }


}
