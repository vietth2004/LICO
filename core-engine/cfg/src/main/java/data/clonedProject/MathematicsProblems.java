package data.clonedProject;
import java.io.FileWriter;
public class MathematicsProblems {
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
public static boolean isThree(int n){
  int numOfDivisors=0;
  for (int i=1; i <= n; i++) {
    if (n % i == 0) {
      numOfDivisors++;
    }
    if (numOfDivisors > 3) {
      return false;
    }
  }
  return numOfDivisors == 3;
}
public static boolean isThree_clone(int n)
{
mark("int numOfDivisors=0;\n", false, false);
int numOfDivisors=0;
mark("int i=1", false, false);
for (int i=1; ((i <= n) && mark("i <= n", true, false)) || mark("i <= n", false, true); mark("i++", false, false),
i++) {
{
if (((n % i == 0) && mark("n % i == 0", true, false)) || mark("n % i == 0", false, true))
{
{
mark("numOfDivisors++;\n", false, false);
numOfDivisors++;
}
}
if (((numOfDivisors > 3) && mark("numOfDivisors > 3", true, false)) || mark("numOfDivisors > 3", false, true))
{
{
mark("return false;\n", false, false);
return false;
}
}
}
}
mark("return numOfDivisors == 3;\n", false, false);
return numOfDivisors == 3;
}

public static final int booleanisThreeintnTotalStatement = 9;
public static final int booleanisThreeintnTotalBranch = 6;
public static boolean isPerfectSquare(int num){
  if (num == 1) {
    return true;
  }
  long start=2;
  long end=num / 2;
  while (start <= end) {
    long mid=start + (end - start) / 2;
    long currSquare=mid * mid;
    if (currSquare == num) {
      return true;
    }
    if (currSquare > num) {
      end=mid - 1;
    }
 else {
      start=mid + 1;
    }
  }
  return false;
}
public static boolean isPerfectSquare_clone(int num)
{
if (((num == 1) && mark("num == 1", true, false)) || mark("num == 1", false, true))
{
{
mark("return true;\n", false, false);
return true;
}
}
mark("long start=2;\n", false, false);
long start=2;
mark("long end=num / 2;\n", false, false);
long end=num / 2;
while (((start <= end) && mark("start <= end", true, false)) || mark("start <= end", false, true)) {
{
mark("long mid=start + (end - start) / 2;\n", false, false);
long mid=start + (end - start) / 2;
mark("long currSquare=mid * mid;\n", false, false);
long currSquare=mid * mid;
if (((currSquare == num) && mark("currSquare == num", true, false)) || mark("currSquare == num", false, true))
{
{
mark("return true;\n", false, false);
return true;
}
}
if (((currSquare > num) && mark("currSquare > num", true, false)) || mark("currSquare > num", false, true))
{
{
mark("end=mid - 1;\n", false, false);
end=mid - 1;
}
}
else {
{
mark("start=mid + 1;\n", false, false);
start=mid + 1;
}
}
}
}
mark("return false;\n", false, false);
return false;
}

public static final int booleanisPerfectSquareintnumTotalStatement = 13;
public static final int booleanisPerfectSquareintnumTotalBranch = 8;
public static int countOdds(int low,int high){
  int mid=(high - low) / 2;
  if (low % 2 != 0 || high % 2 != 0)   return mid + 1;
 else   return mid;
}
public static int countOdds_clone(int low, int high)
{
mark("int mid=(high - low) / 2;\n", false, false);
int mid=(high - low) / 2;
if (((low % 2 != 0 || high % 2 != 0) && mark("low % 2 != 0 || high % 2 != 0", true, false)) || mark("low % 2 != 0 || high % 2 != 0", false, true))
{
mark("return mid + 1;\n", false, false);
return mid + 1;
}
else {
mark("return mid;\n", false, false);
return mid;
}
}

public static final int intcountOddsintlowinthighTotalStatement = 4;
public static final int intcountOddsintlowinthighTotalBranch = 2;
public static final int MathematicsProblemsTotalStatement = 26;
}
