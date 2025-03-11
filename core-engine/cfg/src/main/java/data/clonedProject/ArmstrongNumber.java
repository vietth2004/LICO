package data.clonedProject;
import java.io.FileWriter;
public class ArmstrongNumber {
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
public static boolean isArmstrong(double N){
  double n=getLength(N);
  return getNthPowerSum(N,n) == N;
}
public static boolean isArmstrong_clone(double N)
{
mark("double n=getLength(N);\n", false, false);
double n=getLength(N);
mark("return getNthPowerSum(N,n) == N;\n", false, false);
return getNthPowerSum(N,n) == N;
}

public static final int booleanisArmstrongdoubleNTotalStatement = 2;
public static final int booleanisArmstrongdoubleNTotalBranch = 0;
public static double getNthPowerSum(double n,double p){
  double sum=0.0;
  while (n > 0) {
    double temp=n % 10;
    n=n / 10;
    sum+=Math.pow(temp,p);
  }
  return sum;
}
public static double getNthPowerSum_clone(double n, double p)
{
mark("double sum=0.0;\n", false, false);
double sum=0.0;
while (((n > 0) && mark("n > 0", true, false)) || mark("n > 0", false, true)) {
{
mark("double temp=n % 10;\n", false, false);
double temp=n % 10;
mark("n=n / 10;\n", false, false);
n=n / 10;
mark("sum+=Math.pow(temp,p);\n", false, false);
sum+=Math.pow(temp,p);
}
}
mark("return sum;\n", false, false);
return sum;
}

public static final int doublegetNthPowerSumdoublendoublepTotalStatement = 6;
public static final int doublegetNthPowerSumdoublendoublepTotalBranch = 2;
public static double getLength(double n){
  double count=0;
  while (n > 0) {
    n/=10;
    count++;
  }
  return count;
}
public static double getLength_clone(double n)
{
mark("double count=0;\n", false, false);
double count=0;
while (((n > 0) && mark("n > 0", true, false)) || mark("n > 0", false, true)) {
{
mark("n/=10;\n", false, false);
n/=10;
mark("count++;\n", false, false);
count++;
}
}
mark("return count;\n", false, false);
return count;
}

public static final int doublegetLengthdoublenTotalStatement = 5;
public static final int doublegetLengthdoublenTotalBranch = 2;
public static final int ArmstrongNumberTotalStatement = 13;
}
