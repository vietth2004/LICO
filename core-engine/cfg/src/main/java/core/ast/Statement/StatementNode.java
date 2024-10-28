package core.ast.Statement;

import core.ast.AstNode;
import core.ast.Expression.ExpressionNode;
import core.symbolicExecution.MemoryModel;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public abstract class StatementNode extends AstNode {
    private String optionalLeadingComment = null; // ???

    public static AstNode executeStatement(Statement statement, MemoryModel memoryModel) {
        if(statement instanceof VariableDeclarationStatement) {
            VariableDeclarationStatementNode.executeVariableDeclarationStatement((VariableDeclarationStatement) statement, memoryModel);
            return null; /*???*/
        } else if (statement instanceof ExpressionStatement) {
            return ExpressionStatementNode.executeExpressionStatement((ExpressionStatement) statement, memoryModel);
        } else if (statement instanceof ReturnStatement) {
//            ReturnStatement returnStatement = (ReturnStatement) statement;
//            ExpressionNode.executeExpression(returnStatement.getExpression(), memoryModel);
            return null;
        } else {
//            throw new RuntimeException(statement.getClass() + " is not a Statement");
            return null;
        }
    }
}
