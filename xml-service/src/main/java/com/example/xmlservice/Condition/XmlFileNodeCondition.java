package com.example.xmlservice.Condition;

import com.example.xmlservice.Dom.Node;
import com.example.xmlservice.Dom.Xml.XmlFileNode;

public class XmlFileNodeCondition implements ICondition {
    @Override
    public boolean isSatisfiable(Node node) {
        if (node instanceof XmlFileNode) {
            return true;
        }
        return false;
    }
}
