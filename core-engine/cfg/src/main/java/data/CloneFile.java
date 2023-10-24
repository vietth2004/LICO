package data;
import static core.dataStructure.MarkedPath.markOneStatement;
public class CloneFile {
public static int abs(int x)
{
if (((x < 0) && markOneStatement("x < 0", true, false)) || markOneStatement("x < 0", false, true))
{
markOneStatement("return -x;\n", false, false);
return -x;
}
else {
markOneStatement("return x;\n", false, false);
return x;
}
}
}
