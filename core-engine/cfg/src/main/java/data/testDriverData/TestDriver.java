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
public static int countCharacterTypes(char input, boolean isUpperCase_call_1, boolean isLowerCase_call_2, boolean isLetter_call_3, boolean isDigit_call_4)
{
mark("int uppercaseCount=0;\n", false, false);
int uppercaseCount=0;
mark("int lowercaseCount=0;\n", false, false);
int lowercaseCount=0;
mark("int specialCount=0;\n", false, false);
int specialCount=0;
if (((isUpperCase_call_1) && mark("isUpperCase_call_1", true, false)) || mark("isUpperCase_call_1", false, true))
{
{
mark("uppercaseCount++;\n", false, false);
uppercaseCount++;
}
}
else {
if (((isLowerCase_call_2) && mark("isLowerCase_call_2", true, false)) || mark("isLowerCase_call_2", false, true))
{
{
mark("lowercaseCount++;\n", false, false);
lowercaseCount++;
}
}
else {
if (((!isLetter_call_3 && !isDigit_call_4) && mark("!isLetter_call_3 && !isDigit_call_4", true, false)) || mark("!isLetter_call_3 && !isDigit_call_4", false, true))
{
{
mark("specialCount++;\n", false, false);
specialCount++;
}
}
}
}
mark("return uppercaseCount + lowercaseCount + specialCount;\n", false, false);
return uppercaseCount + lowercaseCount + specialCount;
}

public static void main(String[] args) {
writeDataToFile("", "core-engine/cfg/src/main/java/data/testDriverData/runTestDriverData.txt", false);
long startRunTestTime = System.nanoTime();
Object output = countCharacterTypes('', false, false, true, false);
long endRunTestTime = System.nanoTime();
double runTestDuration = (endRunTestTime - startRunTestTime) / 1000000.0;
writeDataToFile(runTestDuration + "===" + output, "core-engine/cfg/src/main/java/data/testDriverData/runTestDriverData.txt", true);
}
}