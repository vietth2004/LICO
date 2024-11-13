package data.clonedProject;
import java.io.FileWriter;
public class Utils {
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
public static int max(int x,int y){
  if (x > y)   return x;
 else   return y;
}
public static int max_clone(int x, int y)
{
if (((x > y) && mark("x > y", true, false)) || mark("x > y", false, true))
{
mark("return x;\n", false, false);
return x;
}
else {
mark("return y;\n", false, false);
return y;
}
}

public static final int intmaxintxintyTotalStatement = 3;
public static final int intmaxintxintyTotalBranch = 2;
public static int testStub(int x,int y){
  int max=0;
  if (max(x,y) == x) {
    max=x;
  }
 else {
    max=y;
  }
  return max;
}
public static int testStub_clone(int x, int y)
{
mark("int max=0;\n", false, false);
int max=0;
if (((max(x,y) == x) && mark("max(x,y) == x", true, false)) || mark("max(x,y) == x", false, true))
{
{
mark("max=x;\n", false, false);
max=x;
}
}
else {
{
mark("max=y;\n", false, false);
max=y;
}
}
mark("return max;\n", false, false);
return max;
}

public static final int inttestStubintxintyTotalStatement = 5;
public static final int inttestStubintxintyTotalBranch = 2;
public static final int UtilsTotalStatement = 8;
}
