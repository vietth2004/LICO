package com.example.xmlservice.condition;

import com.example.xmlservice.dom.Node;
import com.example.xmlservice.dom.Xml.XmlTagNode;

public class XmlTagNodeCondition implements ICondition {

    @Override
    public boolean isSatisfiable(Node node) {
        return (node instanceof XmlTagNode);
    }
    
}
