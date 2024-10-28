package data.testDriverData;

import java.io.FileWriter;

public class TestDriver {
    private static boolean mark(String statement, boolean isTrueCondition, boolean isFalseCondition) {
        StringBuilder markResult = new StringBuilder();
        markResult.append(statement).append("===");
        markResult.append(isTrueCondition).append("===");
        markResult.append(isFalseCondition).append("---end---");
        writeDataToFile(markResult.toString(), "core-engine/cfg/src/main/java/data/testDriverData/runTestDriverData.txt", true);
        if (!isTrueCondition && !isFalseCondition) return true;
        return !isFalseCondition;
    }

    private static void writeDataToFile(String data, String path, boolean append) {
        try {
            FileWriter writer = new FileWriter(path, append);
            writer.write(data);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isPerfectNumber(double num) {
        if (((num <= 1) && mark("num <= 1", true, false)) || mark("num <= 1", false, true)) {
            mark("return false;\n", false, false);
            return false;
        }
        mark("int sum=1;\n", false, false);
        int sum = 1;
        mark("int i=2", false, false);
        for (int i = 2; ((i <= Math.sqrt(num)) && mark("i <= Math.sqrt(num)", true, false)) || mark("i <= Math.sqrt(num)", false, true); mark("i++", false, false),
                i++) {
            {
                if (((num % i == 0) && mark("num % i == 0", true, false)) || mark("num % i == 0", false, true)) {
                    {
                        mark("sum+=i;\n", false, false);
                        sum += i;
                        if (((i != num / i) && mark("i != num / i", true, false)) || mark("i != num / i", false, true)) {
                            mark("sum+=num / i;\n", false, false);
                            sum += num / i;
                        }
                    }
                }
            }
        }
        mark("return sum == num;\n", false, false);
        return sum == num;
    }

    public static void main(String[] args) {
        writeDataToFile("", "core-engine/cfg/src/main/java/data/testDriverData/runTestDriverData.txt", false);
        long startRunTestTime = System.nanoTime();
        Object output = isPerfectNumber(0.0);
        long endRunTestTime = System.nanoTime();
        double runTestDuration = (endRunTestTime - startRunTestTime) / 1000000.0;
        writeDataToFile(runTestDuration + "===" + output, "core-engine/cfg/src/main/java/data/testDriverData/runTestDriverData.txt", true);
    }
}