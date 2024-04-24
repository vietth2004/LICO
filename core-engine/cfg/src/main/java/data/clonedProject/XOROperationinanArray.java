package data.clonedProject;
import java.io.FileWriter;
public class XOROperationinanArray {
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
public static int xorOperation(int n, int start)
{
mark("int xor=start;\n", false, false);
int xor=start;
mark("int i=1", false, false);
for (int i=1; ((i < n) && mark("i < n", true, false)) || mark("i < n", false, true); mark("i++", false, false),
i++) {
{
mark("int nextNum=start + 2 * i;\n", false, false);
int nextNum=start + 2 * i;
mark("xor=xor ^ nextNum;\n", false, false);
xor=xor ^ nextNum;
}
}
mark("return xor;\n", false, false);
return xor;
}

public static final int intxorOperationintnintstartTotalStatement = 7;
public static final int intxorOperationintnintstartTotalBranch = 2;
public static final int XOROperationinanArrayTotalStatement = 7;
}
