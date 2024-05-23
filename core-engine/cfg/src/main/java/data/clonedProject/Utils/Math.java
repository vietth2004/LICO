package data.clonedProject.Utils;
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
private boolean leapYear(int year)
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
mark("return true;\n", false, false);
return true;
}
}
}
else {
mark("return false;\n", false, false);
return false;
}
}

public static final int booleanleapYearintyearTotalStatement = 7;
public static final int booleanleapYearintyearTotalBranch = 6;
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
public static int fibonacci(int n)
{
mark("int a=0, b=1, c, i;\n", false, false);
int a=0, b=1, c, i;
if (((n == 0) && mark("n == 0", true, false)) || mark("n == 0", false, true))
{
mark("return a;\n", false, false);
return a;
}
mark("i=2", false, false);
for (i=2; ((i <= n) && mark("i <= n", true, false)) || mark("i <= n", false, true); mark("i++", false, false),
i++) {
{
mark("c=a + b;\n", false, false);
c=a + b;
mark("a=b;\n", false, false);
a=b;
mark("b=c;\n", false, false);
b=c;
}
}
mark("return b;\n", false, false);
return b;
}

public static final int intfibonacciintnTotalStatement = 10;
public static final int intfibonacciintnTotalBranch = 4;
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
public int foo(int a, int b, int c)
{
if (((a > 10) && mark("a > 10", true, false)) || mark("a > 10", false, true))
{
{
if (((c < 20) && mark("c < 20", true, false)) || mark("c < 20", false, true))
{
{
mark("int i=0", false, false);
for (int i=0; ((i < b) && mark("i < b", true, false)) || mark("i < b", false, true); mark("i=i + 1", false, false),
i=i + 1) {
{
mark("System.out.println(\"hello\");\n", false, false);
System.out.println("hello");
}
}
if (((b < -5) && mark("b < -5", true, false)) || mark("b < -5", false, true))
{
{
mark("System.out.println(\"bye\");\n", false, false);
System.out.println("bye");
}
}
}
}
else {
{
mark("return a + b + c;\n", false, false);
return a + b + c;
}
}
}
}
else {
{
if (((a + b - c == 10) && mark("a + b - c == 10", true, false)) || mark("a + b - c == 10", false, true))
{
{
mark("System.out.println(\"hehe\");\n", false, false);
System.out.println("hehe");
}
}
else {
if (((a > b) && mark("a > b", true, false)) || mark("a > b", false, true))
{
{
mark("System.out.println(\"hoo\");\n", false, false);
System.out.println("hoo");
}
}
}
}
}
mark("return 5;\n", false, false);
return 5;
}

public static final int intfoointaintbintcTotalStatement = 14;
public static final int intfoointaintbintcTotalBranch = 12;
public int randomSum(int a, int b)
{
if (((a == b) && mark("a == b", true, false)) || mark("a == b", false, true))
{
mark("return 0;\n", false, false);
return 0;
}
if (((a > b) && mark("a > b", true, false)) || mark("a > b", false, true))
{
{
mark("return 1;\n", false, false);
return 1;
}
}
else {
if (((a < b) && mark("a < b", true, false)) || mark("a < b", false, true))
{
{
mark("return -1;\n", false, false);
return -1;
}
}
}
mark("return 2;\n", false, false);
return 2;
}

public static final int intrandomSumintaintbTotalStatement = 7;
public static final int intrandomSumintaintbTotalBranch = 6;
public static final int MathTotalStatement = 78;
}
