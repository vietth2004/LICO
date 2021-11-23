package com.example.xmlservice.Condition;

import com.example.xmlservice.Dom.Node;
import com.example.xmlservice.Dom.Xml.XmlTagNode;

public class XmlTagNodeCondition implements ICondition {

    @Override
    public boolean isSatisfiable(Node node) {
        return (node instanceof XmlTagNode);
    }
    
}
