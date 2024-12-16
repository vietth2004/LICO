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
public static double easeOutBounce(double x)
{
mark("double n1=7.5625;\n", false, false);
double n1=7.5625;
mark("double d1=2.75;\n", false, false);
double d1=2.75;
if (((x < 1 / d1) && mark("x < 1 / d1", true, false)) || mark("x < 1 / d1", false, true))
{
mark("return n1 * x * x;\n", false, false);
return n1 * x * x;
}
if (((x < 2 / d1) && mark("x < 2 / d1", true, false)) || mark("x < 2 / d1", false, true))
{
{
mark("return n1 * (x-=1.5 / d1) * x + 0.75;\n", false, false);
return n1 * (x-=1.5 / d1) * x + 0.75;
}
}
if (((x < 2.5 / d1) && mark("x < 2.5 / d1", true, false)) || mark("x < 2.5 / d1", false, true))
{
{
mark("return n1 * (x-=2.25 / d1) * x + 0.9375;\n", false, false);
return n1 * (x-=2.25 / d1) * x + 0.9375;
}
}
mark("return n1 * (x-=2.625 / d1) * x + 0.984375;\n", false, false);
return n1 * (x-=2.625 / d1) * x + 0.984375;
}

public static void main(String[] args) {
writeDataToFile("", "core-engine/cfg/src/main/java/data/testDriverData/runTestDriverData.txt", false);
long startRunTestTime = System.nanoTime();
Object output = easeOutBounce(0.0);
long endRunTestTime = System.nanoTime();
double runTestDuration = (endRunTestTime - startRunTestTime) / 1000000.0;
writeDataToFile(runTestDuration + "===" + output, "core-engine/cfg/src/main/java/data/testDriverData/runTestDriverData.txt", true);
}
}