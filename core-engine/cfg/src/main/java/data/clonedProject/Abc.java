package data.clonedProject;
import java.io.FileWriter;
public class Abc {
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
public static double intPow(int[] arr, double number, int index)
{
mark("int[] x=new int[10];\n", false, false);
int[] x=new int[10];
mark("double result=1;\n", false, false);
double result=1;
if (((index == 0) && mark("index == 0", true, false)) || mark("index == 0", false, true))
{
mark("return 1;\n", false, false);
return 1;
}
else {
if (((index < 0) && mark("index < 0", true, false)) || mark("index < 0", false, true))
{
{
mark("int i=0", false, false);
for (int i=0; ((i < -index) && mark("i < -index", true, false)) || mark("i < -index", false, true); mark("i++", false, false),
i++) {
{
mark("result*=number;\n", false, false);
result*=number;
}
}
mark("return 1 / result;\n", false, false);
return 1 / result;
}
}
else {
{
mark("int i=0", false, false);
for (int i=0; ((i < index) && mark("i < index", true, false)) || mark("i < index", false, true); mark("i++", false, false),
i++) {
{
mark("result*=number;\n", false, false);
result*=number;
}
}
mark("return result;\n", false, false);
return result;
}
}
}
}

public static final int doubleintPowintarrdoublenumberintindexTotalStatement = 15;
public static final int doubleintPowintarrdoublenumberintindexTotalBranch = 8;
public static final int AbcTotalStatement = 15;
}
