package core.ast.Statement;

import core.symbolicExecution.SymbolicExecution;
import org.eclipse.jdt.core.dom.*;

public class WhileStatementNode extends StatementNode {
    public static void replaceMethodInvocationWithStub(WhileStatement originWhileStatement, MethodInvocation originMethodInvocation, ASTNode replacement) {
        if (originWhileStatement.getExpression() == originMethodInvocation){
            originWhileStatement.setExpression((Expression) replacement);
            SymbolicExecution.getCurrentCfgNode().setAst(replacement);
        }
        // body
    }
}
