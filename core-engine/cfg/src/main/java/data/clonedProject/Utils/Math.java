package data.clonedProject.Utils;
import core.dataStructure.MarkedPath;
public class Math {
public static double intPow(double number, int index)
{
MarkedPath.markOneStatement("double result=1;\n", false, false);
double result=1;
if (((index == 0) && MarkedPath.markOneStatement("index == 0", true, false)) || MarkedPath.markOneStatement("index == 0", false, true))
{
MarkedPath.markOneStatement("return 1;\n", false, false);
return 1;
}
else {
if (((index < 0) && MarkedPath.markOneStatement("index < 0", true, false)) || MarkedPath.markOneStatement("index < 0", false, true))
{
{
MarkedPath.markOneStatement("int i=0", false, false);
for (int i=0; ((i < -index) && MarkedPath.markOneStatement("i < -index", true, false)) || MarkedPath.markOneStatement("i < -index", false, true); MarkedPath.markOneStatement("i++", false, false),
i++) {
{
MarkedPath.markOneStatement("result*=number;\n", false, false);
result*=number;
}
}
MarkedPath.markOneStatement("return 1 / result;\n", false, false);
return 1 / result;
}
}
else {
{
MarkedPath.markOneStatement("int i=0", false, false);
for (int i=0; ((i < index) && MarkedPath.markOneStatement("i < index", true, false)) || MarkedPath.markOneStatement("i < index", false, true); MarkedPath.markOneStatement("i++", false, false),
i++) {
{
MarkedPath.markOneStatement("result*=number;\n", false, false);
result*=number;
}
}
MarkedPath.markOneStatement("return result;\n", false, false);
return result;
}
}
}
}

public static final int doubleintPowdoublenumberintindexTotalStatement = 14;
public static final int doubleintPowdoublenumberintindexTotalBranch = 8;
public static boolean leapYear(int year)
{
if (((year % 4 == 0) && MarkedPath.markOneStatement("year % 4 == 0", true, false)) || MarkedPath.markOneStatement("year % 4 == 0", false, true))
{
{
if (((year % 100 == 0) && MarkedPath.markOneStatement("year % 100 == 0", true, false)) || MarkedPath.markOneStatement("year % 100 == 0", false, true))
{
{
if (((year % 400 == 0) && MarkedPath.markOneStatement("year % 400 == 0", true, false)) || MarkedPath.markOneStatement("year % 400 == 0", false, true))
{
MarkedPath.markOneStatement("return true;\n", false, false);
return true;
}
else {
MarkedPath.markOneStatement("return false;\n", false, false);
return false;
}
}
}
else {
MarkedPath.markOneStatement("return true;\n", false, false);
return true;
}
}
}
else {
MarkedPath.markOneStatement("return false;\n", false, false);
return false;
}
}

public static final int booleanleapYearintyearTotalStatement = 7;
public static final int booleanleapYearintyearTotalBranch = 6;
public static int abs(int x)
{
if (((x < 0) && MarkedPath.markOneStatement("x < 0", true, false)) || MarkedPath.markOneStatement("x < 0", false, true))
{
MarkedPath.markOneStatement("return -x;\n", false, false);
return -x;
}
else {
MarkedPath.markOneStatement("return x;\n", false, false);
return x;
}
}

public static final int intabsintxTotalStatement = 3;
public static final int intabsintxTotalBranch = 2;
public static int factorial(int n)
{
MarkedPath.markOneStatement("int result=1;\n", false, false);
int result=1;
MarkedPath.markOneStatement("int i=1", false, false);
for (int i=1; ((i <= n) && MarkedPath.markOneStatement("i <= n", true, false)) || MarkedPath.markOneStatement("i <= n", false, true); MarkedPath.markOneStatement("i=i + 1", false, false),
i=i + 1) {
{
MarkedPath.markOneStatement("result*=i;\n", false, false);
result*=i;
}
}
MarkedPath.markOneStatement("return result;\n", false, false);
return result;
}

public static final int intfactorialintnTotalStatement = 6;
public static final int intfactorialintnTotalBranch = 2;
public static boolean isPrime(int n)
{
if (((n <= 1) && MarkedPath.markOneStatement("n <= 1", true, false)) || MarkedPath.markOneStatement("n <= 1", false, true))
{
MarkedPath.markOneStatement("return false;\n", false, false);
return false;
}
MarkedPath.markOneStatement("int i=2", false, false);
for (int i=2; ((i <= n / 2) && MarkedPath.markOneStatement("i <= n / 2", true, false)) || MarkedPath.markOneStatement("i <= n / 2", false, true); MarkedPath.markOneStatement("i++", false, false),
i++) {
if (((n % i == 0) && MarkedPath.markOneStatement("n % i == 0", true, false)) || MarkedPath.markOneStatement("n % i == 0", false, true))
{
MarkedPath.markOneStatement("return false;\n", false, false);
return false;
}
}
MarkedPath.markOneStatement("return true;\n", false, false);
return true;
}

public static final int booleanisPrimeintnTotalStatement = 8;
public static final int booleanisPrimeintnTotalBranch = 6;
public static int fibonacci(int n)
{
MarkedPath.markOneStatement("int a=0, b=1, c, i;\n", false, false);
int a=0, b=1, c, i;
if (((n == 0) && MarkedPath.markOneStatement("n == 0", true, false)) || MarkedPath.markOneStatement("n == 0", false, true))
{
MarkedPath.markOneStatement("return a;\n", false, false);
return a;
}
MarkedPath.markOneStatement("i=2", false, false);
for (i=2; ((i <= n) && MarkedPath.markOneStatement("i <= n", true, false)) || MarkedPath.markOneStatement("i <= n", false, true); MarkedPath.markOneStatement("i++", false, false),
i++) {
{
MarkedPath.markOneStatement("c=a + b;\n", false, false);
c=a + b;
MarkedPath.markOneStatement("a=b;\n", false, false);
a=b;
MarkedPath.markOneStatement("b=c;\n", false, false);
b=c;
}
}
MarkedPath.markOneStatement("return b;\n", false, false);
return b;
}

public static final int intfibonacciintnTotalStatement = 10;
public static final int intfibonacciintnTotalBranch = 4;
public static boolean isPerfectNumber(int number)
{
if (((number <= 0) && MarkedPath.markOneStatement("number <= 0", true, false)) || MarkedPath.markOneStatement("number <= 0", false, true))
{
{
MarkedPath.markOneStatement("return false;\n", false, false);
return false;
}
}
MarkedPath.markOneStatement("int sum=0;\n", false, false);
int sum=0;
MarkedPath.markOneStatement("int i=1", false, false);
for (int i=1; ((i < number) && MarkedPath.markOneStatement("i < number", true, false)) || MarkedPath.markOneStatement("i < number", false, true); MarkedPath.markOneStatement("i++", false, false),
i++) {
{
if (((number % i == 0) && MarkedPath.markOneStatement("number % i == 0", true, false)) || MarkedPath.markOneStatement("number % i == 0", false, true))
{
{
MarkedPath.markOneStatement("sum+=i;\n", false, false);
sum+=i;
}
}
}
}
MarkedPath.markOneStatement("return sum == number;\n", false, false);
return sum == number;
}

public static final int booleanisPerfectNumberintnumberTotalStatement = 9;
public static final int booleanisPerfectNumberintnumberTotalBranch = 6;
public static final int MathTotalStatement = 57;
}
