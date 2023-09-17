package com.example.unittesting.model.result.Concolic;

import java.util.ArrayList;
import java.util.List;

public class ConcolicTestData {
//    private String coverage = "0%";
    private List<String> coveredStatements = new ArrayList<>();
    private List<ConcolicParameterData> parameterDataList = new ArrayList<>();

    public ConcolicTestData(List<String> names, Class<?>[] types, Object[] values, List<String> coveredStatements) {
        if(names.size() != types.length || types.length != values.length) {
            throw new RuntimeException("Invalid");
        }

        for(int i = 0; i < names.size(); i++) {
            this.addToParameterDataList(new ConcolicParameterData(names.get(i), types[i].toString(), values[i].toString()));
        }

        this.coveredStatements = coveredStatements;
    }

//    public void setCoverage(String coverage) {
//        this.coverage = coverage;
//    }

    public void addToCoveredStatements(String statement) {
        coveredStatements.add(statement);
    }

    public void addToParameterDataList(ConcolicParameterData parameterData) {
        parameterDataList.add(parameterData);
    }

//    public String getCoverage() {
//        return coverage;
//    }

    public List<String> getCoveredStatements() {
        return coveredStatements;
    }

    public List<ConcolicParameterData> getParameterDataList() {
        return parameterDataList;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append(coveredStatements);

        for (int i = 0; i < parameterDataList.size(); i++) {
            result.append("Parameter no " + i + ": " + parameterDataList.get(i));
        }

        return result.toString();
    }
}
