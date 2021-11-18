package com.jcia.xmlservice.Condition;

import com.jcia.xmlservice.Dom.Node;
import com.jcia.xmlservice.Dom.Xml.XmlTagNode;

public class XmlTagNodeCondition implements ICondition {

    @Override
    public boolean isSatisfiable(Node node) {
        return (node instanceof XmlTagNode);
    }
    
}
