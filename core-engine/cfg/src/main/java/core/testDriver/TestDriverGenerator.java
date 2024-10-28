package core.testDriver;

import core.parser.ASTHelper;
import core.symbolicExecution.SymbolicExecution;
import org.eclipse.jdt.core.dom.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public final class TestDriverGenerator {

    private static String markMethodUtility =
            "private static boolean mark(String statement, boolean isTrueCondition, boolean isFalseCondition) {\n" +
                    "StringBuilder markResult = new StringBuilder();\n" +
                    "markResult.append(statement).append(\"===\");\n" +
                    "markResult.append(isTrueCondition).append(\"===\");\n" +
                    "markResult.append(isFalseCondition).append(\"---end---\");\n" +
                    "writeDataToFile(markResult.toString(), \"core-engine/cfg/src/main/java/data/testDriverData/runTestDriverData.txt\", true);\n" +
                    "if (!isTrueCondition && !isFalseCondition) return true;\n" +
                    "return !isFalseCondition;\n" +
                    "}\n";

    private static String writeDataToFileUtility =
            "private static void writeDataToFile(String data, String path, boolean append) {\n" +
                    "try {\n" +
                    "FileWriter writer = new FileWriter(path, append);\n" +
                    "writer.write(data);\n" +
                    "writer.close();\n" +
                    "} catch(Exception e) {\n" +
                    "e.printStackTrace();\n" +
                    "}\n" +
                    "}\n";

    private static Queue<String> stubVariableNames = new LinkedList<>();
    private static Queue<String> stubVariablesOrigins = new LinkedList<>();

    private TestDriverGenerator() {
    }

    public static String generateTestDriver(MethodDeclaration method, Object[] testData, ASTHelper.Coverage coverage) {
        StringBuilder result = new StringBuilder();

        result.append(generatePreSetup());

        result.append("public class TestDriver {\n");
        result.append(generateUtilities(method, coverage));
        result.append(generateTestRunner(method.getName().toString(), testData));
        result.append("}");

        return result.toString();
    }

    public static String generateTestDriver(MethodDeclaration method, Object[] testData, List<String> stubVariableDeclarations, ASTHelper.Coverage coverage) {
        StringBuilder result = new StringBuilder();

        result.append(generatePreSetup());

        stubVariablesOrigins = new LinkedList<>(SymbolicExecution.getStubVariablesOrigins());
        stubVariableNames = new LinkedList<>(SymbolicExecution.getStubVariableNames());

        result.append("public class TestDriver {\n");
        result.append(generateUtilities(method, stubVariableDeclarations, coverage));
        result.append(generateTestRunner(method.getName().toString(), testData));
        result.append("}");

        return result.toString();
    }

    private static String generatePreSetup() {
        StringBuilder result = new StringBuilder();
        result.append("package data.testDriverData;\n");
        result.append("import java.io.FileWriter;\n");
        return result.toString();
    }

    private static String generateTestRunner(String methodName, Object[] testData) {
        StringBuilder result = new StringBuilder();
        result.append("public static void main(String[] args) {\n");
        result.append("writeDataToFile(\"\", \"core-engine/cfg/src/main/java/data/testDriverData/runTestDriverData.txt\", false);\n");
        result.append("long startRunTestTime = System.nanoTime();\n");
        result.append("Object output = ").append(methodName).append("(");
        for (int i = 0; i < testData.length; i++) {
            if (testData[i] instanceof Character) {
                result.append("'").append(testData[i]).append("'");
            } else {
                result.append(testData[i]);
            }
            if (i != testData.length - 1) result.append(", ");
        }
        result.append(");\n");
        result.append("long endRunTestTime = System.nanoTime();\n");
        result.append("double runTestDuration = (endRunTestTime - startRunTestTime) / 1000000.0;\n");
        result.append("writeDataToFile(runTestDuration + \"===\" + output, \"core-engine/cfg/src/main/java/data/testDriverData/runTestDriverData.txt\", true);\n");
        result.append("}\n");
        return result.toString();
    }

    private static String generateUtilities(MethodDeclaration method, List<String> stubVariableDeclarations, ASTHelper.Coverage coverage) {
        StringBuilder result = new StringBuilder();

        // Generate mark method
        result.append(markMethodUtility);

        // Generate writeDataToFile method
        result.append(writeDataToFileUtility);

        // Generate testing method with instruments
        result.append(createCloneMethod(method, stubVariableDeclarations, coverage));

        return result.toString();
    }

    private static String generateUtilities(MethodDeclaration method, ASTHelper.Coverage coverage) {
        StringBuilder result = new StringBuilder();

        // Generate mark method
        result.append(markMethodUtility);

        // Generate writeDataToFile method
        result.append(writeDataToFileUtility);

        // Generate testing method with instruments
        result.append(createCloneMethod(method, coverage));

        return result.toString();
    }

    private static String createCloneMethod(MethodDeclaration method, List<String> stubVariableDeclarations, ASTHelper.Coverage coverage) {
        StringBuilder cloneMethod = new StringBuilder();

        cloneMethod.append("public static ").append(method.getReturnType2()).append(" ").append(method.getName()).append("(");
        List<ASTNode> parameters = method.parameters();
        for (int i = 0; i < parameters.size(); i++) {
            cloneMethod.append(parameters.get(i));
            if (i != parameters.size() - 1) cloneMethod.append(", ");
        }

        for (String stubVariableDeclaration : stubVariableDeclarations) {
            cloneMethod.append(", ");
            cloneMethod.append(stubVariableDeclaration);
        }
        cloneMethod.append(")\n");

        cloneMethod.append(generateCodeForBlock(method.getBody(), coverage)).append("\n");

        return cloneMethod.toString();
    }

    private static String createCloneMethod(MethodDeclaration method, ASTHelper.Coverage coverage) {
        StringBuilder cloneMethod = new StringBuilder();

        cloneMethod.append("public static ").append(method.getReturnType2()).append(" ").append(method.getName()).append("(");
        List<ASTNode> parameters = method.parameters();
        for (int i = 0; i < parameters.size(); i++) {
            cloneMethod.append(parameters.get(i));
            if (i != parameters.size() - 1) cloneMethod.append(", ");
        }
        cloneMethod.append(")\n");

        cloneMethod.append(generateCodeForBlock(method.getBody(), coverage)).append("\n");

        return cloneMethod.toString();
    }

    private static String generateCodeForOneStatement(ASTNode statement, String markMethodSeparator, ASTHelper.Coverage coverage) {
        if (statement == null) {
            return "";
        }

        if (statement instanceof Block) {
            return generateCodeForBlock((Block) statement, coverage);
        } else if (statement instanceof IfStatement) {
            return generateCodeForIfStatement((IfStatement) statement, coverage);
        } else if (statement instanceof ForStatement) {
            return generateCodeForForStatement((ForStatement) statement, coverage);
        } else if (statement instanceof WhileStatement) {
            return generateCodeForWhileStatement((WhileStatement) statement, coverage);
        } else if (statement instanceof DoStatement) {
            return generateCodeForDoStatement((DoStatement) statement, coverage);
        } else {
            return generateCodeForNormalStatement(statement, markMethodSeparator);
        }

    }

    private static String generateCodeForBlock(Block block, ASTHelper.Coverage coverage) {
        StringBuilder result = new StringBuilder();
        List<ASTNode> statements = block.statements();

        result.append("{\n");
        for (int i = 0; i < statements.size(); i++) {
            result.append(generateCodeForOneStatement(statements.get(i), ";", coverage));
        }
        result.append("}\n");

        return result.toString();
    }

    private static String generateCodeForIfStatement(IfStatement ifStatement, ASTHelper.Coverage coverage) {
        StringBuilder result = new StringBuilder();

        result.append("if (").append(generateCodeForCondition(ifStatement.getExpression(), coverage)).append(")\n");
        result.append("{\n");
        result.append(generateCodeForOneStatement(ifStatement.getThenStatement(), ";", coverage));
        result.append("}\n");


        String elseCode = generateCodeForOneStatement(ifStatement.getElseStatement(), ";", coverage);
        if (!elseCode.equals("")) {
            result.append("else {\n").append(elseCode).append("}\n");
        }

        return result.toString();
    }

    private static String generateCodeForForStatement(ForStatement forStatement, ASTHelper.Coverage coverage) {
        StringBuilder result = new StringBuilder();

        // Initializers
        List<ASTNode> initializers = forStatement.initializers();
        for (ASTNode initializer : initializers) {
            result.append(generateCodeForMarkMethod(initializer, ";"));
        }
        result.append("for (");
        for (int i = 0; i < initializers.size(); i++) {
            result.append(replaceMethodInvocationWithStubVar(initializers.get(i)));
            if (i != initializers.size() - 1) result.append(", ");
        }

        // Condition
        result.append("; ");
        result.append(generateCodeForCondition(forStatement.getExpression(), coverage));

        // Updaters
        result.append("; ");
        List<ASTNode> updaters = forStatement.updaters();
        for (int i = 0; i < updaters.size(); i++) {
            result.append(generateCodeForOneStatement(updaters.get(i), ",", coverage));
            if (i != updaters.size() - 1) result.append(", ");
        }

        // Body
        result.append(") {\n");
        result.append(generateCodeForOneStatement(forStatement.getBody(), ";", coverage));
        result.append("}\n");

        return result.toString();
    }

    private static String generateCodeForWhileStatement(WhileStatement whileStatement, ASTHelper.Coverage coverage) {
        StringBuilder result = new StringBuilder();

        // Condition
        result.append("while (");
        result.append(generateCodeForCondition(whileStatement.getExpression(), coverage));
        result.append(") {\n");

        result.append(generateCodeForOneStatement(whileStatement.getBody(), ";", coverage));
        result.append("}\n");

        return result.toString();
    }

    private static String generateCodeForDoStatement(DoStatement doStatement, ASTHelper.Coverage coverage) {
        StringBuilder result = new StringBuilder();

        // Do body
        result.append("do {");
        result.append(generateCodeForOneStatement(doStatement.getBody(), ";", coverage));
        result.append("}\n");

        // Condition
        result.append("while (");
        result.append(generateCodeForCondition(doStatement.getExpression(), coverage));
        result.append(");\n");

        return result.toString();
    }

    private static String generateCodeForNormalStatement(ASTNode statement, String markMethodSeparator) {
        StringBuilder result = new StringBuilder();

        result.append(generateCodeForMarkMethod(statement, markMethodSeparator));
        result.append(replaceMethodInvocationWithStubVar(statement));

        return result.toString();
    }

    private static String replaceMethodInvocationWithStubVar(ASTNode statement) {
        if (!stubVariablesOrigins.isEmpty() && !stubVariableNames.isEmpty()) {
            String replacement = statement.toString().replace(
                    stubVariablesOrigins.peek(),
                    stubVariableNames.peek()
            );
            if (!replacement.equals(statement.toString())) {
                stubVariablesOrigins.poll();
                stubVariableNames.poll();
            }
            return replacement;
        } else return statement.toString();
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

        result.append("mark(\"").append(newStatement).append("\", false, false)").append(markMethodSeparator).append("\n");

        return result.toString();
    }

    private static String generateCodeForCondition(Expression condition, ASTHelper.Coverage coverage) {
        if (coverage == ASTHelper.Coverage.MCDC) {
            return generateCodeForConditionForMCDCCoverage(condition);
        } else if (coverage == ASTHelper.Coverage.BRANCH || coverage == ASTHelper.Coverage.STATEMENT) {
            return generateCodeForConditionForBranchAndStatementCoverage(condition);
        } else {
            throw new RuntimeException("Invalid coverage!");
        }
    }

    private static String generateCodeForConditionForBranchAndStatementCoverage(Expression condition) {
        String tmpCondition = replaceMethodInvocationWithStubVar(condition);
        return "((" + tmpCondition + ") && mark(\"" + condition + "\", true, false))" +
                " || mark(\"" + condition + "\", false, true)";
    }

    private static String generateCodeForConditionForMCDCCoverage(Expression condition) {
        StringBuilder result = new StringBuilder();

        if (condition instanceof InfixExpression && isSeparableOperator(((InfixExpression) condition).getOperator())) {
            InfixExpression infixCondition = (InfixExpression) condition;

            result.append("(").append(generateCodeForConditionForMCDCCoverage(infixCondition.getLeftOperand())).append(") ").append(infixCondition.getOperator()).append(" (");
            result.append(generateCodeForConditionForMCDCCoverage(infixCondition.getRightOperand())).append(")");

            List<ASTNode> extendedOperands = infixCondition.extendedOperands();
            for (ASTNode operand : extendedOperands) {
                result.append(" ").append(infixCondition.getOperator()).append(" ");
                result.append("(").append(generateCodeForConditionForMCDCCoverage((Expression) operand)).append(")");
            }
        } else {
            result.append(generateCodeForConditionForBranchAndStatementCoverage(condition));
        }

        return result.toString();
    }

    private static boolean isSeparableOperator(InfixExpression.Operator operator) {
        return operator.equals(InfixExpression.Operator.CONDITIONAL_OR) ||
                operator.equals(InfixExpression.Operator.OR) ||
                operator.equals(InfixExpression.Operator.CONDITIONAL_AND) ||
                operator.equals(InfixExpression.Operator.AND);
    }
}
