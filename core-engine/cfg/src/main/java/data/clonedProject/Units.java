package data.clonedProject;
import java.io.FileWriter;
public class Units {
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
public static boolean isPrime(int x){
  if (x <= 1)   return false;
  for (int i=2; i <= Math.sqrt(x); i++) {
    if (x % i == 0)     return false;
  }
  return true;
}
public static boolean isPrime_clone(int x)
{
if (((x <= 1) && mark("x <= 1", true, false)) || mark("x <= 1", false, true))
{
mark("return false;\n", false, false);
return false;
}
mark("int i=2", false, false);
for (int i=2; ((i <= Math.sqrt(x)) && mark("i <= Math.sqrt(x)", true, false)) || mark("i <= Math.sqrt(x)", false, true); mark("i++", false, false),
i++) {
{
if (((x % i == 0) && mark("x % i == 0", true, false)) || mark("x % i == 0", false, true))
{
mark("return false;\n", false, false);
return false;
}
}
}
mark("return true;\n", false, false);
return true;
}

public static final int booleanisPrimeintxTotalStatement = 8;
public static final int booleanisPrimeintxTotalBranch = 6;
public static int checkTriangleType(int a,int b,int c){
  if (a + b <= c || a + c <= b || b + c <= a) {
    return -1;
  }
  if (a == b && b == c) {
    return 3;
  }
 else   if (a == b || b == c || a == c) {
    return 2;
  }
 else {
    int maxBC=Math.max(b,c);
    int hypotenuse=Math.max(a,maxBC);
    int minAB=Math.min(a,b);
    int side1=Math.min(minAB,c);
    int side2=a + b + c - hypotenuse - side1;
    double hypotenuseSquared=Math.pow(hypotenuse,2);
    double side1Squared=Math.pow(side1,2);
    double side2Squared=Math.pow(side2,2);
    if (hypotenuseSquared - (side1Squared + side2Squared) < 0.0001) {
      return 1;
    }
  }
  return 0;
}
public static int checkTriangleType_clone(int a, int b, int c)
{
if (((a + b <= c || a + c <= b || b + c <= a) && mark("a + b <= c || a + c <= b || b + c <= a", true, false)) || mark("a + b <= c || a + c <= b || b + c <= a", false, true))
{
{
mark("return -1;\n", false, false);
return -1;
}
}
if (((a == b && b == c) && mark("a == b && b == c", true, false)) || mark("a == b && b == c", false, true))
{
{
mark("return 3;\n", false, false);
return 3;
}
}
else {
if (((a == b || b == c || a == c) && mark("a == b || b == c || a == c", true, false)) || mark("a == b || b == c || a == c", false, true))
{
{
mark("return 2;\n", false, false);
return 2;
}
}
else {
{
mark("int maxBC=Math.max(b,c);\n", false, false);
int maxBC=Math.max(b,c);
mark("int hypotenuse=Math.max(a,maxBC);\n", false, false);
int hypotenuse=Math.max(a,maxBC);
mark("int minAB=Math.min(a,b);\n", false, false);
int minAB=Math.min(a,b);
mark("int side1=Math.min(minAB,c);\n", false, false);
int side1=Math.min(minAB,c);
mark("int side2=a + b + c - hypotenuse - side1;\n", false, false);
int side2=a + b + c - hypotenuse - side1;
mark("double hypotenuseSquared=Math.pow(hypotenuse,2);\n", false, false);
double hypotenuseSquared=Math.pow(hypotenuse,2);
mark("double side1Squared=Math.pow(side1,2);\n", false, false);
double side1Squared=Math.pow(side1,2);
mark("double side2Squared=Math.pow(side2,2);\n", false, false);
double side2Squared=Math.pow(side2,2);
if (((hypotenuseSquared - (side1Squared + side2Squared) < 0.0001) && mark("hypotenuseSquared - (side1Squared + side2Squared) < 0.0001", true, false)) || mark("hypotenuseSquared - (side1Squared + side2Squared) < 0.0001", false, true))
{
{
mark("return 1;\n", false, false);
return 1;
}
}
}
}
}
mark("return 0;\n", false, false);
return 0;
}

public static final int intcheckTriangleTypeintaintbintcTotalStatement = 17;
public static final int intcheckTriangleTypeintaintbintcTotalBranch = 8;
public static long sumOfPrimesBelow(int n){
  long sum=0;
  for (double num=2; num < n; num++) {
    boolean isPrime=true;
    for (int i=2; i <= Math.sqrt(num); i++) {
      if (num % i == 0) {
        isPrime=false;
        break;
      }
    }
    if (isPrime) {
      sum+=num;
    }
  }
  return sum;
}
public static long sumOfPrimesBelow_clone(int n)
{
mark("long sum=0;\n", false, false);
long sum=0;
mark("double num=2", false, false);
for (double num=2; ((num < n) && mark("num < n", true, false)) || mark("num < n", false, true); mark("num++", false, false),
num++) {
{
mark("boolean isPrime=true;\n", false, false);
boolean isPrime=true;
mark("int i=2", false, false);
for (int i=2; ((i <= Math.sqrt(num)) && mark("i <= Math.sqrt(num)", true, false)) || mark("i <= Math.sqrt(num)", false, true); mark("i++", false, false),
i++) {
{
if (((num % i == 0) && mark("num % i == 0", true, false)) || mark("num % i == 0", false, true))
{
{
mark("isPrime=false;\n", false, false);
isPrime=false;
mark("break;\n", false, false);
break;
}
}
}
}
if (((isPrime) && mark("isPrime", true, false)) || mark("isPrime", false, true))
{
{
mark("sum+=num;\n", false, false);
sum+=num;
}
}
}
}
mark("return sum;\n", false, false);
return sum;
}

public static final int longsumOfPrimesBelowintnTotalStatement = 14;
public static final int longsumOfPrimesBelowintnTotalBranch = 8;
public static boolean isPerfectNumber(double num){
  if (num <= 1)   return false;
  int sum=1;
  for (int i=2; i <= Math.sqrt(num); i++) {
    if (num % i == 0) {
      sum+=i;
      if (i != num / i)       sum+=num / i;
    }
  }
  return sum == num;
}
public static boolean isPerfectNumber_clone(double num)
{
if (((num <= 1) && mark("num <= 1", true, false)) || mark("num <= 1", false, true))
{
mark("return false;\n", false, false);
return false;
}
mark("int sum=1;\n", false, false);
int sum=1;
mark("int i=2", false, false);
for (int i=2; ((i <= Math.sqrt(num)) && mark("i <= Math.sqrt(num)", true, false)) || mark("i <= Math.sqrt(num)", false, true); mark("i++", false, false),
i++) {
{
if (((num % i == 0) && mark("num % i == 0", true, false)) || mark("num % i == 0", false, true))
{
{
mark("sum+=i;\n", false, false);
sum+=i;
if (((i != num / i) && mark("i != num / i", true, false)) || mark("i != num / i", false, true))
{
mark("sum+=num / i;\n", false, false);
sum+=num / i;
}
}
}
}
}
mark("return sum == num;\n", false, false);
return sum == num;
}

public static final int booleanisPerfectNumberdoublenumTotalStatement = 11;
public static final int booleanisPerfectNumberdoublenumTotalBranch = 8;
public static int countCharacterTypes(char input){
  int uppercaseCount=0;
  int lowercaseCount=0;
  int specialCount=0;
  if (Character.isUpperCase(input)) {
    uppercaseCount++;
  }
 else   if (Character.isLowerCase(input)) {
    lowercaseCount++;
  }
 else   if (!Character.isLetter(input) && !Character.isDigit(input)) {
    specialCount++;
  }
  return uppercaseCount + lowercaseCount + specialCount;
}
public static int countCharacterTypes_clone(char input)
{
mark("int uppercaseCount=0;\n", false, false);
int uppercaseCount=0;
mark("int lowercaseCount=0;\n", false, false);
int lowercaseCount=0;
mark("int specialCount=0;\n", false, false);
int specialCount=0;
if (((Character.isUpperCase(input)) && mark("Character.isUpperCase(input)", true, false)) || mark("Character.isUpperCase(input)", false, true))
{
{
mark("uppercaseCount++;\n", false, false);
uppercaseCount++;
}
}
else {
if (((Character.isLowerCase(input)) && mark("Character.isLowerCase(input)", true, false)) || mark("Character.isLowerCase(input)", false, true))
{
{
mark("lowercaseCount++;\n", false, false);
lowercaseCount++;
}
}
else {
if (((!Character.isLetter(input) && !Character.isDigit(input)) && mark("!Character.isLetter(input) && !Character.isDigit(input)", true, false)) || mark("!Character.isLetter(input) && !Character.isDigit(input)", false, true))
{
{
mark("specialCount++;\n", false, false);
specialCount++;
}
}
}
}
mark("return uppercaseCount + lowercaseCount + specialCount;\n", false, false);
return uppercaseCount + lowercaseCount + specialCount;
}

public static final int intcountCharacterTypescharinputTotalStatement = 10;
public static final int intcountCharacterTypescharinputTotalBranch = 6;
public static char reverseCase(char ch){
  if (Character.isUpperCase(ch)) {
    return Character.toLowerCase(ch);
  }
 else   if (Character.isLowerCase(ch)) {
    return Character.toUpperCase(ch);
  }
  return ch;
}
public static char reverseCase_clone(char ch)
{
if (((Character.isUpperCase(ch)) && mark("Character.isUpperCase(ch)", true, false)) || mark("Character.isUpperCase(ch)", false, true))
{
{
mark("return Character.toLowerCase(ch);\n", false, false);
return Character.toLowerCase(ch);
}
}
else {
if (((Character.isLowerCase(ch)) && mark("Character.isLowerCase(ch)", true, false)) || mark("Character.isLowerCase(ch)", false, true))
{
{
mark("return Character.toUpperCase(ch);\n", false, false);
return Character.toUpperCase(ch);
}
}
}
mark("return ch;\n", false, false);
return ch;
}

public static final int charreverseCasecharchTotalStatement = 5;
public static final int charreverseCasecharchTotalBranch = 4;
public static final int UnitsTotalStatement = 65;
}
