package com.example.unittesting.model.result.Concolic;

import java.util.ArrayList;
import java.util.List;

public class ConcolicTestResult {
    //    private String fullCoverage = "0%";
//    private List<String> fullCoveredStatements = new ArrayList<>();
    private List<ConcolicTestData> fullTestData = new ArrayList<>();

//    public void setFullCoverage(String fullCoverage) {
//        this.fullCoverage = fullCoverage;
//    }
//
//    public void addToFullCoveredStatements(String statement) {
//        fullCoveredStatements.add(statement);
//    }

    public void addToFullTestData(ConcolicTestData testData) {
        fullTestData.add(testData);
    }

//    public String getFullCoverage() {
//        return fullCoverage;
//    }
//
//    public List<String> getFullCoveredStatements() {
//        return fullCoveredStatements;
//    }

    public List<ConcolicTestData> getFullTestData() {
        return fullTestData;
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
