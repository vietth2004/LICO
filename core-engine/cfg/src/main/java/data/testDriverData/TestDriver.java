package data.testDriverData;
import java.io.FileWriter;
public class TestDriver {
private static boolean mark(String statement, boolean isTrueCondition, boolean isFalseCondition) {
StringBuilder markResult = new StringBuilder();
markResult.append(statement).append("===");
markResult.append(isTrueCondition).append("===");
markResult.append(isFalseCondition).append("---end---");
writeDataToFile(markResult.toString(), "core-engine/cfg/src/main/java/data/testDriverData/runTestDriverData.txt", true);
if (!isTrueCondition && !isFalseCondition) return true;
return !isFalseCondition;
}
private static void writeDataToFile(String data, String path, boolean append) {
try {
FileWriter writer = new FileWriter(path, append);
writer.write(data);
writer.close();
} catch(Exception e) {
e.printStackTrace();
}
}
public static boolean isPrime(int x)
{
if (((x <= 1) && mark("x <= 1", true, false)) || mark("x <= 1", false, true))
{
mark("return false;\n", false, false);
return false;
}
mark("int i=2", false, false);
for (int i=2; ((i <= Math.sqrt(x)) && mark("i <= Math.sqrt(x)", true, false)) || mark("i <= Math.sqrt(x)", false, true); mark("i++", false, false),
i++) {
{
if (((x % i == 0) && mark("x % i == 0", true, false)) || mark("x % i == 0", false, true))
{
mark("return false;\n", false, false);
return false;
}
}
}
mark("return true;\n", false, false);
return true;
}

public static void main(String[] args) {
writeDataToFile("", "core-engine/cfg/src/main/java/data/testDriverData/runTestDriverData.txt", false);
long startRunTestTime = System.nanoTime();
Object output = isPrime(1);
long endRunTestTime = System.nanoTime();
double runTestDuration = (endRunTestTime - startRunTestTime) / 1000000.0;
writeDataToFile(runTestDuration + "===" + output, "core-engine/cfg/src/main/java/data/testDriverData/runTestDriverData.txt", true);
}
}