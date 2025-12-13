package core.ast.Expression.OperationExpression;

import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import core.Z3Vars.Z3VariableWrapper;
import core.ast.AstNode;
import core.ast.Expression.ExpressionNode;
import core.ast.Expression.Literal.LiteralNode;
import core.ast.Expression.Literal.NumberLiteral.IntegerLiteralNode;
import core.ast.Expression.Literal.NumberLiteral.NumberLiteralNode;
import core.ast.Expression.Name.NameNode;
import core.ast.Expression.Name.SimpleNameNode;
import core.symbolicExecution.MemoryModel;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.InfixExpression;


import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class PostfixExpressionNode extends OperationExpressionNode {
    private ExpressionNode operand;
    private PostfixExpression.Operator operator;

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
        Expression operand = postfixExpression.getOperand();
        if (operand instanceof SimpleName) {
            postfixExpressionNode.operand = SimpleNameNode.executeSimpleName((SimpleName) operand);
        } else {
            //Xem lai
            postfixExpressionNode.operand = (ExpressionNode) ExpressionNode.executeExpression(postfixExpression.getOperand(), memoryModel);
        }
        postfixExpressionNode.operator = postfixExpression.getOperator();

        ExpressionNode expressionNode = executePostfixExpressionNode(postfixExpressionNode, memoryModel);
        return expressionNode;
    }

    public static ExpressionNode executePostfixExpressionNode(PostfixExpressionNode postfixExpressionNode, MemoryModel memoryModel) {
        ExpressionNode operand = postfixExpressionNode.operand;
        PostfixExpression.Operator operator = postfixExpressionNode.operator;

        ExpressionNode oldOperand = postfixExpressionNode.operand;

        if(!operand.isLiteralNode()) {
            postfixExpressionNode.operand = OperationExpressionNode.executeOperandNode(operand, memoryModel);
        } else {
            return operand;
        }
        // PAUSE executing

        // RE-ASSIGN
        if(operand instanceof NameNode) {
            String key = NameNode.getStringNameNode((NameNode) operand);
            AstNode value = memoryModel.getValue(key);

            if (value instanceof NameNode) {
                InfixExpressionNode expr = new InfixExpressionNode();
                
                if (operator == PostfixExpression.Operator.INCREMENT) {
                    expr.setOperator(InfixExpression.Operator.PLUS);
                } else if (operator == PostfixExpression.Operator.DECREMENT) {
                    expr.setOperator(InfixExpression.Operator.MINUS);
                } else {
                    throw new IllegalStateException(
                            "Unsupported postfix operator (only ++/-- supported): " + operator);
                }

                expr.setLeftOperand((ExpressionNode) value);

                IntegerLiteralNode one = new IntegerLiteralNode();
                one.setTokenValue(1);
                expr.setRightOperand(one);
                //InfixExpressionNode.executeInfixExpression((InfixExpression) expression, memoryModel);


                memoryModel.assignVariable(key, expr);
            } else if (value instanceof InfixExpressionNode) {

                if (operator != PostfixExpression.Operator.INCREMENT
                        && operator != PostfixExpression.Operator.DECREMENT) {
                    throw new IllegalStateException(
                            "Unsupported postfix operator on infix expression: " + operator);
                }

                InfixExpressionNode inf = (InfixExpressionNode) value;

                if (!(inf.getRightOperand() instanceof IntegerLiteralNode)) {
                    throw new IllegalStateException(
                            "Right operand is not IntegerLiteralNode: " + inf.getRightOperand());
                }

                IntegerLiteralNode kNode = (IntegerLiteralNode) inf.getRightOperand();
                int k = kNode.getIntegerValue();

                if (operator == PostfixExpression.Operator.INCREMENT) {
                    k++;
                } else {
                    k--;
                }

                IntegerLiteralNode newK = new IntegerLiteralNode();
                newK.setTokenValue(k);
                inf.setRightOperand(newK);

                memoryModel.assignVariable(key, inf);
            } else if(value instanceof LiteralNode) {
                memoryModel.assignVariable(key, LiteralNode.analyzeOnePostfixLiteral((LiteralNode) value, operator));
            } else if (value instanceof OperationExpressionNode) {
                PostfixExpressionNode newValue = new PostfixExpressionNode();
                newValue.operator = operator;
                newValue.operand = (OperationExpressionNode) value;
                memoryModel.assignVariable(key, newValue);
            }
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
