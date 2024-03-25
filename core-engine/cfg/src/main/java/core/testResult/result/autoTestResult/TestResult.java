package core.testResult.result.autoTestResult;

import java.util.ArrayList;
import java.util.List;

public class TestResult {
    private List<TestData> fullTestData = new ArrayList<>();
    private double fullCoverage = 0;

    public void addToFullTestData(TestData testData) {
        fullTestData.add(testData);
    }

    public List<TestData> getFullTestData() {
        return fullTestData;
    }

    public double getFullCoverage() {
        return fullCoverage;
    }

    public void setFullCoverage(double fullCoverage) {
        this.fullCoverage = (double) Math.round(fullCoverage * 100) / 100;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < fullTestData.size(); i++) {
            result.append("Test no " + i + ": " + fullTestData.get(i));
        }

        return result.toString();
    }
}
