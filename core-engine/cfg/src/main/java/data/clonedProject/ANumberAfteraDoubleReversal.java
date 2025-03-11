package data.clonedProject;
import java.io.FileWriter;
public class ANumberAfteraDoubleReversal {
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
public static boolean isSameAfterReversals(int num){
  if (num <= 9) {
    return true;
  }
  if (num % 10 == 0) {
    return false;
  }
  return true;
}
public static boolean isSameAfterReversals_clone(int num)
{
if (((num <= 9) && mark("num <= 9", true, false)) || mark("num <= 9", false, true))
{
{
mark("return true;\n", false, false);
return true;
}
}
if (((num % 10 == 0) && mark("num % 10 == 0", true, false)) || mark("num % 10 == 0", false, true))
{
{
mark("return false;\n", false, false);
return false;
}
}
mark("return true;\n", false, false);
return true;
}

public static final int booleanisSameAfterReversalsintnumTotalStatement = 5;
public static final int booleanisSameAfterReversalsintnumTotalBranch = 4;
public static final int ANumberAfteraDoubleReversalTotalStatement = 5;
}
