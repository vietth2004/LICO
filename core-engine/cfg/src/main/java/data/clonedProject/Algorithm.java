package data.clonedProject;
import data.clonedProject.utils.JavaAlgorithm;
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
private static boolean mark(String statement, boolean isTrueCondition, boolean isFalseCondition) {
StringBuilder markResult = new StringBuilder();
markResult.append(statement).append("===");
markResult.append(isTrueCondition).append("===");
markResult.append(isFalseCondition).append("---end---");
writeDataToFile(markResult.toString(), "core-engine/cfg/src/main/java/data/testDriverData/runTestDriverData.txt", true);
if (!isTrueCondition && !isFalseCondition) return true;
return !isFalseCondition;
}
public static int fibonacci(int n){
  int a=0, b=1, c, i;
  if (n == 0)   return a;
  for (i=2; i <= n; i++) {
    c=a + b;
    a=b;
    b=c;
  }
  return b;
}
public static int fibonacci_clone(int n)
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
private static boolean leapYear(int year){
  if (year % 4 == 0) {
    if (year % 100 == 0) {
      if (year % 400 == 0)       return true;
 else       return false;
    }
 else     return true;
  }
 else   return false;
}
private static boolean leapYear_clone(int year)
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
public static int calDigits(char start,char end){
  int sum=0;
  for (char ch=start; ch <= end; ch++) {
    sum+=1;
  }
  return sum;
}
public static int calDigits_clone(char start, char end)
{
mark("int sum=0;\n", false, false);
int sum=0;
mark("char ch=start", false, false);
for (char ch=start; ((ch <= end) && mark("ch <= end", true, false)) || mark("ch <= end", false, true); mark("ch++", false, false),
ch++) {
{
mark("sum+=1;\n", false, false);
sum+=1;
}
}
mark("return sum;\n", false, false);
return sum;
}

public static final int intcalDigitscharstartcharendTotalStatement = 6;
public static final int intcalDigitscharstartcharendTotalBranch = 2;
public static boolean isTriangle(int a,int b,int c){
  if (a <= 0 || b <= 0 || c <= 0) {
    return false;
  }
 else   if (a + b <= c || a + c <= b || b + c <= a)   return false;
 else   return true;
}
public static boolean isTriangle_clone(int a, int b, int c)
{
if (((a <= 0 || b <= 0 || c <= 0) && mark("a <= 0 || b <= 0 || c <= 0", true, false)) || mark("a <= 0 || b <= 0 || c <= 0", false, true))
{
{
mark("return false;\n", false, false);
return false;
}
}
else {
if (((a + b <= c || a + c <= b || b + c <= a) && mark("a + b <= c || a + c <= b || b + c <= a", true, false)) || mark("a + b <= c || a + c <= b || b + c <= a", false, true))
{
mark("return false;\n", false, false);
return false;
}
else {
mark("return true;\n", false, false);
return true;
}
}
}

public static final int booleanisTriangleintaintbintcTotalStatement = 5;
public static final int booleanisTriangleintaintbintcTotalBranch = 4;
private static int findMax(int n1,int n2,int n3){
  int maxN1N2;
  if (n1 > n2) {
    maxN1N2=n1;
  }
 else {
    maxN1N2=n2;
  }
  if (maxN1N2 > n3) {
    return maxN1N2;
  }
 else {
    return n3;
  }
}
private static int findMax_clone(int n1, int n2, int n3)
{
mark("int maxN1N2;\n", false, false);
int maxN1N2;
if (((n1 > n2) && mark("n1 > n2", true, false)) || mark("n1 > n2", false, true))
{
{
mark("maxN1N2=n1;\n", false, false);
maxN1N2=n1;
}
}
else {
{
mark("maxN1N2=n2;\n", false, false);
maxN1N2=n2;
}
}
if (((maxN1N2 > n3) && mark("maxN1N2 > n3", true, false)) || mark("maxN1N2 > n3", false, true))
{
{
mark("return maxN1N2;\n", false, false);
return maxN1N2;
}
}
else {
{
mark("return n3;\n", false, false);
return n3;
}
}
}

