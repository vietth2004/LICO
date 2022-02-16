package com.example.strutservice.condition;

import com.example.strutservice.dom.Node;
import com.example.strutservice.dom.Xml.XmlTagNode;

public class XmlTagNodeCondition implements ICondition {

    @Override
    public boolean isSatisfiable(Node node) {
        return (node instanceof XmlTagNode);
    }
    
}
