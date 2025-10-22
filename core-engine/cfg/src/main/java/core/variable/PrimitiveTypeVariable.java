package core.variable;

import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import core.symbolicExecution.SymbolicExecutionRewrite;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.Type;

public class PrimitiveTypeVariable extends Variable {
    private PrimitiveType primitiveType;

    public PrimitiveTypeVariable(PrimitiveType primitiveType, String name) {
        this.primitiveType = primitiveType;
        super.setName(name);
    }

    public static Expr createZ3PrimitiveTypeVariable(PrimitiveTypeVariable primitiveTypeVariable, Context ctx) {
        PrimitiveType.Code code = primitiveTypeVariable.getCode();
        String name = primitiveTypeVariable.getName();

        int bitSize;

        if (code.equals(PrimitiveType.BYTE)) {
            bitSize = 8;
        } else if (code.equals(PrimitiveType.SHORT)) {
            bitSize = 16;
        } else if (code.equals(PrimitiveType.CHAR)) {
            bitSize = 16; // unsigned
        } else if (code.equals(PrimitiveType.INT)) {
            bitSize = 32;
        } else if (code.equals(PrimitiveType.LONG)) {
            bitSize = 64;
        } else if (code.equals(PrimitiveType.FLOAT)) {
            bitSize = 32; // biểu diễn không chính xác dấu phẩy
        } else if (code.equals(PrimitiveType.DOUBLE)) {
            bitSize = 64; // biểu diễn không chính xác dấu phẩy
        } else if (code.equals(PrimitiveType.BOOLEAN)) {
            return ctx.mkBoolConst(name);
        } else {
            throw new RuntimeException("Invalid type: " + code);
        }
        SymbolicExecutionRewrite.variableTypeMap.put(name, code);

        // Tạo BitVecExpr theo số bit tương ứng
        return ctx.mkBVConst(name, bitSize);
    }

    public PrimitiveType.Code getCode() {
        return primitiveType.getPrimitiveTypeCode();
    }

    @Override
    public Type getType() {
        return this.primitiveType;
    }
}
