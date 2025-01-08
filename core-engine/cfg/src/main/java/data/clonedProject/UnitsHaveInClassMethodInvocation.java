package data.clonedProject;
import java.io.FileWriter;
public class UnitsHaveInClassMethodInvocation {
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
public static boolean isLeapYear(int year){
  if (year % 4 == 0) {
    if (year % 100 == 0) {
      if (year % 400 == 0)       return true;
 else       return false;
    }
 else {
      return true;
    }
  }
  return false;
}
public static boolean isLeapYear_clone(int year)
{
if (((year % 4 == 0) && mark("year % 4 == 0", true, false)) || mark("year % 4 == 0", false, true))
{
{
if (((year % 100 == 0) && mark("year % 100 == 0", true, false)) || mark("year % 100 == 0", false, true))
{
{
if (((year % 400 == 0) && mark("year % 400 == 0", true, false)) || mark("year % 400 == 0", false, true))
{
mark("return true;\n", false, false);
return true;
}
else {
mark("return false;\n", false, false);
return false;
}
}
}
else {
{
mark("return true;\n", false, false);
return true;
}
}
}
}
mark("return false;\n", false, false);
return false;
}

public static final int booleanisLeapYearintyearTotalStatement = 7;
public static final int booleanisLeapYearintyearTotalBranch = 6;
private static int getNumberOfDays(int year){
  if (isLeapYear(year))   return 366;
 else   return 365;
}
private static int getNumberOfDays_clone(int year)
{
if (((isLeapYear(year)) && mark("isLeapYear(year)", true, false)) || mark("isLeapYear(year)", false, true))
{
mark("return 366;\n", false, false);
return 366;
}
else {
mark("return 365;\n", false, false);
return 365;
}
}

public static final int intgetNumberOfDaysintyearTotalStatement = 3;
public static final int intgetNumberOfDaysintyearTotalBranch = 2;
public boolean isArmstrong(double N){
  int n=getLength(N);
  if (getNthPowerSum(N,n) == N)   return true;
 else   return false;
}
public boolean isArmstrong_clone(double N)
{
mark("int n=getLength(N);\n", false, false);
int n=getLength(N);
if (((getNthPowerSum(N,n) == N) && mark("getNthPowerSum(N,n) == N", true, false)) || mark("getNthPowerSum(N,n) == N", false, true))
{
mark("return true;\n", false, false);
return true;
}
else {
mark("return false;\n", false, false);
return false;
}
}

public static final int booleanisArmstrongdoubleNTotalStatement = 4;
public static final int booleanisArmstrongdoubleNTotalBranch = 2;
public static double pow(double a,double b){
  double res=1;
  for (int i=0; i < b; i++) {
    res*=a;
  }
  return res;
}
public static double pow_clone(double a, double b)
{
mark("double res=1;\n", false, false);
double res=1;
mark("int i=0", false, false);
for (int i=0; ((i < b) && mark("i < b", true, false)) || mark("i < b", false, true); mark("i++", false, false),
i++) {
{
mark("res*=a;\n", false, false);
res*=a;
}
}
mark("return res;\n", false, false);
return res;
}

public static final int doublepowdoubleadoublebTotalStatement = 6;
public static final int doublepowdoubleadoublebTotalBranch = 2;
public static double getNthPowerSum(double n,double p){
  double sum=0;
  while (n > 0) {
    double temp=n % 10;
    n/=10;
    sum+=pow(temp,p);
  }
  return sum;
}
public static double getNthPowerSum_clone(double n, double p)
{
mark("double sum=0;\n", false, false);
double sum=0;
while (((n > 0) && mark("n > 0", true, false)) || mark("n > 0", false, true)) {
{
mark("double temp=n % 10;\n", false, false);
double temp=n % 10;
mark("n/=10;\n", false, false);
n/=10;
mark("sum+=pow(temp,p);\n", false, false);
sum+=pow(temp,p);
}
}
mark("return sum;\n", false, false);
return sum;
}

public static final int doublegetNthPowerSumdoublendoublepTotalStatement = 6;
public static final int doublegetNthPowerSumdoublendoublepTotalBranch = 2;
public static int getLength(double n){
  int count=0;
  while (n > 0) {
    n/=10;
    count++;
  }
  return count;
}
public static int getLength_clone(double n)
{
mark("int count=0;\n", false, false);
int count=0;
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

public static final int intgetLengthdoublenTotalStatement = 5;
public static final int intgetLengthdoublenTotalBranch = 2;
public static final int UnitsHaveInClassMethodInvocationTotalStatement = 31;
}
