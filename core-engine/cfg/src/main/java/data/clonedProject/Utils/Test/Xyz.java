package data.clonedProject.Utils.Test;
import core.dataStructure.MarkedPath;
public class Xyz {
public static boolean leapYear(int year)
{
if (((year % 4 == 0) && MarkedPath.markOneStatement("year % 4 == 0", true, false)) || MarkedPath.markOneStatement("year % 4 == 0", false, true))
{
{
if (((year % 100 == 0) && MarkedPath.markOneStatement("year % 100 == 0", true, false)) || MarkedPath.markOneStatement("year % 100 == 0", false, true))
{
{
if (((year % 400 == 0) && MarkedPath.markOneStatement("year % 400 == 0", true, false)) || MarkedPath.markOneStatement("year % 400 == 0", false, true))
{
MarkedPath.markOneStatement("return true;\n", false, false);
return true;
}
else {
MarkedPath.markOneStatement("return false;\n", false, false);
return false;
}
}
}
else {
MarkedPath.markOneStatement("return true;\n", false, false);
return true;
}
}
}
else {
MarkedPath.markOneStatement("return false;\n", false, false);
return false;
}
}

public static int abs(int x)
{
if (((x < 0) && MarkedPath.markOneStatement("x < 0", true, false)) || MarkedPath.markOneStatement("x < 0", false, true))
{
MarkedPath.markOneStatement("return -x;\n", false, false);
return -x;
}
else {
MarkedPath.markOneStatement("return x;\n", false, false);
return x;
}
}

}