public static final int intfindMaxintn1intn2intn3TotalStatement = 7;
public static final int intfindMaxintn1intn2intn3TotalBranch = 4;
public static int foo(int a,int b,int c){
  if (a > 10) {
    if (c < 20) {
      for (int i=0; i < b; i=i + 1) {
        System.out.println("hello");
      }
      if (b < -5) {
        System.out.println("bye");
      }
    }
 else {
      return a + b + c;
    }
  }
 else {
    if (a + b - c == 10) {
      System.out.println("hehe");
    }
 else     if (a > b) {
      System.out.println("hoo");
    }
  }
  return 5;
}
public static int foo_clone(int a, int b, int c)
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
public static int randomSum(int a,int b){
  if (a == b)   return 0;
  if (a > b) {
    return 1;
  }
 else   if (a < b) {
    return -1;
  }
  return 2;
}
public static int randomSum_clone(int a, int b)
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
public static int getFare(int age,int distance){
  int fare=0;
  if (age > 4 && age < 14) {
    if (distance >= 10) {
      fare=130;
    }
 else {
      fare=100;
    }
  }
 else   if (age > 15) {
    if (distance < 10 && age >= 60) {
      fare=160;
    }
 else     if (distance > 10 && age < 60) {
      fare=250;
    }
 else {
      fare=200;
    }
  }
  return fare;
}
public static int getFare_clone(int age, int distance)
{
mark("int fare=0;\n", false, false);
int fare=0;
if (((age > 4 && age < 14) && mark("age > 4 && age < 14", true, false)) || mark("age > 4 && age < 14", false, true))
{
{
if (((distance >= 10) && mark("distance >= 10", true, false)) || mark("distance >= 10", false, true))
{
{
mark("fare=130;\n", false, false);
fare=130;
}
}
else {
{
mark("fare=100;\n", false, false);
fare=100;
}
}
}
}
else {
if (((age > 15) && mark("age > 15", true, false)) || mark("age > 15", false, true))
{
{
if (((distance < 10 && age >= 60) && mark("distance < 10 && age >= 60", true, false)) || mark("distance < 10 && age >= 60", false, true))
{
{
mark("fare=160;\n", false, false);
fare=160;
}
}
else {
if (((distance > 10 && age < 60) && mark("distance > 10 && age < 60", true, false)) || mark("distance > 10 && age < 60", false, true))
{
{
mark("fare=250;\n", false, false);
fare=250;
}
}
else {
{
mark("fare=200;\n", false, false);
fare=200;
}
}
}
}
}
}
mark("return fare;\n", false, false);
return fare;
}

public static final int intgetFareintageintdistanceTotalStatement = 12;
public static final int intgetFareintageintdistanceTotalBranch = 10;
public static int power(int i,int j){
  int value;
  if (j < 0) {
    if (i == 1) {
      value=1;
    }
 else     if (i == 0) {
      return -1;
    }
 else {
      value=0;
    }
  }
 else   if (j == 0) {
    if (i >= 0) {
      return -1;
    }
 else {
      value=1;
    }
  }
 else   if (j >= 1) {
    value=i;
  }
 else {
    value=1;
    for (int k=1; k <= j; k++) {
      value*=i;
    }
  }
  return value;
}
public static int power_clone(int i, int j)
{
mark("int value;\n", false, false);
int value;
if (((j < 0) && mark("j < 0", true, false)) || mark("j < 0", false, true))
{
{
if (((i == 1) && mark("i == 1", true, false)) || mark("i == 1", false, true))
{
{
mark("value=1;\n", false, false);
value=1;
}
}
else {
if (((i == 0) && mark("i == 0", true, false)) || mark("i == 0", false, true))
{
{
mark("return -1;\n", false, false);
return -1;
}
}
else {
{
mark("value=0;\n", false, false);
value=0;
}
}
}
}
}
else {
if (((j == 0) && mark("j == 0", true, false)) || mark("j == 0", false, true))
{
{
if (((i >= 0) && mark("i >= 0", true, false)) || mark("i >= 0", false, true))
{
{
mark("return -1;\n", false, false);
return -1;
}
}
else {
{
mark("value=1;\n", false, false);
value=1;
}
}
}
}
else {
if (((j >= 1) && mark("j >= 1", true, false)) || mark("j >= 1", false, true))
{
{
mark("value=i;\n", false, false);
value=i;
}
}
else {
{
mark("value=1;\n", false, false);
value=1;
mark("int k=1", false, false);
for (int k=1; ((k <= j) && mark("k <= j", true, false)) || mark("k <= j", false, true); mark("k++", false, false),
k++) {
{
mark("value*=i;\n", false, false);
value*=i;
}
}
}
}
}
}
mark("return value;\n", false, false);
return value;
}

