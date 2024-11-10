package core.ast.Statement;

import core.symbolicExecution.SymbolicExecution;
import org.eclipse.jdt.core.dom.*;

public class IfStatementNode extends StatementNode {
    public static void replaceMethodInvocationWithStub(IfStatement originIfStatement, MethodInvocation originMethodInvocation, ASTNode replacement) {
        if (originIfStatement.getExpression() == originMethodInvocation){
            originIfStatement.setExpression((Expression) replacement);
            SymbolicExecution.getCurrentCfgNode().setAst(replacement);
        }
        // then statement
        // else statement
    }
}
