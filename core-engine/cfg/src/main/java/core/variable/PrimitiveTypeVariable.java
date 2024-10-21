package core.variable;

import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.Type;

public class PrimitiveTypeVariable extends Variable {
    private PrimitiveType.Code code;
    private PrimitiveType primitiveType;

    public PrimitiveTypeVariable(PrimitiveType primitiveType, String name) {
//        this.code = type.getPrimitiveTypeCode();
        this.primitiveType = primitiveType;
        super.setName(name);
    }

    public PrimitiveTypeVariable(PrimitiveType.Code code, String name) {
        this.code = code;
        this.primitiveType = primitiveType;
        super.setName(name);
    }

    public static Expr createZ3PrimitiveTypeVariable(PrimitiveTypeVariable primitiveTypeVariable, Context ctx) {
        PrimitiveType.Code code = primitiveTypeVariable.getCode();
        String name = primitiveTypeVariable.getName();

        if (code.equals(PrimitiveType.INT) ||
                code.equals(PrimitiveType.LONG) ||
                code.equals(PrimitiveType.SHORT) ||
                code.equals(PrimitiveType.BYTE) ||
                code.equals(PrimitiveType.CHAR)) {
            return ctx.mkIntConst(name);
        } else if (code.equals(PrimitiveType.DOUBLE) ||
                code.equals(PrimitiveType.FLOAT)) {
            return ctx.mkRealConst(name);
        } else if (code.equals(PrimitiveType.BOOLEAN)) {
            return ctx.mkBoolConst(name);
        } else {
            throw new RuntimeException("Invalid type");
        }
    }

    public PrimitiveType.Code getCode() {
        if (code == null) return primitiveType.getPrimitiveTypeCode();
        else return code;
    }

    @Override
    public Type getType() {
        return this.primitiveType;
    }
}
