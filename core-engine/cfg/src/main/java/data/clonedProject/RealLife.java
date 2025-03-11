package data.clonedProject;
import java.io.FileWriter;
public class RealLife {
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
public static int arrangeCoins(int n){
  long left=1;
  long right=n;
  while (left <= right) {
    long mid=(left + right) / 2;
    long sum=mid * (mid + 1) / 2;
    if (sum == n) {
      return (int)mid;
    }
 else     if (sum > n) {
      right=mid - 1;
    }
 else {
      left=mid + 1;
    }
  }
  return (int)right;
}
public static int arrangeCoins_clone(int n)
{
mark("long left=1;\n", false, false);
long left=1;
mark("long right=n;\n", false, false);
long right=n;
while (((left <= right) && mark("left <= right", true, false)) || mark("left <= right", false, true)) {
{
mark("long mid=(left + right) / 2;\n", false, false);
long mid=(left + right) / 2;
mark("long sum=mid * (mid + 1) / 2;\n", false, false);
long sum=mid * (mid + 1) / 2;
if (((sum == n) && mark("sum == n", true, false)) || mark("sum == n", false, true))
{
{
mark("return (int)mid;\n", false, false);
return (int)mid;
}
}
else {
if (((sum > n) && mark("sum > n", true, false)) || mark("sum > n", false, true))
{
{
mark("right=mid - 1;\n", false, false);
right=mid - 1;
}
}
else {
{
mark("left=mid + 1;\n", false, false);
left=mid + 1;
}
}
}
}
}
mark("return (int)right;\n", false, false);
return (int)right;
}

public static final int intarrangeCoinsintnTotalStatement = 11;
public static final int intarrangeCoinsintnTotalBranch = 6;
public static int findDelayedArrivalTime(int arrivalTime,int delayedTime){
  int newTime=arrivalTime + delayedTime;
  if (newTime >= 24)   return newTime - 24;
 else   return newTime;
}
public static int findDelayedArrivalTime_clone(int arrivalTime, int delayedTime)
{
mark("int newTime=arrivalTime + delayedTime;\n", false, false);
int newTime=arrivalTime + delayedTime;
if (((newTime >= 24) && mark("newTime >= 24", true, false)) || mark("newTime >= 24", false, true))
{
mark("return newTime - 24;\n", false, false);
return newTime - 24;
}
else {
mark("return newTime;\n", false, false);
return newTime;
}
}

public static final int intfindDelayedArrivalTimeintarrivalTimeintdelayedTimeTotalStatement = 4;
public static final int intfindDelayedArrivalTimeintarrivalTimeintdelayedTimeTotalBranch = 2;
public static final int RealLifeTotalStatement = 15;
}
