package com.example.unittesting.model.result.Concolic;

import com.example.unittesting.model.coveredStatement.CoveredStatement;

import java.util.ArrayList;
import java.util.List;

public class ConcolicTestData {
//    private String coverage = "0%";
    private List<CoveredStatement> coveredStatements = new ArrayList<>();
    private List<ConcolicParameterData> parameterDataList = new ArrayList<>();
    private Object output;

    public ConcolicTestData(List<String> names, Class<?>[] types, Object[] values, List<CoveredStatement> coveredStatements, Object output) {
        if(names.size() != types.length || types.length != values.length) {
            throw new RuntimeException("Invalid");
        }

        for(int i = 0; i < names.size(); i++) {
            this.addToParameterDataList(new ConcolicParameterData(names.get(i), types[i].toString(), values[i].toString()));
        }

        this.coveredStatements = coveredStatements;

        this.output = output;
    }

//    public void setCoverage(String coverage) {
//        this.coverage = coverage;
//    }

    public void addToCoveredStatements(CoveredStatement statement) {
        coveredStatements.add(statement);
    }

    public void addToParameterDataList(ConcolicParameterData parameterData) {
        parameterDataList.add(parameterData);
    }

//    public String getCoverage() {
//        return coverage;
//    }

    public List<CoveredStatement> getCoveredStatements() {
        return coveredStatements;
    }

    public List<ConcolicParameterData> getParameterDataList() {
        return parameterDataList;
    }

    public Object getOutput() {
        return output;
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
