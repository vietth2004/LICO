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

    public static int fibonacci(int n) {
        mark("int a=0, b=1, c, i;\n", false, false);
        int a = 0, b = 1, c, i;
        if (((n == 0) && mark("n == 0", true, false)) || mark("n == 0", false, true)) {
            mark("return a;\n", false, false);
            return a;
        }
        mark("i=2", false, false);
        for (i = 2; ((i <= n) && mark("i <= n", true, false)) || mark("i <= n", false, true); mark("i++", false, false),
                i++) {
            {
                mark("c=a + b;\n", false, false);
                c = a + b;
                mark("a=b;\n", false, false);
                a = b;
                mark("b=c;\n", false, false);
                b = c;
            }
        }
        mark("return b;\n", false, false);
        return b;
    }

    public static void main(String[] args) {
        writeDataToFile("", "core-engine/cfg/src/main/java/data/testDriverData/runTestDriverData.txt", false);
        long startRunTestTime = System.nanoTime();
        Object output = fibonacci(0);
        long endRunTestTime = System.nanoTime();
        double runTestDuration = (endRunTestTime - startRunTestTime) / 1000000.0;
        writeDataToFile(runTestDuration + "===" + output, "core-engine/cfg/src/main/java/data/testDriverData/runTestDriverData.txt", true);
    }
}