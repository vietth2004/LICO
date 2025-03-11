package data.clonedProject;
import java.util.stream.IntStream;
import java.io.FileWriter;
public class CountIntegersWithEvenDigitSum {
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
public static int countEven(int num){
  return (int)IntStream.range(1,num + 1).boxed().filter(CountIntegersWithEvenDigitSum::isSumOfDigitsEven).count();
}
public static int countEven_clone(int num)
{
mark("return (int)IntStream.range(1,num + 1).boxed().filter(CountIntegersWithEvenDigitSum::isSumOfDigitsEven).count();\n", false, false);
return (int)IntStream.range(1,num + 1).boxed().filter(CountIntegersWithEvenDigitSum::isSumOfDigitsEven).count();
}

public static final int intcountEvenintnumTotalStatement = 1;
public static final int intcountEvenintnumTotalBranch = 0;
public static boolean isSumOfDigitsEven(int num){
  int sum=0;
  while (num > 0) {
    sum+=num % 10;
    num/=10;
  }
  return sum % 2 == 0;
}
public static boolean isSumOfDigitsEven_clone(int num)
{
mark("int sum=0;\n", false, false);
int sum=0;
while (((num > 0) && mark("num > 0", true, false)) || mark("num > 0", false, true)) {
{
mark("sum+=num % 10;\n", false, false);
sum+=num % 10;
mark("num/=10;\n", false, false);
num/=10;
}
}
mark("return sum % 2 == 0;\n", false, false);
return sum % 2 == 0;
}

public static final int booleanisSumOfDigitsEvenintnumTotalStatement = 5;
public static final int booleanisSumOfDigitsEvenintnumTotalBranch = 2;
public static final int CountIntegersWithEvenDigitSumTotalStatement = 6;
}
