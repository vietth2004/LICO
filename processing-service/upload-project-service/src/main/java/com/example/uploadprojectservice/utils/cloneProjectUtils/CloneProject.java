package com.example.uploadprojectservice.utils.cloneProjectUtils;

import com.example.uploadprojectservice.utils.cloneProjectUtils.dataModel.ClassData;
import org.eclipse.jdt.core.dom.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class CloneProject {

    public static String getJavaDirPath(String originDir) {
        File dir = new File(originDir);

        if (!dir.isDirectory()) {
            throw new RuntimeException("Invalid Dir");
        }
        for(File file : dir.listFiles()) {
            if(file.isDirectory()) {
                if(file.getName().equals("java")) {
                    return file.getPath();
                }
                else {
                    String dirPath = getJavaDirPath(file.getPath());
                    if(dirPath.endsWith("java")) return dirPath;
                }
            }
        }

        return "";
    }
    public static void cloneProject(String directoryPath, String cloneDirectoryPath) throws IOException {
        deleteFilesInDirectory(cloneDirectoryPath);

        File[] files = getFilesInDirectory(directoryPath);

        for (File file : files) {
            if (file.isDirectory()) {
                String dirName = file.getName();
                createCloneDirectory(cloneDirectoryPath, dirName);
                cloneProject(directoryPath + "\\" + dirName, cloneDirectoryPath + "\\" + dirName);
            } else if (file.isFile() && file.getName().endsWith("java")) {
                String fileName = file.getName();
                createCloneFile(cloneDirectoryPath, fileName);
                CompilationUnit compilationUnit = Parser.parseFileToCompilationUnit(directoryPath + "\\" + fileName);
                String sourceCode = createCloneSourceCode(compilationUnit);
                writeDataToFile(sourceCode, cloneDirectoryPath + "\\" + fileName);
            }
        }
    }

    private static File[] getFilesInDirectory(String directoryPath) {
        File directory = new File(directoryPath);

        if (!directory.isDirectory()) {
            throw new RuntimeException("Invalid Dir");
        }

        return directory.listFiles();
    }

    private static void deleteFilesInDirectory(String directoryPath) {
        File[] files = getFilesInDirectory(directoryPath);
        for (File file : files) {
            if (file.isDirectory()) {
                deleteFilesInDirectory(file.getPath());
            }
            file.delete();
        }
    }

    private static void createCloneDirectory(String parent, String child) {
        File newDirectory = new File(parent, child);

        boolean created = newDirectory.mkdir();

        if (!created) {
            System.out.println("Existed Dir");
        }
    }

    private static void createCloneFile(String directoryPath, String fileName) {
        File directory = new File(directoryPath);
        if (!directory.isDirectory()) {
            throw new RuntimeException("Invalid dir");
        }

        File newFile = new File(directory, fileName);

        try {
            boolean created = newFile.createNewFile();

            if (!created) {
                System.out.println("Existed file");
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't create file");
        }
    }

    private static String createCloneSourceCode(CompilationUnit compilationUnit) {
        StringBuilder result = new StringBuilder();

        //Packet
        if (compilationUnit.getPackage() != null) {
            result.append("package data.clonedProject.").append(compilationUnit.getPackage().getName().toString()).append(";\n");
        } else {
            result.append("package data.clonedProject;\n");
        }

        //Imports
        for (ASTNode iImport : (List<ASTNode>) compilationUnit.imports()) {
            result.append(iImport);
        }
        result.append("import core.dataStructure.MarkedPath;\n");

        final ClassData[] classDataArr = {new ClassData()};
        ASTVisitor classVisitor = new ASTVisitor() {
            @Override
            public boolean visit(TypeDeclaration node) {
                classDataArr[0] = new ClassData(node);
                return true;
            }
        };
        compilationUnit.accept(classVisitor);

        // Class type (interface/class) and class name
        ClassData classData = classDataArr[0];
        result.append("public ").append(classData.getTypeOfClass()).append(" ").append(classData.getClassName());

        //Extensions
        if (classData.getSuperClassName() != null) {
            result.append(" extends ").append(classData.getSuperClassName());
        }

        //implementations
        if (classData.getSuperInterfaceName() != null) {
            result.append(" implements ");
            List<String> interfaceList = classData.getSuperInterfaceName();
            for (int i = 0; i < interfaceList.size(); i++) {
                result.append(interfaceList.get(i));
                if (i != interfaceList.size() - 1) {
                    result.append(", ");
                }
            }
        }

        result.append(" {\n");


        List<ASTNode> methods = new ArrayList<>();
        ASTVisitor methodsVisitor = new ASTVisitor() {
            @Override
            public boolean visit(TypeDeclaration node) {
                for (MethodDeclaration method : node.getMethods()) {
                    if (!method.isConstructor()) {
                        methods.add(method);
                    }
                }
                return true;
            }
        };
        compilationUnit.accept(methodsVisitor);

        for (ASTNode astNode : methods) {
            result.append(createCloneMethod((MethodDeclaration) astNode));
        }

        result.append("}");

        return result.toString();
    }

    private static String createCloneMethod(MethodDeclaration method) {
        StringBuilder cloneMethod = new StringBuilder();

        cloneMethod.append("public static ").append(method.getReturnType2()).append(" ").append(method.getName()).append("(");
        List<ASTNode> parameters = method.parameters();
        for (int i = 0; i < parameters.size(); i++) {
            cloneMethod.append(parameters.get(i));
            if (i != parameters.size() - 1) cloneMethod.append(", ");
        }
        cloneMethod.append(")\n");

        cloneMethod.append(generateCodeForBlock(method.getBody())).append("\n");

        return cloneMethod.toString();
    }

    private static String generateCodeForOneStatement(ASTNode statement, String markMethodSeparator) {
        if (statement == null) {
            return "";
        }

        if (statement instanceof Block) {
            return generateCodeForBlock((Block) statement);
        } else if (statement instanceof IfStatement) {
            return generateCodeForIfStatement((IfStatement) statement);
        } else if (statement instanceof ForStatement) {
            return generateCodeForForStatement((ForStatement) statement);
        } else if (statement instanceof WhileStatement) {
            return generateCodeForWhileStatement((WhileStatement) statement);
        } else if (statement instanceof DoStatement) {
            return generateCodeForDoStatement((DoStatement) statement);
        } else {
            return generateCodeForNormalStatement(statement, markMethodSeparator);
        }

    }

    private static String generateCodeForBlock(Block block) {
        StringBuilder result = new StringBuilder();
        List<ASTNode> statements = block.statements();

        result.append("{\n");
        for (int i = 0; i < statements.size(); i++) {
            result.append(generateCodeForOneStatement(statements.get(i), ";"));
        }
        result.append("}\n");

        return result.toString();
    }

    private static String generateCodeForIfStatement(IfStatement ifStatement) {
        StringBuilder result = new StringBuilder();

        result.append("if (").append(generateCodeForCondition(ifStatement.getExpression())).append(")\n");
        result.append("{\n");
        result.append(generateCodeForOneStatement(ifStatement.getThenStatement(), ";"));
        result.append("}\n");


        String elseCode = generateCodeForOneStatement(ifStatement.getElseStatement(), ";");
        if (!elseCode.equals("")) {
            result.append("else {\n").append(elseCode).append("}\n");
        }

        return result.toString();
    }

    private static String generateCodeForForStatement(ForStatement forStatement) {
        StringBuilder result = new StringBuilder();

        // Initializers
        List<ASTNode> initializers = forStatement.initializers();
        for (ASTNode initializer : initializers) {
            result.append(generateCodeForMarkMethod(initializer, ";"));
        }
        result.append("for (");
        for (int i = 0; i < initializers.size(); i++) {
            result.append(initializers.get(i));
            if (i != initializers.size() - 1) result.append(", ");
        }

        // Condition
        result.append("; ");
        result.append(generateCodeForCondition(forStatement.getExpression()));

        // Updaters
        result.append("; ");
        List<ASTNode> updaters = forStatement.updaters();
        for (int i = 0; i < updaters.size(); i++) {
            result.append(generateCodeForOneStatement(updaters.get(i), ","));
            if (i != updaters.size() - 1) result.append(", ");
        }

        // Body
        result.append(") {\n");
        result.append(generateCodeForOneStatement(forStatement.getBody(), ";"));
        result.append("}\n");

        return result.toString();
    }

    private static String generateCodeForWhileStatement(WhileStatement whileStatement) {
        StringBuilder result = new StringBuilder();

        // Condition
        result.append("while (");
        result.append(generateCodeForCondition(whileStatement.getExpression()));
        result.append(") {\n");

        result.append(generateCodeForOneStatement(whileStatement.getBody(), ";"));
        result.append("}\n");

        return result.toString();
    }

    private static String generateCodeForDoStatement(DoStatement doStatement) {
        StringBuilder result = new StringBuilder();

        // Do body
        result.append("do {");
        result.append(generateCodeForOneStatement(doStatement.getBody(), ";"));
        result.append("}\n");

        // Condition
        result.append("while (");
        result.append(generateCodeForCondition(doStatement.getExpression()));
        result.append(");\n");

        return result.toString();
    }

    private static String generateCodeForNormalStatement(ASTNode statement, String markMethodSeparator) {
        StringBuilder result = new StringBuilder();

        result.append(generateCodeForMarkMethod(statement, markMethodSeparator));
        result.append(statement);

        return result.toString();
    }

    private static String generateCodeForMarkMethod(ASTNode statement, String markMethodSeparator) {
        StringBuilder result = new StringBuilder();

        String stringStatement = statement.toString();
        StringBuilder newStatement = new StringBuilder();

        // Rewrite Statement for mark method
        for (int i = 0; i < stringStatement.length(); i++) {
            char charAt = stringStatement.charAt(i);

            if (charAt == '\n') {
                newStatement.append("\\n");
                continue;
            } else if (charAt == '"') {
                newStatement.append("\\").append('"');
                continue;
            } else if (i != stringStatement.length() - 1 && charAt == '\\' && stringStatement.charAt(i + 1) == 'n') {
                newStatement.append("\" + \"").append("\\n").append("\" + \"");
                i++;
                continue;
            }

            newStatement.append(charAt);
        }

        result.append("MarkedPath.markOneStatement(\"").append(newStatement).append("\", false, false)").append(markMethodSeparator).append("\n");

        return result.toString();
    }

    private static String generateCodeForCondition(Expression condition) {
        StringBuilder result = new StringBuilder();

        if (condition instanceof InfixExpression && isSeparableOperator(((InfixExpression) condition).getOperator())) {
            InfixExpression infixCondition = (InfixExpression) condition;

            result.append("(").append(generateCodeForCondition(infixCondition.getLeftOperand())).append(") ").append(infixCondition.getOperator()).append(" (");
            result.append(generateCodeForCondition(infixCondition.getRightOperand())).append(")");

            List<ASTNode> extendedOperands = infixCondition.extendedOperands();
            for (ASTNode operand : extendedOperands) {
                result.append(" ").append(infixCondition.getOperator()).append(" ");
                result.append("(").append(generateCodeForCondition((Expression) operand)).append(")");
            }
        } else {
            result.append("((").append(condition).append(") && MarkedPath.markOneStatement(\"").append(condition).append("\", true, false))");
            result.append(" || MarkedPath.markOneStatement(\"").append(condition).append("\", false, true)");
        }

        return result.toString();
    }

    private static boolean isSeparableOperator(InfixExpression.Operator operator) {
        return operator.equals(InfixExpression.Operator.CONDITIONAL_OR) ||
                operator.equals(InfixExpression.Operator.OR) ||
                operator.equals(InfixExpression.Operator.CONDITIONAL_AND) ||
                operator.equals(InfixExpression.Operator.AND);
    }

    private static void writeDataToFile(String data, String path) {
        try {
            FileWriter writer = new FileWriter(path);
            writer.write(data + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
