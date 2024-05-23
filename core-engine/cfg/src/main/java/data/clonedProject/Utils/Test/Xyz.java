package data.clonedProject.Utils.Test;
import java.io.FileWriter;
public class Xyz {
private static void writeDataToFile(String data, String path, boolean append) {
try {
FileWriter writer = new FileWriter(path, append);
writer.write(data);
writer.close();
} catch (Exception e) {
e.printStackTrace();
}
}
private static boolean mark(String statement, boolean isTrueCondition, boolean isFalseCondition) {
StringBuilder markResult = new StringBuilder();
markResult.append(statement).append("===");
markResult.append(isTrueCondition).append("===");
markResult.append(isFalseCondition).append("---end---");
writeDataToFile(markResult.toString(), "core-engine/cfg/src/main/java/data/testDriverData/runTestDriverData.txt", true);
if (!isTrueCondition && !isFalseCondition) return true;
return !isFalseCondition;
}
private boolean leapYear(int year)
{
if (((year % 4 == 0) && mark("year % 4 == 0", true, false)) || mark("year % 4 == 0", false, true))
{
{
if (((year % 100 == 0) && mark("year % 100 == 0", true, false)) || mark("year % 100 == 0", false, true))
{
{
if (((year % 400 == 0) && mark("year % 400 == 0", true, false)) || mark("year % 400 == 0", false, true))
{
mark("return true;\n", false, false);
return true;
}
else {
mark("return false;\n", false, false);
return false;
}
}
}
else {
mark("return true;\n", false, false);
return true;
}
}
}
else {
mark("return false;\n", false, false);
return false;
}
}

public static final int booleanleapYearintyearTotalStatement = 7;
public static final int booleanleapYearintyearTotalBranch = 6;
public static int abs(int x)
{
if (((x < 0) && mark("x < 0", true, false)) || mark("x < 0", false, true))
{
mark("return -x;\n", false, false);
return -x;
}
else {
mark("return x;\n", false, false);
return x;
}
}

public static final int intabsintxTotalStatement = 3;
public static final int intabsintxTotalBranch = 2;
public static final int XyzTotalStatement = 10;
}
