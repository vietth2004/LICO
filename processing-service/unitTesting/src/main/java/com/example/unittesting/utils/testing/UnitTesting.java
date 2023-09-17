package com.example.unittesting.utils.testing;

import com.example.unittesting.ast.Node.Parameter;
import com.example.unittesting.model.DataTest;
import com.example.unittesting.model.result.NormalResult.RunResult;
import core.dataStructure.MarkedPath;
import core.parser.ProjectParser;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static core.testDriver.Utils.*;

public final class UnitTesting {

    private UnitTesting() {}

    public static RunResult runTest(String path, String methodName, DataTest testData) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // set "return" parameter value thành giá trị chạy hàm cần chạy

        // Clone 1 hàm để có thể chạy được và thực hiện phân tích độ phủ như bên unit testing.

        RunResult runResult = new RunResult();

        // Parse File
        ArrayList<ASTNode> funcAstNodeList = ProjectParser.parseFile(path);
        CompilationUnit compilationUnit = ProjectParser.parseFileToCompilationUnit(path);

        for (ASTNode func : funcAstNodeList) {
            if (((MethodDeclaration) func).getName().getIdentifier().equals(methodName)) {
                createCloneMethod((MethodDeclaration) func, compilationUnit);

                List<ASTNode> parameters = ((MethodDeclaration) func).parameters();
                Class<?>[] parameterClasses = getParameterClasses(parameters);
                Method method = Class.forName("data.CloneFile").getDeclaredMethod(methodName, parameterClasses);
                List<Parameter> parameterList = testData.getInfoMethod().getParameters();

                Object actualOutput = method.invoke(parameterClasses, extractParameterData(parameterList));
                runResult.setCoveredStatements(MarkedPath.getMarkedStatementsStringList());

                String expectedOutput = parameterList.get(parameterList.size() - 1).getValue(); // Return parameter

                runResult.setMatchExpectedOutput(actualOutput.toString().equals(expectedOutput));

                break;
            }
        }

        return runResult;
    }

    private static Object[] extractParameterData(List<Parameter> parameters) {
        Object[] result = new Object[parameters.size()];

        // Phần tử cuối là return.
        for (int i = 0; i < parameters.size() - 1; i++) {
            Parameter parameter = parameters.get(i);

            String className = parameter.getDescribe();

            if ("int".equals(className)) {
                result[i] = Integer.parseInt(parameter.getValue());
            } else if ("boolean".equals(className)) {
                result[i] = Boolean.parseBoolean(parameter.getValue());
            } else if ("byte".equals(className)) {
                result[i] = Byte.parseByte(parameter.getValue());
            } else if ("short".equals(className)) {
                result[i] = Short.parseShort(parameter.getValue());
            } else if ("char".equals(className)) {
                if(parameter.getValue().length() != 1) {
                    throw new RuntimeException("Invalid character");
                }
                result[i] = parameter.getValue().charAt(0);
            } else if ("long".equals(className)) {
                result[i] = Long.parseLong(parameter.getValue());
            } else if ("float".equals(className)) {
                result[i] = Float.parseFloat(parameter.getValue());
            } else if ("double".equals(className)) {
                result[i] = Double.parseDouble(parameter.getValue());
            } else if ("void".equals(className)) {
                result[i] = null;
            } else {
                throw new RuntimeException("Unsupported type: " + className);
            }
        }

        return result;
    }

}
