package data.clonedProject.QKL;
import core.dataStructure.MarkedPath;
public class Hzy {
public static int factorial(int n)
{
MarkedPath.markOneStatement("int result=1;\n", false, false);
int result=1;
MarkedPath.markOneStatement("int i=1", false, false);
for (int i=1; ((i <= n) && MarkedPath.markOneStatement("i <= n", true, false)) || MarkedPath.markOneStatement("i <= n", false, true); MarkedPath.markOneStatement("i=i + 1", false, false),
i=i + 1) {
{
MarkedPath.markOneStatement("result*=i;\n", false, false);
result*=i;
}
}
MarkedPath.markOneStatement("return result;\n", false, false);
return result;
}

}
