package com.example.xmlservice.tiles.parser;

/**
 * Created by cuong on 5/18/2017.
 */
public class JciaLocator {

    private int lineNumber;
    private int columnNumber;

    public JciaLocator(int lineNumber, int columnNumber) {
        setLineNumber(lineNumber);
        setColumnNumber(columnNumber);
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }
}
