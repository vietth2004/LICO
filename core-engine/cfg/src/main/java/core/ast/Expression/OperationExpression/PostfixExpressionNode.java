package core.ast.Expression.OperationExpression;

import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import core.Z3Vars.Z3VariableWrapper;
import core.ast.AstNode;
import core.ast.Expression.ExpressionNode;
import core.ast.Expression.Literal.LiteralNode;
import core.ast.Expression.Name.NameNode;
import core.ast.Expression.Name.SimpleNameNode;
import core.symbolicExecution.MemoryModel;
import org.eclipse.jdt.core.dom.*;

import java.util.List;

public class PostfixExpressionNode extends OperationExpressionNode {
    private ExpressionNode operand;
    private PostfixExpression.Operator operator;
    private Expression originalOperand;

    public static void replaceMethodInvocationWithStub(PostfixExpression originPostfixExpression, MethodInvocation originMethodInvocation, ASTNode replacement) {
        Expression operand = originPostfixExpression.getOperand();
        if (operand == originMethodInvocation)
            originPostfixExpression.setOperand((Expression) replacement);
    }

    public static Expr createZ3Expression(PostfixExpressionNode postfixExpressionNode, Context ctx, List<Z3VariableWrapper> vars, MemoryModel memoryModel) {
        ExpressionNode operand = postfixExpressionNode.operand;
        PostfixExpression.Operator operator = postfixExpressionNode.operator;

        Expr Z3Operand = OperationExpressionNode.createZ3Expression(operand, ctx, vars, memoryModel);
        return Z3Operand;
    }

    public static ExpressionNode executePostfixExpression(PostfixExpression postfixExpression, MemoryModel memoryModel) {
        PostfixExpressionNode postfixExpressionNode = new PostfixExpressionNode();
        postfixExpressionNode.originalOperand = postfixExpression.getOperand();
        postfixExpressionNode.operand = (ExpressionNode) ExpressionNode.executeExpression(postfixExpression.getOperand(), memoryModel);
        postfixExpressionNode.operator = postfixExpression.getOperator();

        ExpressionNode expressionNode = executePostfixExpressionNode(postfixExpressionNode, memoryModel);
        return expressionNode;
    }

    public static ExpressionNode executePostfixExpressionNode(PostfixExpressionNode postfixExpressionNode, MemoryModel memoryModel) {
        ExpressionNode operand = postfixExpressionNode.operand;
        PostfixExpression.Operator operator = postfixExpressionNode.operator;

        // PAUSE executing

        // RE-ASSIGN
            String key = postfixExpressionNode.originalOperand.toString();
            AstNode value = memoryModel.getValue(key);

            if(value instanceof LiteralNode) {
                memoryModel.assignVariable(key, LiteralNode.analyzeOnePostfixLiteral((LiteralNode) value, operator));
            } else if (value instanceof OperationExpressionNode) {
                PostfixExpressionNode newValue = new PostfixExpressionNode();
                newValue.operator = operator;
                newValue.operand = (OperationExpressionNode) value;
                memoryModel.assignVariable(key, newValue);
            }

        // CONTINUE executing
//        if(oldOperand != postfixExpressionNode.operand) {
//            return executePostfixExpressionNode(postfixExpressionNode, memoryModel);
//        } else {
//            return postfixExpressionNode;
//        }
        return postfixExpressionNode;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("");
        result.append(operand.toString());
        result.append(operator.toString());

        return result.toString();
    }
}
