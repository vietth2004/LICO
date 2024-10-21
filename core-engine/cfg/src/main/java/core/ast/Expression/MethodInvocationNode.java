package core.ast.Expression;

import core.ast.AstNode;
import core.ast.Expression.Name.SimpleNameNode;
import core.ast.VariableDeclaration.SingleVariableDeclarationNode;
import core.symbolicExecution.MemoryModel;
import core.testDriver.TestDriverUtils;
import core.testGeneration.TestGeneration;
import org.eclipse.jdt.core.dom.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MethodInvocationNode extends ExpressionNode {
    private static int numberOfFunctionsCall = 1;

    public static AstNode executeMethodInvocation(MethodInvocation methodInvocation, MemoryModel memoryModel) {
        String methodName = methodInvocation.getName().toString();

        if (methodInvocation.getExpression() == null) { // method invocation in the same class
            MethodDeclaration methodDeclaration = getInvokedMethodAST(methodName);
            return declareStubVariable(methodName, methodDeclaration, memoryModel);
        } else { // method invocation outside the class or in libs
            Class<?> invokedMethodReturnClass = getInvokedMethodReturnClass(methodInvocation, memoryModel);
            return declareStubVariable(methodName, invokedMethodReturnClass, memoryModel);
        }
    }

    private static MethodDeclaration getInvokedMethodAST(String methodName) {
        ArrayList<ASTNode> funcAstNodeList = TestGeneration.getFuncAstNodeList();
        for (ASTNode astNode : funcAstNodeList) {
            if (((MethodDeclaration) astNode).getName().getIdentifier().equals(methodName)) {
                return (MethodDeclaration) astNode;
            }
        }
        throw new RuntimeException("There is no method named: " + methodName);
    }

    private static Class<?> getInvokedMethodReturnClass(MethodInvocation methodInvocation, MemoryModel memoryModel) {
        CompilationUnit compilationUnit = TestGeneration.getCompilationUnit();
        String optionalExpression = methodInvocation.getExpression().toString();

        for (ASTNode iImport : (List<ASTNode>) compilationUnit.imports()) {
            ImportDeclaration importDeclaration = (ImportDeclaration) iImport;
            String importName = importDeclaration.getName().toString();
            if (importName.contains(optionalExpression)) {
                Class<?>[] classes = TestDriverUtils.getVariableClasses(methodInvocation.arguments(), memoryModel);
                try {
                    Method invokedMethodReflect = Class.forName(importName).getDeclaredMethod(methodInvocation.getName().toString(), classes);
                    return invokedMethodReflect.getReturnType();
                } catch (NoSuchMethodException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }


        Class<?>[] classes = TestDriverUtils.getVariableClasses(methodInvocation.arguments(), memoryModel);
        try {
            Method invokedMethodReflect = Class.forName("java.lang." + optionalExpression).getDeclaredMethod(methodInvocation.getName().toString(), classes);
            return invokedMethodReflect.getReturnType();
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    private static AstNode declareStubVariable(String methodName, MethodDeclaration methodDeclaration, MemoryModel memoryModel) {
        Type funcReturnType = methodDeclaration.getReturnType2();
        String stubName = methodName + "_call_" + numberOfFunctionsCall;
        numberOfFunctionsCall++;
        SimpleNameNode stubNameNode = new SimpleNameNode(stubName);

        if (funcReturnType instanceof PrimitiveType) {
            memoryModel.declarePrimitiveTypeVariable(((PrimitiveType) funcReturnType), stubName, stubNameNode);
            return stubNameNode;
        } else if (funcReturnType instanceof ArrayType) {
            ArrayType arrayType = (ArrayType) funcReturnType;
            AstNode arrayNode = SingleVariableDeclarationNode.createMultiDimensionsInitializationArray(stubName, 0, arrayType.getDimensions(), arrayType.getElementType(), memoryModel);
            memoryModel.declareArrayTypeVariable(arrayType, stubName, arrayType.getDimensions(), arrayNode);
            return arrayNode;
        } else { // OTHER TYPES
            throw new RuntimeException("Invalid type");
        }
    }

    private static AstNode declareStubVariable(String methodName, Class<?> invokedMethodReturnClass, MemoryModel memoryModel) {
        String stubName = methodName + "_call_" + numberOfFunctionsCall;
        numberOfFunctionsCall++;
        SimpleNameNode stubNameNode = new SimpleNameNode(stubName);

        if (invokedMethodReturnClass.isPrimitive()) {
            memoryModel.declarePrimitiveTypeVariable(TestDriverUtils.getPrimitiveCode(invokedMethodReturnClass), stubName, stubNameNode);
            return stubNameNode;
        } else if (invokedMethodReturnClass.isArray()) {

            throw new RuntimeException("Haven't handled array type");
//            ArrayType arrayType = (ArrayType) funcReturnType;
//            AstNode arrayNode = SingleVariableDeclarationNode.createMultiDimensionsInitializationArray(stubName, 0, arrayType.getDimensions(), arrayType.getElementType(), memoryModel);
//            memoryModel.declareArrayTypeVariable(arrayType, stubName, arrayType.getDimensions(), arrayNode);
//            return arrayNode;
        } else { // OTHER TYPES
            throw new RuntimeException("Invalid type");
        }
    }
}
