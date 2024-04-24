package data.clonedProject;
import java.io.FileWriter;
public class Numberof1bits {
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
public static int hammingWeight(int n)
{
mark("int bits=0;\n", false, false);
int bits=0;
mark("int mask=1;\n", false, false);
int mask=1;
mark("int i=0", false, false);
for (int i=0; ((i < 32) && mark("i < 32", true, false)) || mark("i < 32", false, true); mark("i++", false, false),
i++) {
{
if ((((n & mask) != 0) && mark("(n & mask) != 0", true, false)) || mark("(n & mask) != 0", false, true))
{
{
mark("bits++;\n", false, false);
bits++;
}
}
mark("mask<<=1;\n", false, false);
mask<<=1;
}
}
mark("return bits;\n", false, false);
return bits;
}

public static final int inthammingWeightintnTotalStatement = 9;
public static final int inthammingWeightintnTotalBranch = 4;
public static final int Numberof1bitsTotalStatement = 9;
}
