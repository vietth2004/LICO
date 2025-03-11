package data.clonedProject;
import java.io.FileWriter;
public class BitSolutions {
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
public static int hammingWeight(int n){
  int bits=0;
  int mask=1;
  for (int i=0; i < 32; i++) {
    if ((n & mask) != 0) {
      bits+=1;
    }
    mask=mask << 1;
  }
  return bits;
}
public static int hammingWeight_clone(int n)
{
mark("int bits=0;\n", false, false);
int bits=0;
mark("int mask=1;\n", false, false);
int mask=1;
mark("int i=0", false, false);
for (int i=0; ((i < 32) && mark("i < 32", true, false)) || mark("i < 32", false, true); mark("i++", false, false),
i++) {
{
if ((((n & mask) != 0) && mark("(n & mask) != 0", true, false)) || mark("(n & mask) != 0", false, true))
{
{
mark("bits+=1;\n", false, false);
bits+=1;
}
}
mark("mask=mask << 1;\n", false, false);
mask=mask << 1;
}
}
mark("return bits;\n", false, false);
return bits;
}

public static final int inthammingWeightintnTotalStatement = 9;
public static final int inthammingWeightintnTotalBranch = 4;
public static int bitwiseComplement(int N){
  if (N == 0) {
    return 0;
  }
  int todo=N;
  int bit=1;
  while (todo != 0) {
    N=N ^ bit;
    bit=bit << 1;
    todo=todo >> 1;
  }
  return N;
}
public static int bitwiseComplement_clone(int N)
{
if (((N == 0) && mark("N == 0", true, false)) || mark("N == 0", false, true))
{
{
mark("return 0;\n", false, false);
return 0;
}
}
mark("int todo=N;\n", false, false);
int todo=N;
mark("int bit=1;\n", false, false);
int bit=1;
while (((todo != 0) && mark("todo != 0", true, false)) || mark("todo != 0", false, true)) {
{
mark("N=N ^ bit;\n", false, false);
N=N ^ bit;
mark("bit=bit << 1;\n", false, false);
bit=bit << 1;
mark("todo=todo >> 1;\n", false, false);
todo=todo >> 1;
}
}
mark("return N;\n", false, false);
return N;
}

public static final int intbitwiseComplementintNTotalStatement = 9;
public static final int intbitwiseComplementintNTotalBranch = 4;
public static int xorOperation(int n,int start){
  int xor=start;
  for (int i=1; i < n; i++) {
    int nextNum=start + 2 * i;
    xor=xor ^ nextNum;
  }
  return xor;
}
public static int xorOperation_clone(int n, int start)
{
mark("int xor=start;\n", false, false);
int xor=start;
mark("int i=1", false, false);
for (int i=1; ((i < n) && mark("i < n", true, false)) || mark("i < n", false, true); mark("i++", false, false),
i++) {
{
mark("int nextNum=start + 2 * i;\n", false, false);
int nextNum=start + 2 * i;
mark("xor=xor ^ nextNum;\n", false, false);
xor=xor ^ nextNum;
}
}
mark("return xor;\n", false, false);
return xor;
}

public static final int intxorOperationintnintstartTotalStatement = 7;
public static final int intxorOperationintnintstartTotalBranch = 2;
public static final int BitSolutionsTotalStatement = 25;
}
