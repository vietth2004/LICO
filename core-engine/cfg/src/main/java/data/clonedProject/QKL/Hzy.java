package data.clonedProject.QKL;
import java.io.FileWriter;
public class Hzy {
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
public int factorial(int n)
{
mark("int result=1;\n", false, false);
int result=1;
mark("int i=1", false, false);
for (int i=1; ((i <= n) && mark("i <= n", true, false)) || mark("i <= n", false, true); mark("i=i + 1", false, false),
i=i + 1) {
{
mark("result*=i;\n", false, false);
result*=i;
}
}
mark("return result;\n", false, false);
return result;
}

public static final int intfactorialintnTotalStatement = 6;
public static final int intfactorialintnTotalBranch = 2;
public static final int HzyTotalStatement = 6;
}
