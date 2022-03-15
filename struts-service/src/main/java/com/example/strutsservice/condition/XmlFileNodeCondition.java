package com.example.strutsservice.condition;

import com.example.strutsservice.dom.Node;
import com.example.strutsservice.dom.Xml.XmlFileNode;

public class XmlFileNodeCondition implements ICondition {
    @Override
    public boolean isSatisfiable(Node node) {
        if (node instanceof XmlFileNode) {
            return true;
        }
        return false;
    }
}
