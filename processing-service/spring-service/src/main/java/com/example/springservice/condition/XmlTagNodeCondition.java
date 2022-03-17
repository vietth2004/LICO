package com.example.springservice.condition;

import com.example.springservice.dom.Node;
import com.example.springservice.dom.Xml.XmlTagNode;

public class XmlTagNodeCondition implements ICondition {

    @Override
    public boolean isSatisfiable(Node node) {
        return (node instanceof XmlTagNode);
    }
    
}
