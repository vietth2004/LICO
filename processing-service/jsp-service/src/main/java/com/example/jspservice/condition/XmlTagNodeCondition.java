package com.example.jspservice.condition;

import com.example.jspservice.dom.Node;
import com.example.jspservice.dom.Xml.XmlTagNode;

public class XmlTagNodeCondition implements ICondition {

    @Override
    public boolean isSatisfiable(Node node) {
        return (node instanceof XmlTagNode);
    }

}
