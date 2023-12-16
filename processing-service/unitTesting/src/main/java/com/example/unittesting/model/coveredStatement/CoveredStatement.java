package com.example.unittesting.model.coveredStatement;

import core.cfg.CfgNode;
import core.dataStructure.MarkedStatement;

import java.util.ArrayList;
import java.util.List;

public class CoveredStatement {
    private String statementContent = "";
    private int lineNumber = 0;

    private String conditionStatus = "";

    public CoveredStatement(String statementContent, int lineNumber) {
        this.statementContent = statementContent;
        this.lineNumber = lineNumber;
    }

    public static List<CoveredStatement> switchToCoveredStatementList(List<MarkedStatement> markedStatements) {
        List<CoveredStatement> coveredStatements = new ArrayList<>();

        for(MarkedStatement markedStatement : markedStatements) {
            CfgNode cfgNode = markedStatement.getCfgNode();
            CoveredStatement coveredStatement = new CoveredStatement(cfgNode.getContent(), cfgNode.getLineNumber());

            if(markedStatement.isTrueConditionalStatement()) {
                coveredStatement.conditionStatus = "true";
            } else if (markedStatement.isFalseConditionalStatement()) {
                coveredStatement.conditionStatus = "false";
            }

            coveredStatements.add(coveredStatement);
        }

        return coveredStatements;
    }

    public String getStatementContent() {
        return statementContent;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getConditionStatus() {
        return conditionStatus;
    }

    @Override
    public String toString() {
        return lineNumber + " " + statementContent + " " + conditionStatus;
    }
}
