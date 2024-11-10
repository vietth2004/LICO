package core.ast.Statement;

import core.symbolicExecution.SymbolicExecution;
import org.eclipse.jdt.core.dom.*;

public class DoStatementNode extends StatementNode {
    public static void replaceMethodInvocationWithStub(DoStatement originDoStatement, MethodInvocation originMethodInvocation, ASTNode replacement) {
        if (originDoStatement.getExpression() == originMethodInvocation){
            originDoStatement.setExpression((Expression) replacement);
            SymbolicExecution.getCurrentCfgNode().setAst(replacement);
        }
        // body
    }
}
