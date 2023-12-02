package com.example.jsfservice.condition;

import com.example.jsfservice.dom.Node;
import com.example.jsfservice.dom.Xml.XmlTagNode;

public class XmlTagNodeCondition implements ICondition {

    @Override
    public boolean isSatisfiable(Node node) {
        return (node instanceof XmlTagNode);
    }

}
