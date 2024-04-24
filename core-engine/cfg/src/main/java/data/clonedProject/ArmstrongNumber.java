package data.clonedProject;

import java.io.FileWriter;

public class ArmstrongNumber {
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

    public boolean isArmstrong(int N) {
        mark("int n=getLength(N);\n", false, false);
        int n = getLength(N);
        mark("return getNthPowerSum(N,n) == N;\n", false, false);
        return getNthPowerSum(N, n) == N;
    }

    public static final int booleanisArmstrongintNTotalStatement = 2;
    public static final int booleanisArmstrongintNTotalBranch = 0;

    private int getNthPowerSum(int n, int p) {
        mark("int sum=0;\n", false, false);
        int sum = 0;
        while (((n > 0) && mark("n > 0", true, false)) || mark("n > 0", false, true)) {
            {
                mark("int temp=n % 10;\n", false, false);
                int temp = n % 10;
                mark("n/=10;\n", false, false);
                n /= 10;
                mark("sum+=(int)Math.pow(temp,p);\n", false, false);
                sum += (int) Math.pow(temp, p);
            }
        }
        mark("return sum;\n", false, false);
        return sum;
    }

    public static final int intgetNthPowerSumintnintpTotalStatement = 6;
    public static final int intgetNthPowerSumintnintpTotalBranch = 2;

    public static int getLength(int n) {
        mark("int count=0;\n", false, false);
        int count = 0;
        while (((n > 0) && mark("n > 0", true, false)) || mark("n > 0", false, true)) {
            {
                mark("n/=10;\n", false, false);
                n /= 10;
                mark("count++;\n", false, false);
                count++;
            }
        }
        mark("return count;\n", false, false);
        return count;
    }

    public static final int intgetLengthintnTotalStatement = 5;
    public static final int intgetLengthintnTotalBranch = 2;
    public static final int ArmstrongNumberTotalStatement = 13;
}
