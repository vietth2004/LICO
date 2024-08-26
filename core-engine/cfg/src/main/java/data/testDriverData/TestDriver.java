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
public static int test(int a, int b)
{
mark("int j=0;\n", false, false);
int j=0;
do {{
mark("System.out.println(\"abc\");\n", false, false);
System.out.println("abc");
mark("j++;\n", false, false);
j++;
}
}
while (((((j > a) && mark("j > a", true, false)) || mark("j > a", false, true)) && (((j < b) && mark("j < b", true, false)) || mark("j < b", false, true))) || (((j > 18) && mark("j > 18", true, false)) || mark("j > 18", false, true)));
if (((((a > 2) && mark("a > 2", true, false)) || mark("a > 2", false, true)) && (((b < 5) && mark("b < 5", true, false)) || mark("b < 5", false, true))) || (((a > 18) && mark("a > 18", true, false)) || mark("a > 18", false, true)))
{
{
mark("System.out.println(\"abc\");\n", false, false);
System.out.println("abc");
}
}
mark("int i=0", false, false);
for (int i=0; ((((i < a) && mark("i < a", true, false)) || mark("i < a", false, true)) && (((i > b) && mark("i > b", true, false)) || mark("i > b", false, true))) || (((i > 12) && mark("i > 12", true, false)) || mark("i > 12", false, true)); mark("i=i + 1", false, false),
i=i + 1) {
{
mark("System.out.println(\"abc\");\n", false, false);
System.out.println("abc");
}
}
mark("return 3;\n", false, false);
return 3;
}

public static void main(String[] args) {
writeDataToFile("", "core-engine/cfg/src/main/java/data/testDriverData/runTestDriverData.txt", false);
long startRunTestTime = System.nanoTime();
Object output = test(8, 8);
long endRunTestTime = System.nanoTime();
double runTestDuration = (endRunTestTime - startRunTestTime) / 1000000.0;
writeDataToFile(runTestDuration + "===" + output, "core-engine/cfg/src/main/java/data/testDriverData/runTestDriverData.txt", true);
}
}