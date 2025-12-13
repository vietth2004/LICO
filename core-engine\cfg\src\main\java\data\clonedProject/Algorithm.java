package data.clonedProject;
import java.io.FileWriter;
public class Algorithm {
private static void writeDataToFile(String data, String path, boolean append) {
try {
FileWriter writer = new FileWriter(path, append);
writer.write(data);
writer.close();
} catch (Exception e) {
e.printStackTrace();
}
}
private static boolean mark(String statement, boolean isTrueCondition, boolean isFalseCondition, int id) {
StringBuilder markResult = new StringBuilder();
markResult.append(statement).append("===");
markResult.append(isTrueCondition).append("===");
markResult.append(isFalseCondition).append("===");
markResult.append(id).append("---end---");
writeDataToFile(markResult.toString(), "core-engine/cfg/src/main/java/data/testDriverData/runTestDriverData.txt", true);
if (!isTrueCondition && !isFalseCondition) return true;
return !isFalseCondition;
}
private static int MAX_RECURSION_DEPTH = 1;
public static int fibonacci(int n) {
if (MAX_RECURSION_DEPTH <= 0) {
System.out.println("Recursion depth exceeded. Returning default value.");
return 0;
}
MAX_RECURSION_DEPTH--;
{
mark("int a=0, b=1, c, i;\n", false, false, 1);
int a=0, b=1, c, i;
if (((n == 0) && mark("n == 0", true, false, 2)) || mark("n == 0", false, true, 2))
{
mark("return a;\n", false, false, 3);
return a;
}
mark("i=2", false, false, 4);
for (i=2; ((i <= n) && mark("i <= n", true, false, 4)) || mark("i <= n", false, true, 4); mark("i++", false, false, 4),
i++) {
{
mark("c=a + b;\n", false, false, 5);
c=a + b;
mark("a=b;\n", false, false, 6);
a=b;
mark("b=c;\n", false, false, 7);
b=c;
}
}
mark("return b;\n", false, false, 9);
return b;
}

}
public static final int intfibonacciintnTotalStatement = 10;
public static final int intfibonacciintnTotalBranch = 4;
public static boolean leapYear(int year) {
if (MAX_RECURSION_DEPTH <= 0) {
System.out.println("Recursion depth exceeded. Returning default value.");
return false;
}
MAX_RECURSION_DEPTH--;
{
if (((year % 4 == 0) && mark("year % 4 == 0", true, false, 1)) || mark("year % 4 == 0", false, true, 1))
{
{
if (((year % 100 == 0) && mark("year % 100 == 0", true, false, 2)) || mark("year % 100 == 0", false, true, 2))
{
{
if (((year % 400 == 0) && mark("year % 400 == 0", true, false, 3)) || mark("year % 400 == 0", false, true, 3))
{
mark("return true;\n", false, false, 4);
return true;
}
else {
mark("return false;\n", false, false, 6);
return false;
}
}
}
else {
mark("return true;\n", false, false, 8);
return true;
}
}
}
else {
mark("return false;\n", false, false, 10);
return false;
}
}

}
public static final int booleanleapYearintyearTotalStatement = 7;
public static final int booleanleapYearintyearTotalBranch = 6;
public static int calDigits(char start, char end) {
if (MAX_RECURSION_DEPTH <= 0) {
System.out.println("Recursion depth exceeded. Returning default value.");
return 0;
}
MAX_RECURSION_DEPTH--;
{
mark("int sum=0;\n", false, false, 1);
int sum=0;
mark("char ch=start", false, false, 2);
for (char ch=start; ((ch <= end) && mark("ch <= end", true, false, 2)) || mark("ch <= end", false, true, 2); mark("ch++", false, false, 2),
ch++) {
{
mark("sum+=1;\n", false, false, 3);
sum+=1;
}
}
mark("return sum;\n", false, false, 5);
return sum;
}

}
public static final int intcalDigitscharstartcharendTotalStatement = 6;
public static final int intcalDigitscharstartcharendTotalBranch = 2;
public static boolean isTriangle(int a, int b, int c) {
if (MAX_RECURSION_DEPTH <= 0) {
System.out.println("Recursion depth exceeded. Returning default value.");
return false;
}
MAX_RECURSION_DEPTH--;
{
if (((a <= 0 || b <= 0 || c <= 0) && mark("a <= 0 || b <= 0 || c <= 0", true, false, 1)) || mark("a <= 0 || b <= 0 || c <= 0", false, true, 1))
{
{
mark("return false;\n", false, false, 2);
return false;
}
}
else {
if (((a + b <= c || a + c <= b || b + c <= a) && mark("a + b <= c || a + c <= b || b + c <= a", true, false, 3)) || mark("a + b <= c || a + c <= b || b + c <= a", false, true, 3))
{
mark("return false;\n", false, false, 4);
return false;
}
else {
mark("return true;\n", false, false, 6);
return true;
}
}
}

}
public static final int booleanisTriangleintaintbintcTotalStatement = 5;
public static final int booleanisTriangleintaintbintcTotalBranch = 4;
public static int findMax(int n1, int n2, int n3) {
if (MAX_RECURSION_DEPTH <= 0) {
System.out.println("Recursion depth exceeded. Returning default value.");
return 0;
}
MAX_RECURSION_DEPTH--;
{
mark("int maxN1N2;\n", false, false, 1);
int maxN1N2;
if (((n1 > n2) && mark("n1 > n2", true, false, 2)) || mark("n1 > n2", false, true, 2))
{
{
mark("maxN1N2=n1;\n", false, false, 3);
maxN1N2=n1;
}
}
else {
{
mark("maxN1N2=n2;\n", false, false, 5);
maxN1N2=n2;
}
}
if (((maxN1N2 > n3) && mark("maxN1N2 > n3", true, false, 8)) || mark("maxN1N2 > n3", false, true, 8))
{
{
mark("return maxN1N2;\n", false, false, 9);
return maxN1N2;
}
}
else {
{
mark("return n3;\n", false, false, 11);
return n3;
}
}
}

}
public static final int intfindMaxintn1intn2intn3TotalStatement = 7;
public static final int intfindMaxintn1intn2intn3TotalBranch = 4;
public static int foo(int a, int b, int c) {
if (MAX_RECURSION_DEPTH <= 0) {
System.out.println("Recursion depth exceeded. Returning default value.");
return 0;
}
MAX_RECURSION_DEPTH--;
{
if (((a > 10) && mark("a > 10", true, false, 1)) || mark("a > 10", false, true, 1))
{
{
if (((c < 20) && mark("c < 20", true, false, 2)) || mark("c < 20", false, true, 2))
{
{
mark("int i=0", false, false, 3);
for (int i=0; ((i < b) && mark("i < b", true, false, 3)) || mark("i < b", false, true, 3); mark("i=i + 1", false, false, 3),
i=i + 1) {
{
mark("System.out.println(\"hello\");\n", false, false, 4);
System.out.println("hello");
}
}
if (((b < -5) && mark("b < -5", true, false, 6)) || mark("b < -5", false, true, 6))
{
{
mark("System.out.println(\"bye\");\n", false, false, 7);
System.out.println("bye");
}
}
}
}
else {
{
mark("return a + b + c;\n", false, false, 10);
return a + b + c;
}
}
}
}
else {
{
if (((a + b - c == 10) && mark("a + b - c == 10", true, false, 13)) || mark("a + b - c == 10", false, true, 13))
{
{
mark("System.out.println(\"hehe\");\n", false, false, 14);
System.out.println("hehe");
}
}
else {
if (((a > b) && mark("a > b", true, false, 15)) || mark("a > b", false, true, 15))
{
{
mark("System.out.println(\"hoo\");\n", false, false, 16);
System.out.println("hoo");
}
}
}
}
}
mark("return 5;\n", false, false, 19);
return 5;
}

}
public static final int intfoointaintbintcTotalStatement = 14;
public static final int intfoointaintbintcTotalBranch = 12;
public static int randomSum(int a, int b) {
if (MAX_RECURSION_DEPTH <= 0) {
System.out.println("Recursion depth exceeded. Returning default value.");
return 0;
}
MAX_RECURSION_DEPTH--;
{
if (((a == b) && mark("a == b", true, false, 1)) || mark("a == b", false, true, 1))
{
mark("return 0;\n", false, false, 1);
return 0;
}
if (((a > b) && mark("a > b", true, false, 2)) || mark("a > b", false, true, 2))
{
{
mark("return 1;\n", false, false, 3);
return 1;
}
}
else {
if (((a < b) && mark("a < b", true, false, 4)) || mark("a < b", false, true, 4))
{
{
mark("return -1;\n", false, false, 5);
return -1;
}
}
}
mark("return 2;\n", false, false, 7);
return 2;
}

}
public static final int intrandomSumintaintbTotalStatement = 7;
public static final int intrandomSumintaintbTotalBranch = 6;
public static int getFare(int age, int distance) {
if (MAX_RECURSION_DEPTH <= 0) {
System.out.println("Recursion depth exceeded. Returning default value.");
return 0;
}
MAX_RECURSION_DEPTH--;
{
mark("int fare=0;\n", false, false, 1);
int fare=0;
if (((age > 4 && age < 14) && mark("age > 4 && age < 14", true, false, 2)) || mark("age > 4 && age < 14", false, true, 2))
{
{
if (((distance >= 10) && mark("distance >= 10", true, false, 3)) || mark("distance >= 10", false, true, 3))
{
{
mark("fare=130;\n", false, false, 4);
fare=130;
}
}
else {
{
mark("fare=100;\n", false, false, 6);
fare=100;
}
}
}
}
else {
if (((age > 15) && mark("age > 15", true, false, 8)) || mark("age > 15", false, true, 8))
{
{
if (((distance < 10 && age >= 60) && mark("distance < 10 && age >= 60", true, false, 9)) || mark("distance < 10 && age >= 60", false, true, 9))
{
{
mark("fare=160;\n", false, false, 10);
fare=160;
}
}
else {
if (((distance > 10 && age < 60) && mark("distance > 10 && age < 60", true, false, 11)) || mark("distance > 10 && age < 60", false, true, 11))
{
{
mark("fare=250;\n", false, false, 12);
fare=250;
}
}
else {
{
mark("fare=200;\n", false, false, 14);
fare=200;
}
}
}
}
}
}
mark("return fare;\n", false, false, 17);
return fare;
}

}
public static final int intgetFareintageintdistanceTotalStatement = 12;
public static final int intgetFareintageintdistanceTotalBranch = 10;
public static int power(int i, int j) {
if (MAX_RECURSION_DEPTH <= 0) {
System.out.println("Recursion depth exceeded. Returning default value.");
return 0;
}
MAX_RECURSION_DEPTH--;
{
mark("int value;\n", false, false, 1);
int value;
if (((j < 0) && mark("j < 0", true, false, 2)) || mark("j < 0", false, true, 2))
{
{
if (((i == 1) && mark("i == 1", true, false, 3)) || mark("i == 1", false, true, 3))
{
{
mark("value=1;\n", false, false, 4);
value=1;
}
}
else {
if (((i == 0) && mark("i == 0", true, false, 5)) || mark("i == 0", false, true, 5))
{
{
mark("return -1;\n", false, false, 6);
return -1;
}
}
else {
{
mark("value=0;\n", false, false, 8);
value=0;
}
}
}
}
}
else {
if (((j == 0) && mark("j == 0", true, false, 10)) || mark("j == 0", false, true, 10))
{
{
if (((i >= 0) && mark("i >= 0", true, false, 11)) || mark("i >= 0", false, true, 11))
{
{
mark("return -1;\n", false, false, 12);
return -1;
}
}
else {
{
mark("value=1;\n", false, false, 14);
value=1;
}
}
}
}
else {
if (((j >= 1) && mark("j >= 1", true, false, 16)) || mark("j >= 1", false, true, 16))
{
{
mark("value=i;\n", false, false, 17);
value=i;
}
}
else {
{
mark("value=1;\n", false, false, 19);
value=1;
mark("int k=1", false, false, 20);
for (int k=1; ((k <= j) && mark("k <= j", true, false, 20)) || mark("k <= j", false, true, 20); mark("k++", false, false, 20),
k++) {
{
mark("value*=i;\n", false, false, 21);
value*=i;
}
}
}
}
}
}
mark("return value;\n", false, false, 24);
return value;
}

}
public static final int intpowerintiintjTotalStatement = 19;
public static final int intpowerintiintjTotalBranch = 14;
public static int gcd(int m, int n) {
if (MAX_RECURSION_DEPTH <= 0) {
System.out.println("Recursion depth exceeded. Returning default value.");
return 0;
}
MAX_RECURSION_DEPTH--;
{
if (((m < 0) && mark("m < 0", true, false, 1)) || mark("m < 0", false, true, 1))
{
mark("m=-m;\n", false, false, 1);
m=-m;
}
if (((n < 0) && mark("n < 0", true, false, 2)) || mark("n < 0", false, true, 2))
{
mark("n=-n;\n", false, false, 2);
n=-n;
}
if (((m == 0) && mark("m == 0", true, false, 3)) || mark("m == 0", false, true, 3))
{
mark("return m;\n", false, false, 3);
return m;
}
if (((n == 0) && mark("n == 0", true, false, 4)) || mark("n == 0", false, true, 4))
{
mark("return n;\n", false, false, 4);
return n;
}
while (((m != n) && mark("m != n", true, false, 5)) || mark("m != n", false, true, 5)) {
{
if (((m > n) && mark("m > n", true, false, 6)) || mark("m > n", false, true, 6))
{
mark("m=m - n;\n", false, false, 6);
m=m - n;
}
else {
mark("n=n - m;\n", false, false, 7);
n=n - m;
}
}
}
mark("return m;\n", false, false, 9);
return m;
}

}
public static final int intgcdintmintnTotalStatement = 13;
public static final int intgcdintmintnTotalBranch = 12;
public static int checkValidDate(int day, int month, int year) {
if (MAX_RECURSION_DEPTH <= 0) {
System.out.println("Recursion depth exceeded. Returning default value.");
return 0;
}
MAX_RECURSION_DEPTH--;
{
if (((day > 1 && month > 1 && year > 1 && day < 31 && month < 12 && year < 2024) && mark("day > 1 && month > 1 && year > 1 && day < 31 && month < 12 && year < 2024", true, false, 1)) || mark("day > 1 && month > 1 && year > 1 && day < 31 && month < 12 && year < 2024", false, true, 1))
{
{
if ((((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) && (day <= 31)) && mark("(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) && (day <= 31)", true, false, 2)) || mark("(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) && (day <= 31)", false, true, 2))
{
mark("return 1;\n", false, false, 3);
return 1;
}
if ((((month == 4 || month == 6 || month == 9 || month == 11) && (day <= 30)) && mark("(month == 4 || month == 6 || month == 9 || month == 11) && (day <= 30)", true, false, 4)) || mark("(month == 4 || month == 6 || month == 9 || month == 11) && (day <= 30)", false, true, 4))
{
mark("return 1;\n", false, false, 4);
return 1;
}
if ((((month == 2) && (day < 28)) && mark("(month == 2) && (day < 28)", true, false, 5)) || mark("(month == 2) && (day < 28)", false, true, 5))
{
mark("return 1;\n", false, false, 5);
return 1;
}
if ((((month == 2) && (day == 29) && (year % 4 == 0)&& (year % 400 != 0)) && mark("(month == 2) && (day == 29) && (year % 4 == 0)&& (year % 400 != 0)", true, false, 6)) || mark("(month == 2) && (day == 29) && (year % 4 == 0)&& (year % 400 != 0)", false, true, 6))
{
mark("return 1;\n", false, false, 6);
return 1;
}
}
}
mark("return 0;\n", false, false, 8);
return 0;
}

}
public static final int intcheckValidDateintdayintmonthintyearTotalStatement = 10;
public static final int intcheckValidDateintdayintmonthintyearTotalBranch = 10;
public static boolean test(int a, int b) {
if (MAX_RECURSION_DEPTH <= 0) {
System.out.println("Recursion depth exceeded. Returning default value.");
return false;
}
MAX_RECURSION_DEPTH--;
{
if (((a > 2 && b < 5 || a > 18) && mark("a > 2 && b < 5 || a > 18", true, false, 1)) || mark("a > 2 && b < 5 || a > 18", false, true, 1))
{
{
mark("return true;\n", false, false, 2);
return true;
}
}
mark("return false;\n", false, false, 4);
return false;
}

}
public static final int booleantestintaintbTotalStatement = 3;
public static final int booleantestintaintbTotalBranch = 2;
public static int minimum(int a, int b, int c) {
if (MAX_RECURSION_DEPTH <= 0) {
System.out.println("Recursion depth exceeded. Returning default value.");
return 0;
}
MAX_RECURSION_DEPTH--;
{
if (((a < b && a < c) && mark("a < b && a < c", true, false, 1)) || mark("a < b && a < c", false, true, 1))
{
{
mark("return a;\n", false, false, 2);
return a;
}
}
else {
if (((b < a && b < c) && mark("b < a && b < c", true, false, 3)) || mark("b < a && b < c", false, true, 3))
{
{
mark("return b;\n", false, false, 4);
return b;
}
}
else {
{
mark("return c;\n", false, false, 6);
return c;
}
}
}
}

}
public static final int intminimumintaintbintcTotalStatement = 5;
public static final int intminimumintaintbintcTotalBranch = 4;
public static int methodInvocation(int x, int y) {
if (MAX_RECURSION_DEPTH <= 0) {
System.out.println("Recursion depth exceeded. Returning default value.");
return 0;
}
MAX_RECURSION_DEPTH--;
{
if (((Math.max(x,y) == y) && mark("Math.max(x,y) == y", true, false, 1)) || mark("Math.max(x,y) == y", false, true, 1))
{
{
mark("return 1;\n", false, false, 2);
return 1;
}
}
else {
{
mark("return 3;\n", false, false, 4);
return 3;
}
}
}

}
public static final int intmethodInvocationintxintyTotalStatement = 3;
public static final int intmethodInvocationintxintyTotalBranch = 2;
public static double easeOutBounce(double x) {
if (MAX_RECURSION_DEPTH <= 0) {
System.out.println("Recursion depth exceeded. Returning default value.");
return 0;
}
MAX_RECURSION_DEPTH--;
{
mark("double n1=7.5625;\n", false, false, 1);
double n1=7.5625;
mark("double d1=2.75;\n", false, false, 2);
double d1=2.75;
if (((x < 1 / d1) && mark("x < 1 / d1", true, false, 3)) || mark("x < 1 / d1", false, true, 3))
{
mark("return n1 * x * x;\n", false, false, 3);
return n1 * x * x;
}
if (((x < 2 / d1) && mark("x < 2 / d1", true, false, 4)) || mark("x < 2 / d1", false, true, 4))
{
{
mark("return n1 * (x-=1.5 / d1) * x + 0.75;\n", false, false, 5);
return n1 * (x-=1.5 / d1) * x + 0.75;
}
}
if (((x < 2.5 / d1) && mark("x < 2.5 / d1", true, false, 7)) || mark("x < 2.5 / d1", false, true, 7))
{
{
mark("return n1 * (x-=2.25 / d1) * x + 0.9375;\n", false, false, 8);
return n1 * (x-=2.25 / d1) * x + 0.9375;
}
}
mark("return n1 * (x-=2.625 / d1) * x + 0.984375;\n", false, false, 10);
return n1 * (x-=2.625 / d1) * x + 0.984375;
}

}
public static final int doubleeaseOutBouncedoublexTotalStatement = 9;
public static final int doubleeaseOutBouncedoublexTotalBranch = 6;
public static final int AlgorithmTotalStatement = 130;
}
