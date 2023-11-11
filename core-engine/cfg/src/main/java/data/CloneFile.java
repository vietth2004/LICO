package data;
import static core.dataStructure.MarkedPath.markOneStatement;
public class CloneFile {
public static double intPow(double number, int index)
{
markOneStatement("double result=1;\n", false, false);
double result=1;
if (((index == 0) && markOneStatement("index == 0", true, false)) || markOneStatement("index == 0", false, true))
{
markOneStatement("return 1;\n", false, false);
return 1;
}
else {
if (((index < 0) && markOneStatement("index < 0", true, false)) || markOneStatement("index < 0", false, true))
{
{
markOneStatement("int i=0", false, false);
for (int i=0; ((i < -index) && markOneStatement("i < -index", true, false)) || markOneStatement("i < -index", false, true); markOneStatement("i++", false, false),
i++) {
{
markOneStatement("result*=number;\n", false, false);
result*=number;
}
}
markOneStatement("return 1 / result;\n", false, false);
return 1 / result;
}
}
else {
{
markOneStatement("int i=0", false, false);
for (int i=0; ((i < index) && markOneStatement("i < index", true, false)) || markOneStatement("i < index", false, true); markOneStatement("i++", false, false),
i++) {
{
markOneStatement("result*=number;\n", false, false);
result*=number;
}
}
markOneStatement("return result;\n", false, false);
return result;
}
}
}
}
}
