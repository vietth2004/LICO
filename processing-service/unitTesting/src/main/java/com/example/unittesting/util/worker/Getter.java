package com.example.unittesting.util.worker;

import com.example.unittesting.Node.JavaNode;
import com.example.unittesting.Node.Parameter;
import com.example.unittesting.Node.MethodNode;
import com.example.unittesting.Node.Node;
import com.example.unittesting.model.Response;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Getter {
    private static int idCounter = 1;
    private static final String[] IGNORED_DIRS = {".git", "resources", ".ide", ".mvn" , "target"};
    public static Response getResponse(String path) {
        setIdCounter(1);
        File mainDir = new File(path);
        if (mainDir.isDirectory()) {
            File[] files = mainDir.listFiles();
            Node rootNode = createNodeFromDirectory(files[0]);
            return new Response(rootNode);
        }
        // Xử lý logic cho trường hợp mainDir không phải là thư mục

        return new Response();
    }

    private static Node createNodeFromDirectory(File directory) {
        if (shouldIgnoreDir(directory.getName())) {
            return null; // Bỏ qua các thư mục không cần phân tích
        }
        Node node = new Node(getNextId(), directory.getName(), "Package", new ArrayList<>(), directory.getAbsolutePath());
        List<Node> children = new ArrayList<>();

        File[] files = directory.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                Node childNode = null;
                if (files[i].isDirectory()) {
                    childNode = createNodeFromDirectory(files[i]);
                } else {
                    String fileName = files[i].getName();
                    String fileExtension = getFileExtension(fileName);
                    if (fileExtension.equals("java")) {
                        childNode = createNodeFromFile(files[i]);
                    }
                }
                if (childNode != null) {
                    children.add(childNode);
                }
            }
        }

        node.setChildren(children);
        return node;
    }


    private static Node createNodeFromFile(File file) {
        String fileName = file.getName();
        String fileExtension = getFileExtension(fileName);
        Node node = new Node(getNextId(), fileName, null, new ArrayList<>(), file.getAbsolutePath());
        JavaNode javaNode = createJavaNodeFromFile(file);
        if (javaNode != null) {
            List<Node> children = new ArrayList<>();
            children.add(javaNode);
            node.setEntityClass("JavaNode");
            node.setChildren(children);
        }

        return node;
    }
    private static JavaNode createJavaNodeFromFile(File file) {
        try {
            CompilationUnit compilationUnit = StaticJavaParser.parse(file);
            List<MethodDeclaration> methodDeclarations = compilationUnit.findAll(MethodDeclaration.class);
            List<MethodNode> methodNodes = new ArrayList<>();

            for (MethodDeclaration methodDeclaration : methodDeclarations) {
                String methodName = methodDeclaration.getSignature().asString();
                List<Parameter> parameters = new ArrayList<>();
                for (com.github.javaparser.ast.body.Parameter parameter : methodDeclaration.getParameters()) {
                    parameters.add(new Parameter(parameter.getNameAsString(), parameter.getType().asString()));
                }
                String returnType = methodDeclaration.getType().asString();
                parameters.add(new Parameter("return", returnType));
                String packageName = compilationUnit.getPackageDeclaration().map(pkg -> pkg.getName().asString()).orElse("");
                String className = file.getName().replaceFirst("[.][^.]+$", "");
                String qualifiedName = packageName + "." + className + "." + methodDeclaration.getNameAsString();
                String uniqueName = packageName + "." + className + "." + methodDeclaration.getSignature().asString();

                int methodNodeId = getNextId();
                StringBuilder content = new StringBuilder(methodDeclaration.toString());

                MethodNode methodNode = new MethodNode(
                        methodNodeId,
                        methodName,
                        new ArrayList<>(),
                        file.getAbsolutePath(),
                        qualifiedName,
                        uniqueName,
                        (ArrayList<Parameter>) parameters,
                        content
                );
                methodNodes.add(methodNode);
            }

            if (!methodNodes.isEmpty()) {
                JavaNode javaNode = new JavaNode(getNextId(), file.getName(), "JavaNode", new ArrayList<>(), file.getAbsolutePath());
                javaNode.setChildren(methodNodes);
                return javaNode;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }




    private static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }

    private static String getQualifiedName(File file) {
        try {
            CompilationUnit compilationUnit = StaticJavaParser.parse(file);
            String packageName = compilationUnit.getPackageDeclaration().map(pkg -> pkg.getName().asString()).orElse("");
            return packageName + "." + file.getName();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String getName(File file) {
        return file.getName().replace(".java", "");
    }

    private static int getNextId() {
        return idCounter++;
    }

    public static void setIdCounter(int idCounter) {
        Getter.idCounter = idCounter;
    }
    private static boolean shouldIgnoreDir(String dirName) {
        for (String ignoredDir : IGNORED_DIRS) {
            if (dirName.equalsIgnoreCase(ignoredDir)) {
                return true;
            }
        }
        return false;
    }
}
