package data;import static core.dataStructure.MarkedPath.markOneStatement;
public class CloneFile {
public static boolean isPerfectNumber(int number)
{
if (((number <= 0) && markOneStatement("number <= 0", true, false)) || markOneStatement("number <= 0", false, true))
{
{
markOneStatement("return false;\n", false, false);
return false;
}
}
markOneStatement("int sum=0;\n", false, false);
int sum=0;
markOneStatement("int i=1", false, false);
for (int i=1; ((i < number) && markOneStatement("i < number", true, false)) || markOneStatement("i < number", false, true); markOneStatement("i++", false, false),
i++) {
{
if (((number % i == 0) && markOneStatement("number % i == 0", true, false)) || markOneStatement("number % i == 0", false, true))
{
{
markOneStatement("sum+=i;\n", false, false);
sum+=i;
}
}
}
}
markOneStatement("return sum == number;\n", false, false);
return sum == number;
}
}
