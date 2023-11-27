package data;

import static core.dataStructure.MarkedPath.markOneStatement;

public class CloneFile {
    public static int fibonacci(int n) {
        markOneStatement("int a=0, b=1, c, i;\n", false, false);
        int a = 0, b = 1, c, i;
        if (((n == 0) && markOneStatement("n == 0", true, false)) || markOneStatement("n == 0", false, true)) {
            markOneStatement("return a;\n", false, false);
            return a;
        }
        markOneStatement("i=2", false, false);
        for (i = 2; ((i <= n) && markOneStatement("i <= n", true, false)) || markOneStatement("i <= n", false, true); markOneStatement("i++", false, false),
                i++) {
            {
                markOneStatement("c=a + b;\n", false, false);
                c = a + b;
                markOneStatement("a=b;\n", false, false);
                a = b;
                markOneStatement("b=c;\n", false, false);
                b = c;
            }
        }
        markOneStatement("return b;\n", false, false);
        return b;
    }
}
