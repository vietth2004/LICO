package data.clonedProject;
import java.io.FileWriter;
public class ValidPerfectSquare {
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
public static boolean isPerfectSquare(int num)
{
if (((num == 1) && mark("num == 1", true, false)) || mark("num == 1", false, true))
{
{
mark("return true;\n", false, false);
return true;
}
}
mark("long start=2;\n", false, false);
long start=2;
mark("long end=num / 2;\n", false, false);
long end=num / 2;
while (((start <= end) && mark("start <= end", true, false)) || mark("start <= end", false, true)) {
{
mark("long mid=start + (end - start) / 2;\n", false, false);
long mid=start + (end - start) / 2;
mark("long currSquare=mid * mid;\n", false, false);
long currSquare=mid * mid;
if (((currSquare == num) && mark("currSquare == num", true, false)) || mark("currSquare == num", false, true))
{
{
mark("return true;\n", false, false);
return true;
}
}
if (((currSquare > num) && mark("currSquare > num", true, false)) || mark("currSquare > num", false, true))
{
{
mark("end=mid - 1;\n", false, false);
end=mid - 1;
}
}
else {
{
mark("start=mid + 1;\n", false, false);
start=mid + 1;
}
}
}
}
mark("return false;\n", false, false);
return false;
}

public static final int booleanisPerfectSquareintnumTotalStatement = 13;
public static final int booleanisPerfectSquareintnumTotalBranch = 8;
public static final int ValidPerfectSquareTotalStatement = 13;
}
