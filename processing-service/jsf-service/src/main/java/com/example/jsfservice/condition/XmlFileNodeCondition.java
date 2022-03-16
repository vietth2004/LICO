package com.example.jsfservice.condition;

import com.example.jsfservice.dom.Node;
import com.example.jsfservice.dom.Xml.XmlFileNode;

public class XmlFileNodeCondition implements ICondition {
    @Override
    public boolean isSatisfiable(Node node) {
        if (node instanceof XmlFileNode) {
            return true;
        }
        return false;
    }
}
