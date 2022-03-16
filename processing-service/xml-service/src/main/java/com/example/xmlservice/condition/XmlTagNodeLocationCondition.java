package com.example.xmlservice.condition;

import com.example.xmlservice.dom.Node;
import com.example.xmlservice.dom.Xml.XmlTagNode;

public class XmlTagNodeLocationCondition implements ICondition {

    private int lineNum;
    private int colNum;

    public XmlTagNodeLocationCondition(int lineNum, int colNum) {
        this.lineNum = lineNum;
        this.colNum = colNum;
    }

    @Override
    public boolean isSatisfiable(Node node) {
        if (node instanceof XmlTagNode) {
            XmlTagNode xmlTagNode = (XmlTagNode) node;
            return xmlTagNode.getLineNumber() == lineNum && xmlTagNode.getColumnNumber() == colNum;
        }
        return false;
    }
}