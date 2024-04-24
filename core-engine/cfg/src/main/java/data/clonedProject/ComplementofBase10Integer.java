package data.clonedProject;
import java.io.FileWriter;
public class ComplementofBase10Integer {
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
public static int bitwiseComplement(int N)
{
if (((N == 0) && mark("N == 0", true, false)) || mark("N == 0", false, true))
{
{
mark("return 1;\n", false, false);
return 1;
}
}
mark("int todo=N;\n", false, false);
int todo=N;
mark("int bit=1;\n", false, false);
int bit=1;
while (((todo != 0) && mark("todo != 0", true, false)) || mark("todo != 0", false, true)) {
{
mark("N=N ^ bit;\n", false, false);
N=N ^ bit;
mark("bit=bit << 1;\n", false, false);
bit=bit << 1;
mark("todo=todo >> 1;\n", false, false);
todo=todo >> 1;
}
}
mark("return N;\n", false, false);
return N;
}

public static final int intbitwiseComplementintNTotalStatement = 9;
public static final int intbitwiseComplementintNTotalBranch = 4;
public static final int ComplementofBase10IntegerTotalStatement = 9;
}
