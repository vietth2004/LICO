package data.clonedProject;
import java.io.FileWriter;
public class DummyTel {
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
public static double calculateTotalCost(int startTime, int callDuration)
{
mark("double resultCost=0.5 * callDuration;\n", false, false);
double resultCost=0.5 * callDuration;
if (((startTime >= 18) && mark("startTime >= 18", true, false)) || mark("startTime >= 18", false, true))
{
{
mark("resultCost=resultCost / 2;\n", false, false);
resultCost=resultCost / 2;
}
}
else {
if (((startTime < 8) && mark("startTime < 8", true, false)) || mark("startTime < 8", false, true))
{
{
mark("resultCost=resultCost / 2;\n", false, false);
resultCost=resultCost / 2;
}
}
}
if (((callDuration > 60) && mark("callDuration > 60", true, false)) || mark("callDuration > 60", false, true))
{
{
mark("resultCost-=(resultCost * 15.0 / 100);\n", false, false);
resultCost-=(resultCost * 15.0 / 100);
}
}
mark("resultCost+=(resultCost * 5.0 / 100);\n", false, false);
resultCost+=(resultCost * 5.0 / 100);
mark("return resultCost;\n", false, false);
return resultCost;
}

public static final int doublecalculateTotalCostintstartTimeintcallDurationTotalStatement = 9;
public static final int doublecalculateTotalCostintstartTimeintcallDurationTotalBranch = 6;
public static final int DummyTelTotalStatement = 9;
}
