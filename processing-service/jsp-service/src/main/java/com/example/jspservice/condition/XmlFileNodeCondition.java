package com.example.jspservice.condition;

import com.example.jspservice.dom.Node;
import com.example.jspservice.dom.Xml.XmlFileNode;

public class XmlFileNodeCondition implements ICondition {
    @Override
    public boolean isSatisfiable(Node node) {
        if (node instanceof XmlFileNode) {
            return true;
        }
        return false;
    }
}
