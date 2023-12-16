package core.testDriver;

import core.cmd.CommandLine;
import core.dataStructure.MarkedStatement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class TestDriverRunner {
    private static double runtime;
    private static String output;
    private TestDriverRunner() {
    }

    public static List<MarkedStatement> runTestDriver(String testDriver) throws IOException, InterruptedException {
        writeDataToFile(testDriver, "core-engine/cfg/src/main/java/data/testDriverData/TestDriver.java");

        CommandLine.executeCommand("javac core-engine/cfg/src/main/java/data/testDriverData/TestDriver.java");
        CommandLine.executeCommand("java core-engine/cfg/src/main/java/data/testDriverData/TestDriver.java");

        return getMarkedStatement();
    }

    private static List<MarkedStatement> getMarkedStatement() {
        List<MarkedStatement> result = new ArrayList<>();

        String markedData = getDataFromFile("core-engine/cfg/src/main/java/data/testDriverData/runTestDriverData.txt");
        String[] markedStatements = markedData.split("---end---");
        for (int i = 0; i < markedStatements.length; i++) {
            String[] markedStatementData = markedStatements[i].split("===");
            if(i == markedStatements.length - 1) {
                runtime = Double.parseDouble(markedStatementData[0]);
                output = markedStatementData[1];
            } else {
                String statement = markedStatementData[0];
                boolean isTrueConditionalStatement = Boolean.parseBoolean(markedStatementData[1]);
                boolean isFalseConditionalStatement = Boolean.parseBoolean(markedStatementData[2]);
                result.add(new MarkedStatement(statement, isTrueConditionalStatement, isFalseConditionalStatement));
            }
        }

        return result;
    }

    public static double getRuntime() {
        return runtime;
    }

    public static String getOutput() {
        return output;
    }

    private static String getDataFromFile(String path) {
        StringBuilder result = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            if((line = br.readLine()) != null) {
                result.append(line);
            }
            while ((line = br.readLine()) != null) {
                result.append("\n").append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    private static void writeDataToFile(String data, String path) {
        try {
            FileWriter writer = new FileWriter(path);
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
