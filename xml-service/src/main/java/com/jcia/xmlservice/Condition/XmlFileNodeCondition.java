package com.jcia.xmlservice.Condition;

import com.jcia.xmlservice.Dom.Node;
import com.jcia.xmlservice.Dom.Xml.XmlFileNode;

public class XmlFileNodeCondition implements ICondition {
    @Override
    public boolean isSatisfiable(Node node) {
        if (node instanceof XmlFileNode) {
            return true;
        }
        return false;
    }
}
