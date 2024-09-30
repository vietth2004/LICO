package core.ast.Expression;

import core.ast.AstNode;
import core.ast.Expression.Literal.NumberLiteral.IntegerLiteralNode;
import core.ast.Expression.Name.SimpleNameNode;
import core.ast.VariableDeclaration.SingleVariableDeclarationNode;
import core.symbolicExecution.MemoryModel;
import core.testDriver.TestDriverUtils;
import core.testGeneration.TestGeneration;
import org.eclipse.jdt.core.dom.*;

import java.lang.reflect.InvocationTargetException;
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
            CompilationUnit compilationUnit = TestGeneration.getCompilationUnit();

            for (ASTNode iImport : (List<ASTNode>) compilationUnit.imports()) {
                ImportDeclaration importDeclaration = (ImportDeclaration) iImport;
                System.out.println("abc");
            }

            Class<?>[] classes = TestDriverUtils.getVariableClasses(methodInvocation.arguments(), memoryModel);
        }

        return null;
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

    private static AstNode declareStubVariable(String methodName, MethodDeclaration methodDeclaration, MemoryModel memoryModel) {
        Type funcReturnType = methodDeclaration.getReturnType2();
        String stubName = methodName + "_call_" + numberOfFunctionsCall;
        numberOfFunctionsCall++;
        SimpleNameNode stubNameNode = new SimpleNameNode(stubName);

        if(funcReturnType instanceof PrimitiveType) {
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
}
