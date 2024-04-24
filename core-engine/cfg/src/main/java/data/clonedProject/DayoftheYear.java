package data.clonedProject;
import java.util.Arrays;
import java.util.List;
import java.io.FileWriter;
public class DayoftheYear {
List<Integer> daysInMonth=Arrays.asList(31,28,31,30,31,30,31,31,30,31,30,31);

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
public int dayOfYear(String date)
{
mark("int year=Integer.parseInt(date.split(\"-\")[0]);\n", false, false);
int year=Integer.parseInt(date.split("-")[0]);
mark("int month=Integer.parseInt(date.split(\"-\")[1]);\n", false, false);
int month=Integer.parseInt(date.split("-")[1]);
mark("int day=Integer.parseInt(date.split(\"-\")[2]);\n", false, false);
int day=Integer.parseInt(date.split("-")[2]);
mark("int numOfDays=getNumOfDays(month,isLeapYear(year));\n", false, false);
int numOfDays=getNumOfDays(month,isLeapYear(year));
mark("return numOfDays + day;\n", false, false);
return numOfDays + day;
}

public static final int intdayOfYearStringdateTotalStatement = 5;
public static final int intdayOfYearStringdateTotalBranch = 0;
public static boolean isLeapYear(int year)
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
private int getNumOfDays(int month, boolean isLeapYear)
{
mark("int numOfDays=0;\n", false, false);
int numOfDays=0;
mark("int i=1", false, false);
for (int i=1; ((i < month) && mark("i < month", true, false)) || mark("i < month", false, true); mark("i++", false, false),
i++) {
{
if (((i == 2) && mark("i == 2", true, false)) || mark("i == 2", false, true))
{
{
if (((isLeapYear) && mark("isLeapYear", true, false)) || mark("isLeapYear", false, true))
{
{
mark("numOfDays+=daysInMonth.get(i - 1) + 1;\n", false, false);
numOfDays+=daysInMonth.get(i - 1) + 1;
}
}
else {
mark("numOfDays+=daysInMonth.get(i - 1);\n", false, false);
numOfDays+=daysInMonth.get(i - 1);
}
}
}
else {
{
mark("numOfDays+=daysInMonth.get(i - 1);\n", false, false);
numOfDays+=daysInMonth.get(i - 1);
}
}
}
}
mark("return numOfDays;\n", false, false);
return numOfDays;
}

public static final int intgetNumOfDaysintmonthbooleanisLeapYearTotalStatement = 10;
public static final int intgetNumOfDaysintmonthbooleanisLeapYearTotalBranch = 6;
public static final int DayoftheYearTotalStatement = 22;
}
