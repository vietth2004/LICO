package com.example.strutservice.condition;

import com.example.strutservice.dom.Node;
import com.example.strutservice.dom.Xml.XmlFileNode;

public class XmlFileNodeCondition implements ICondition {
    @Override
    public boolean isSatisfiable(Node node) {
        if (node instanceof XmlFileNode) {
            return true;
        }
        return false;
    }
}
