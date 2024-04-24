package data.clonedProject;
import java.io.FileWriter;
public class ThreeDivisors {
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
public static boolean isThree(int n)
{
mark("int numOfDivisors=0;\n", false, false);
int numOfDivisors=0;
mark("int i=1", false, false);
for (int i=1; ((i <= n) && mark("i <= n", true, false)) || mark("i <= n", false, true); mark("i++", false, false),
i++) {
{
if (((n % i == 0) && mark("n % i == 0", true, false)) || mark("n % i == 0", false, true))
{
{
mark("numOfDivisors++;\n", false, false);
numOfDivisors++;
}
}
if (((numOfDivisors > 3) && mark("numOfDivisors > 3", true, false)) || mark("numOfDivisors > 3", false, true))
{
{
mark("return false;\n", false, false);
return false;
}
}
}
}
mark("return numOfDivisors == 3;\n", false, false);
return numOfDivisors == 3;
}

public static final int booleanisThreeintnTotalStatement = 9;
public static final int booleanisThreeintnTotalBranch = 6;
public static final int ThreeDivisorsTotalStatement = 9;
}
