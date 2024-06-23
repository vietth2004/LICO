package data.clonedProject;
import java.io.FileWriter;
public class Sample {
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
public static int calculateFare(int age, int distance)
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

public static final int intcalculateFareintageintdistanceTotalStatement = 12;
public static final int intcalculateFareintageintdistanceTotalBranch = 10;
public static boolean checkValidTime(int hour, int minute, int second)
{
if (((hour > 0 && hour < 23) && mark("hour > 0 && hour < 23", true, false)) || mark("hour > 0 && hour < 23", false, true))
{
{
if (((minute > 0 && minute < 59) && mark("minute > 0 && minute < 59", true, false)) || mark("minute > 0 && minute < 59", false, true))
{
{
if (((second > 0 && second < 59) && mark("second > 0 && second < 59", true, false)) || mark("second > 0 && second < 59", false, true))
{
{
mark("return true;\n", false, false);
return true;
}
}
}
}
}
}
mark("return false;\n", false, false);
return false;
}

public static final int booleancheckValidTimeinthourintminuteintsecondTotalStatement = 5;
public static final int booleancheckValidTimeinthourintminuteintsecondTotalBranch = 6;
public static void triangleType(int a, int b, int c)
{
if (((a != 0 && b != 0 && c != 0) && mark("a != 0 && b != 0 && c != 0", true, false)) || mark("a != 0 && b != 0 && c != 0", false, true))
{
{
if (((a + b >= c || a + c >= b || b + c >= a) && mark("a + b >= c || a + c >= b || b + c >= a", true, false)) || mark("a + b >= c || a + c >= b || b + c >= a", false, true))
{
{
if (((a == b && b == c) && mark("a == b && b == c", true, false)) || mark("a == b && b == c", false, true))
{
mark("System.out.println(\"Equilateral Triangle\");\n", false, false);
System.out.println("Equilateral Triangle");
}
else {
if (((a == b || b == c || c == a) && mark("a == b || b == c || c == a", true, false)) || mark("a == b || b == c || c == a", false, true))
{
mark("System.out.println(\"Isosceles Triangle\");\n", false, false);
System.out.println("Isosceles Triangle");
}
else {
mark("System.out.println(\"Scalene Triangle\");\n", false, false);
System.out.println("Scalene Triangle");
}
}
}
}
}
}
}

public static final int voidtriangleTypeintaintbintcTotalStatement = 7;
public static final int voidtriangleTypeintaintbintcTotalBranch = 8;
public static char convertGrade(int math, int english)
{
if (((math <= 100 && math > 0 && english <= 100 && english > 0) && mark("math <= 100 && math > 0 && english <= 100 && english > 0", true, false)) || mark("math <= 100 && math > 0 && english <= 100 && english > 0", false, true))
{
{
if (((math + english >= 180) && mark("math + english >= 180", true, false)) || mark("math + english >= 180", false, true))
{
{
mark("return 'A';\n", false, false);
return 'A';
}
}
else {
if (((math + english >= 150) && mark("math + english >= 150", true, false)) || mark("math + english >= 150", false, true))
{
{
mark("return 'B';\n", false, false);
return 'B';
}
}
else {
if (((math + english >= 120) && mark("math + english >= 120", true, false)) || mark("math + english >= 120", false, true))
{
{
if (((math < 60 && english < 60) && mark("math < 60 && english < 60", true, false)) || mark("math < 60 && english < 60", false, true))
{
{
mark("return 'C';\n", false, false);
return 'C';
}
}
}
}
else {
if (((math + english >= 100) && mark("math + english >= 100", true, false)) || mark("math + english >= 100", false, true))
{
{
if (((math < 50 && english < 50) && mark("math < 50 && english < 50", true, false)) || mark("math < 50 && english < 50", false, true))
{
{
mark("return 'D';\n", false, false);
return 'D';
}
}
}
}
}
}
}
}
}
mark("return 'X';\n", false, false);
return 'X';
}

public static final int charconvertGradeintmathintenglishTotalStatement = 12;
public static final int charconvertGradeintmathintenglishTotalBranch = 14;
public static int findMax(int n1, int n2, int n3)
{
mark("int maxN1N2;\n", false, false);
int maxN1N2;
if (((n1 < n2) && mark("n1 < n2", true, false)) || mark("n1 < n2", false, true))
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
public static double intPow(double number, int index)
{
mark("double result=1;\n", false, false);
double result=1;
if (((index == 0) && mark("index == 0", true, false)) || mark("index == 0", false, true))
{
mark("return 0;\n", false, false);
return 0;
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
mark("return -result;\n", false, false);
return -result;
}
}
}
}

