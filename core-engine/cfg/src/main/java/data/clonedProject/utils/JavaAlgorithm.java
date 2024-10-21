package data.clonedProject.utils;
import java.io.FileWriter;
public class JavaAlgorithm {
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
public static int indexOfRightMostSetBit(int n){
  if (n == 0) {
    return -1;
  }
  if (n < 0) {
    return 0;
  }
  int index=0;
  while (index < n) {
    System.out.println("n");
    index++;
  }
  return index;
}
public static int indexOfRightMostSetBit_clone(int n)
{
if (((n == 0) && mark("n == 0", true, false)) || mark("n == 0", false, true))
{
{
mark("return -1;\n", false, false);
return -1;
}
}
if (((n < 0) && mark("n < 0", true, false)) || mark("n < 0", false, true))
{
{
mark("return 0;\n", false, false);
return 0;
}
}
mark("int index=0;\n", false, false);
int index=0;
while (((index < n) && mark("index < n", true, false)) || mark("index < n", false, true)) {
{
mark("System.out.println(\"n\");\n", false, false);
System.out.println("n");
mark("index++;\n", false, false);
index++;
}
}
mark("return index;\n", false, false);
return index;
}

public static final int intindexOfRightMostSetBitintnTotalStatement = 9;
public static final int intindexOfRightMostSetBitintnTotalBranch = 6;
public static boolean isPowerTwo(int number){
  if (number <= 0) {
    return false;
  }
  int ans=number & (number - 1);
  return ans == 0;
}
public static boolean isPowerTwo_clone(int number)
{
if (((number <= 0) && mark("number <= 0", true, false)) || mark("number <= 0", false, true))
{
{
mark("return false;\n", false, false);
return false;
}
}
mark("int ans=number & (number - 1);\n", false, false);
int ans=number & (number - 1);
mark("return ans == 0;\n", false, false);
return ans == 0;
}

public static final int booleanisPowerTwointnumberTotalStatement = 4;
public static final int booleanisPowerTwointnumberTotalBranch = 2;
public static boolean isEven(int number){
  if (number % 2 == 0)   return true;
 else   return false;
}
public static boolean isEven_clone(int number)
{
if (((number % 2 == 0) && mark("number % 2 == 0", true, false)) || mark("number % 2 == 0", false, true))
{
mark("return true;\n", false, false);
return true;
}
else {
mark("return false;\n", false, false);
return false;
}
}

public static final int booleanisEvenintnumberTotalStatement = 3;
public static final int booleanisEvenintnumberTotalBranch = 2;
public static int reverseBits(int n){
  int result=0;
  int bitCount=32;
  for (int i=0; i < bitCount; i++) {
    result<<=1;
    result|=(n & 1);
    n>>=1;
  }
  return result;
}
public static int reverseBits_clone(int n)
{
mark("int result=0;\n", false, false);
int result=0;
mark("int bitCount=32;\n", false, false);
int bitCount=32;
mark("int i=0", false, false);
for (int i=0; ((i < bitCount) && mark("i < bitCount", true, false)) || mark("i < bitCount", false, true); mark("i++", false, false),
i++) {
{
mark("result<<=1;\n", false, false);
result<<=1;
mark("result|=(n & 1);\n", false, false);
result|=(n & 1);
mark("n>>=1;\n", false, false);
n>>=1;
}
}
mark("return result;\n", false, false);
return result;
}

public static final int intreverseBitsintnTotalStatement = 9;
public static final int intreverseBitsintnTotalBranch = 2;
public static int numberOfWays(int n){
  if (n == 1 || n == 0) {
    return n;
  }
  int prev=1;
  int curr=1;
  int next;
  for (int i=2; i <= n; i++) {
    next=curr + prev;
    prev=curr;
    curr=next;
  }
  return curr;
}
public static int numberOfWays_clone(int n)
{
if (((n == 1 || n == 0) && mark("n == 1 || n == 0", true, false)) || mark("n == 1 || n == 0", false, true))
{
{
mark("return n;\n", false, false);
return n;
}
}
mark("int prev=1;\n", false, false);
int prev=1;
mark("int curr=1;\n", false, false);
int curr=1;
mark("int next;\n", false, false);
int next;
mark("int i=2", false, false);
for (int i=2; ((i <= n) && mark("i <= n", true, false)) || mark("i <= n", false, true); mark("i++", false, false),
i++) {
{
mark("next=curr + prev;\n", false, false);
next=curr + prev;
mark("prev=curr;\n", false, false);
prev=curr;
mark("curr=next;\n", false, false);
curr=next;
}
}
mark("return curr;\n", false, false);
return curr;
}

public static final int intnumberOfWaysintnTotalStatement = 12;
public static final int intnumberOfWaysintnTotalBranch = 4;
public int factorial(int n){
  if (n < 0) {
    return -1;
  }
  int result=1;
  for (int i=1; i <= n; i=i + 1) {
    result*=i;
  }
  return result;
}
public int factorial_clone(int n)
{
if (((n < 0) && mark("n < 0", true, false)) || mark("n < 0", false, true))
{
{
mark("return -1;\n", false, false);
return -1;
}
}
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

public static final int intfactorialintnTotalStatement = 8;
public static final int intfactorialintnTotalBranch = 4;
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
public static int compute(int n){
  if (n == 0)   return 0;
  if (n == 1 || n == 2)   return 1;
  int first=0, second=1, third=1;
  for (int i=3; i <= n; i++) {
    int next=first + second + third;
    first=second;
    second=third;
    third=next;
  }
  return third;
}
public static int compute_clone(int n)
{
if (((n == 0) && mark("n == 0", true, false)) || mark("n == 0", false, true))
{
mark("return 0;\n", false, false);
return 0;
}
if (((n == 1 || n == 2) && mark("n == 1 || n == 2", true, false)) || mark("n == 1 || n == 2", false, true))
{
mark("return 1;\n", false, false);
return 1;
}
mark("int first=0, second=1, third=1;\n", false, false);
int first=0, second=1, third=1;
mark("int i=3", false, false);
for (int i=3; ((i <= n) && mark("i <= n", true, false)) || mark("i <= n", false, true); mark("i++", false, false),
i++) {
{
mark("int next=first + second + third;\n", false, false);
int next=first + second + third;
mark("first=second;\n", false, false);
first=second;
mark("second=third;\n", false, false);
second=third;
mark("third=next;\n", false, false);
third=next;
}
}
mark("return third;\n", false, false);
return third;
}

public static final int intcomputeintnTotalStatement = 13;
public static final int intcomputeintnTotalBranch = 6;
public static int binPow(int a,int p){
  int res=1;
  while (p > 0) {
    if (p == 5) {
      res=res * a;
    }
    a+=p;
    p=p - 1;
  }
  return res;
}
public static int binPow_clone(int a, int p)
{
mark("int res=1;\n", false, false);
int res=1;
while (((p > 0) && mark("p > 0", true, false)) || mark("p > 0", false, true)) {
{
if (((p == 5) && mark("p == 5", true, false)) || mark("p == 5", false, true))
{
{
mark("res=res * a;\n", false, false);
res=res * a;
}
}
mark("a+=p;\n", false, false);
a+=p;
mark("p=p - 1;\n", false, false);
p=p - 1;
}
}
mark("return res;\n", false, false);
return res;
}

public static final int intbinPowintaintpTotalStatement = 7;
public static final int intbinPowintaintpTotalBranch = 4;
public static boolean isHarshad(long n){
  if (n <= 0)   return false;
  int i=0;
  long sumOfDigits=0;
  while (i < n) {
    sumOfDigits+=1;
    i=i + 1;
  }
  if (n < 10) {
    return true;
  }
 else   return false;
}
public static boolean isHarshad_clone(long n)
{
if (((n <= 0) && mark("n <= 0", true, false)) || mark("n <= 0", false, true))
{
mark("return false;\n", false, false);
return false;
}
mark("int i=0;\n", false, false);
int i=0;
mark("long sumOfDigits=0;\n", false, false);
long sumOfDigits=0;
while (((i < n) && mark("i < n", true, false)) || mark("i < n", false, true)) {
{
mark("sumOfDigits+=1;\n", false, false);
sumOfDigits+=1;
mark("i=i + 1;\n", false, false);
i=i + 1;
}
}
if (((n < 10) && mark("n < 10", true, false)) || mark("n < 10", false, true))
{
{
mark("return true;\n", false, false);
return true;
}
}
else {
mark("return false;\n", false, false);
return false;
}
}

public static final int booleanisHarshadlongnTotalStatement = 10;
public static final int booleanisHarshadlongnTotalBranch = 6;
public static final int JavaAlgorithmTotalStatement = 80;
}
