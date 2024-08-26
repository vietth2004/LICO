package data.clonedProject.utils;
import java.io.FileWriter;
public class Math {
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
public static double intPow(double number, int index)
{
mark("double result=1;\n", false, false);
double result=1;
if (((index == 0) && mark("index == 0", true, false)) || mark("index == 0", false, true))
{
mark("return 1;\n", false, false);
return 1;
}
else {
if (((index < 0) && mark("index < 0", true, false)) || mark("index < 0", false, true))
{
{
mark("int i=0", false, false);
for (int i=0; ((i < -index) && mark("i < -index", true, false)) || mark("i < -index", false, true); mark("i++", false, false),
i++) {
{
mark("result*=number;\n", false, false);
result*=number;
}
}
mark("return 1 / result;\n", false, false);
return 1 / result;
}
}
else {
{
mark("int i=0", false, false);
for (int i=0; ((i < index) && mark("i < index", true, false)) || mark("i < index", false, true); mark("i++", false, false),
i++) {
{
mark("result*=number;\n", false, false);
result*=number;
}
}
mark("return result;\n", false, false);
return result;
}
}
}
}

public static final int doubleintPowdoublenumberintindexTotalStatement = 14;
public static final int doubleintPowdoublenumberintindexTotalBranch = 8;
public static int abs(int x)
{
if (((x < 0) && mark("x < 0", true, false)) || mark("x < 0", false, true))
{
mark("return -x;\n", false, false);
return -x;
}
else {
mark("return x;\n", false, false);
return x;
}
}

public static final int intabsintxTotalStatement = 3;
public static final int intabsintxTotalBranch = 2;
public int factorial(int n)
{
mark("int result=1;\n", false, false);
int result=1;
mark("int i=1", false, false);
for (int i=1; ((i <= n) && mark("i <= n", true, false)) || mark("i <= n", false, true); mark("i=i + 1", false, false),
i=i + 1) {
{
mark("result*=i;\n", false, false);
result*=i;
}
}
mark("return result;\n", false, false);
return result;
}

public static final int intfactorialintnTotalStatement = 6;
public static final int intfactorialintnTotalBranch = 2;
public static boolean isPrime(int n)
{
if (((n <= 1) && mark("n <= 1", true, false)) || mark("n <= 1", false, true))
{
mark("return false;\n", false, false);
return false;
}
mark("int i=2", false, false);
for (int i=2; ((i <= n / 2) && mark("i <= n / 2", true, false)) || mark("i <= n / 2", false, true); mark("i++", false, false),
i++) {
if (((n % i == 0) && mark("n % i == 0", true, false)) || mark("n % i == 0", false, true))
{
mark("return false;\n", false, false);
return false;
}
}
mark("return true;\n", false, false);
return true;
}

public static final int booleanisPrimeintnTotalStatement = 8;
public static final int booleanisPrimeintnTotalBranch = 6;
public boolean isPerfectNumber(int number)
{
if (((number <= 0) && mark("number <= 0", true, false)) || mark("number <= 0", false, true))
{
{
mark("return false;\n", false, false);
return false;
}
}
mark("int sum=0;\n", false, false);
int sum=0;
mark("int i=1", false, false);
for (int i=1; ((i < number) && mark("i < number", true, false)) || mark("i < number", false, true); mark("i++", false, false),
i++) {
{
if (((number % i == 0) && mark("number % i == 0", true, false)) || mark("number % i == 0", false, true))
{
{
mark("sum+=i;\n", false, false);
sum+=i;
}
}
}
}
mark("return sum == number;\n", false, false);
return sum == number;
}

public static final int booleanisPerfectNumberintnumberTotalStatement = 9;
public static final int booleanisPerfectNumberintnumberTotalBranch = 6;
public static int findGCD(int x, int y)
{
mark("int gcd=1;\n", false, false);
int gcd=1;
mark("int i=1", false, false);
for (int i=1; ((i <= x && i <= y) && mark("i <= x && i <= y", true, false)) || mark("i <= x && i <= y", false, true); mark("i++", false, false),
i++) {
{
if (((x % i == 0 && y % i == 0) && mark("x % i == 0 && y % i == 0", true, false)) || mark("x % i == 0 && y % i == 0", false, true))
{
mark("gcd=i;\n", false, false);
gcd=i;
}
}
}
mark("return gcd;\n", false, false);
return gcd;
}

public static final int intfindGCDintxintyTotalStatement = 7;
public static final int intfindGCDintxintyTotalBranch = 4;
public static final int MathTotalStatement = 47;
}