public static final int doubleintPowdoublenumberintindexTotalStatement = 14;
public static final int doubleintPowdoublenumberintindexTotalBranch = 8;
public static double calculateBMI(double weight, double height)
{
if (((weight < 0 || height < 0) && mark("weight < 0 || height < 0", true, false)) || mark("weight < 0 || height < 0", false, true))
{
{
mark("return -1;\n", false, false);
return -1;
}
}
mark("return weight / (height * height);\n", false, false);
return weight / (height * height);
}

public static final int doublecalculateBMIdoubleweightdoubleheightTotalStatement = 3;
public static final int doublecalculateBMIdoubleweightdoubleheightTotalBranch = 2;
public static boolean isValidDate(int day, int month, int year)
{
if (((day > 1 && month > 1 && year > 1 && day < 31 && month < 12 && year < 2024) && mark("day > 1 && month > 1 && year > 1 && day < 31 && month < 12 && year < 2024", true, false)) || mark("day > 1 && month > 1 && year > 1 && day < 31 && month < 12 && year < 2024", false, true))
{
{
if ((((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) && (day <= 31)) && mark("(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) && (day <= 31)", true, false)) || mark("(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) && (day <= 31)", false, true))
{
mark("return true;\n", false, false);
return true;
}
if ((((month == 4 || month == 6 || month == 9 || month == 11) && (day <= 30)) && mark("(month == 4 || month == 6 || month == 9 || month == 11) && (day <= 30)", true, false)) || mark("(month == 4 || month == 6 || month == 9 || month == 11) && (day <= 30)", false, true))
{
mark("return true;\n", false, false);
return true;
}
if ((((month == 2) && (day < 28)) && mark("(month == 2) && (day < 28)", true, false)) || mark("(month == 2) && (day < 28)", false, true))
{
mark("return true;\n", false, false);
return true;
}
if ((((month == 2) && (day == 29) && (year % 4 == 0)&& (year % 400 != 0)) && mark("(month == 2) && (day == 29) && (year % 4 == 0)&& (year % 400 != 0)", true, false)) || mark("(month == 2) && (day == 29) && (year % 4 == 0)&& (year % 400 != 0)", false, true))
{
mark("return true;\n", false, false);
return true;
}
}
}
mark("return false;\n", false, false);
return false;
}

public static final int booleanisValidDateintdayintmonthintyearTotalStatement = 10;
public static final int booleanisValidDateintdayintmonthintyearTotalBranch = 10;
public static boolean isAscending(int a, int b, int c, int d)
{
if (((a <= b && b <= c && c <= d) && mark("a <= b && b <= c && c <= d", true, false)) || mark("a <= b && b <= c && c <= d", false, true))
{
{
mark("return true;\n", false, false);
return true;
}
}
if (((a > 0 && a < 0) && mark("a > 0 && a < 0", true, false)) || mark("a > 0 && a < 0", false, true))
{
{
mark("return false;\n", false, false);
return false;
}
}
mark("return true;\n", false, false);
return true;
}

public static final int booleanisAscendingintaintbintcintdTotalStatement = 5;
public static final int booleanisAscendingintaintbintcintdTotalBranch = 4;
public static final int SampleTotalStatement = 75;
}
