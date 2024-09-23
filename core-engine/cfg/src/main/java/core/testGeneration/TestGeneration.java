package core.testGeneration;

import core.cfg.CfgEndBlockNode;
import core.cfg.CfgNode;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestGeneration {
    public enum Coverage {
        STATEMENT,
        BRANCH,
        MCDC,
        PATH
    }

    protected static CompilationUnit compilationUnit;
    protected static ArrayList<ASTNode> funcAstNodeList;
    protected static CfgNode cfgBeginNode;
    protected static CfgEndBlockNode cfgEndNode;
    protected static List<ASTNode> parameters;
    protected static Class<?>[] parameterClasses;
    protected static List<String> parameterNames;
    protected static ASTNode testFunc;

    public static ArrayList<ASTNode> getFuncAstNodeList() {
        return funcAstNodeList;
    }
}