public static final int intpowerintiintjTotalStatement = 19;
public static final int intpowerintiintjTotalBranch = 14;
public static int gcd(int m,int n){
  if (m < 0)   m=-m;
  if (n < 0)   n=-n;
  if (m == 0)   return m;
  if (n == 0)   return n;
  while (m != n) {
    if (m > n)     m=m - n;
 else     n=n - m;
  }
  return m;
}
public static int gcd_clone(int m, int n)
{
if (((m < 0) && mark("m < 0", true, false)) || mark("m < 0", false, true))
{
mark("m=-m;\n", false, false);
m=-m;
}
if (((n < 0) && mark("n < 0", true, false)) || mark("n < 0", false, true))
{
mark("n=-n;\n", false, false);
n=-n;
}
if (((m == 0) && mark("m == 0", true, false)) || mark("m == 0", false, true))
{
mark("return m;\n", false, false);
return m;
}
if (((n == 0) && mark("n == 0", true, false)) || mark("n == 0", false, true))
{
mark("return n;\n", false, false);
return n;
}
while (((m != n) && mark("m != n", true, false)) || mark("m != n", false, true)) {
{
if (((m > n) && mark("m > n", true, false)) || mark("m > n", false, true))
{
mark("m=m - n;\n", false, false);
m=m - n;
}
else {
mark("n=n - m;\n", false, false);
n=n - m;
}
}
}
mark("return m;\n", false, false);
return m;
}

public static final int intgcdintmintnTotalStatement = 13;
public static final int intgcdintmintnTotalBranch = 12;
public static int checkValidDate(int day,int month,int year){
  if (day > 1 && month > 1 && year > 1 && day < 31 && month < 12 && year < 2024) {
    if ((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) && (day <= 31))     return 1;
    if ((month == 4 || month == 6 || month == 9 || month == 11) && (day <= 30))     return 1;
    if ((month == 2) && (day < 28))     return 1;
    if ((month == 2) && (day == 29) && (year % 4 == 0)&& (year % 400 != 0))     return 1;
  }
  return 0;
}
public static int checkValidDate_clone(int day, int month, int year)
{
if (((day > 1 && month > 1 && year > 1 && day < 31 && month < 12 && year < 2024) && mark("day > 1 && month > 1 && year > 1 && day < 31 && month < 12 && year < 2024", true, false)) || mark("day > 1 && month > 1 && year > 1 && day < 31 && month < 12 && year < 2024", false, true))
{
{
if ((((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) && (day <= 31)) && mark("(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) && (day <= 31)", true, false)) || mark("(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) && (day <= 31)", false, true))
{
mark("return 1;\n", false, false);
return 1;
}
if ((((month == 4 || month == 6 || month == 9 || month == 11) && (day <= 30)) && mark("(month == 4 || month == 6 || month == 9 || month == 11) && (day <= 30)", true, false)) || mark("(month == 4 || month == 6 || month == 9 || month == 11) && (day <= 30)", false, true))
{
mark("return 1;\n", false, false);
return 1;
}
if ((((month == 2) && (day < 28)) && mark("(month == 2) && (day < 28)", true, false)) || mark("(month == 2) && (day < 28)", false, true))
{
mark("return 1;\n", false, false);
return 1;
}
if ((((month == 2) && (day == 29) && (year % 4 == 0)&& (year % 400 != 0)) && mark("(month == 2) && (day == 29) && (year % 4 == 0)&& (year % 400 != 0)", true, false)) || mark("(month == 2) && (day == 29) && (year % 4 == 0)&& (year % 400 != 0)", false, true))
{
mark("return 1;\n", false, false);
return 1;
}
}
}
mark("return 0;\n", false, false);
return 0;
}

