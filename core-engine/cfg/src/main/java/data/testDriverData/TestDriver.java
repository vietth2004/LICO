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
public static int getFare(int age, int distance)
{
mark("int fare=0;\n", false, false);
int fare=0;
if (((age > 4 && age < 14) && mark("age > 4 && age < 14", true, false)) || mark("age > 4 && age < 14", false, true))
{
{
if (((distance >= 10) && mark("distance >= 10", true, false)) || mark("distance >= 10", false, true))
{
{
mark("fare=130;\n", false, false);
fare=130;
}
}
else {
{
mark("fare=100;\n", false, false);
fare=100;
}
}
}
}
else {
if (((age > 15) && mark("age > 15", true, false)) || mark("age > 15", false, true))
{
{
if (((distance < 10 && age >= 60) && mark("distance < 10 && age >= 60", true, false)) || mark("distance < 10 && age >= 60", false, true))
{
{
mark("fare=160;\n", false, false);
fare=160;
}
}
else {
if (((distance > 10 && age < 60) && mark("distance > 10 && age < 60", true, false)) || mark("distance > 10 && age < 60", false, true))
{
{
mark("fare=250;\n", false, false);
fare=250;
}
}
else {
{
mark("fare=200;\n", false, false);
fare=200;
}
}
}
}
}
}
mark("return fare;\n", false, false);
return fare;
}

public static void main(String[] args) {
writeDataToFile("", "core-engine/cfg/src/main/java/data/testDriverData/runTestDriverData.txt", false);
long startRunTestTime = System.nanoTime();
Object output = getFare(5, 10);
long endRunTestTime = System.nanoTime();
double runTestDuration = (endRunTestTime - startRunTestTime) / 1000000.0;
writeDataToFile(runTestDuration + "===" + output, "core-engine/cfg/src/main/java/data/testDriverData/runTestDriverData.txt", true);
}
}