package com.example.xmlservice.condition;

import com.example.xmlservice.dom.Node;
import com.example.xmlservice.dom.Xml.XmlFileNode;

public class XmlFileNodeCondition implements ICondition {
    @Override
    public boolean isSatisfiable(Node node) {
        if (node instanceof XmlFileNode) {
            return true;
        }
        return false;
    }
}
