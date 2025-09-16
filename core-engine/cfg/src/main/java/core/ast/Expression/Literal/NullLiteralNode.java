package core.ast.Expression.Literal;

import core.ast.Expression.Literal.LiteralNode;

public class NullLiteralNode extends LiteralNode {
    @Override
    public String toString() {
        return "null";
    }

    @Override
    public LiteralNode copy() {
        return new NullLiteralNode();
    }
}