public static final int intcheckValidDateintdayintmonthintyearTotalStatement = 10;
public static final int intcheckValidDateintdayintmonthintyearTotalBranch = 10;
public static boolean test(int a,int b){
  if (a > 2 && b < 5 || a > 18) {
    return true;
  }
  return false;
}
public static boolean test_clone(int a, int b)
{
if (((a > 2 && b < 5 || a > 18) && mark("a > 2 && b < 5 || a > 18", true, false)) || mark("a > 2 && b < 5 || a > 18", false, true))
{
{
mark("return true;\n", false, false);
return true;
}
}
mark("return false;\n", false, false);
return false;
}

public static final int booleantestintaintbTotalStatement = 3;
public static final int booleantestintaintbTotalBranch = 2;
public static int minimum(int a,int b,int c){
  if (a < b && a < c) {
    return a;
  }
 else   if (b < a && b < c) {
    return b;
  }
 else {
    return c;
  }
}
public static int minimum_clone(int a, int b, int c)
{
if (((a < b && a < c) && mark("a < b && a < c", true, false)) || mark("a < b && a < c", false, true))
{
{
mark("return a;\n", false, false);
return a;
}
}
else {
if (((b < a && b < c) && mark("b < a && b < c", true, false)) || mark("b < a && b < c", false, true))
{
{
mark("return b;\n", false, false);
return b;
}
}
else {
{
mark("return c;\n", false, false);
return c;
}
}
}
}

public static final int intminimumintaintbintcTotalStatement = 5;
public static final int intminimumintaintbintcTotalBranch = 4;
public static int methodInvocation(int x,int y){
  if (Math.max(x,y) == y) {
    return 1;
  }
 else {
    return 3;
  }
}
public static int methodInvocation_clone(int x, int y)
{
if (((Math.max(x,y) == y) && mark("Math.max(x,y) == y", true, false)) || mark("Math.max(x,y) == y", false, true))
{
{
mark("return 1;\n", false, false);
return 1;
}
}
else {
{
mark("return 3;\n", false, false);
return 3;
}
}
}

public static final int intmethodInvocationintxintyTotalStatement = 3;
public static final int intmethodInvocationintxintyTotalBranch = 2;
public static double easeOutBounce(double x){
  double n1=7.5625;
  double d1=2.75;
  if (x < 1 / d1)   return n1 * x * x;
  if (x < 2 / d1) {
    return n1 * (x-=1.5 / d1) * x + 0.75;
  }
  if (x < 2.5 / d1) {
    return n1 * (x-=2.25 / d1) * x + 0.9375;
  }
  return n1 * (x-=2.625 / d1) * x + 0.984375;
}
public static double easeOutBounce_clone(double x)
{
mark("double n1=7.5625;\n", false, false);
double n1=7.5625;
mark("double d1=2.75;\n", false, false);
double d1=2.75;
if (((x < 1 / d1) && mark("x < 1 / d1", true, false)) || mark("x < 1 / d1", false, true))
{
mark("return n1 * x * x;\n", false, false);
return n1 * x * x;
}
if (((x < 2 / d1) && mark("x < 2 / d1", true, false)) || mark("x < 2 / d1", false, true))
{
{
mark("return n1 * (x-=1.5 / d1) * x + 0.75;\n", false, false);
return n1 * (x-=1.5 / d1) * x + 0.75;
}
}
if (((x < 2.5 / d1) && mark("x < 2.5 / d1", true, false)) || mark("x < 2.5 / d1", false, true))
{
{
mark("return n1 * (x-=2.25 / d1) * x + 0.9375;\n", false, false);
return n1 * (x-=2.25 / d1) * x + 0.9375;
}
}
mark("return n1 * (x-=2.625 / d1) * x + 0.984375;\n", false, false);
return n1 * (x-=2.625 / d1) * x + 0.984375;
}

public static final int doubleeaseOutBouncedoublexTotalStatement = 9;
public static final int doubleeaseOutBouncedoublexTotalBranch = 6;
public static final int AlgorithmTotalStatement = 130;
}
