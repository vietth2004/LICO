package com.example.jspservice.condition;

import com.example.jspservice.dom.Node;
import com.example.jspservice.dom.Xml.XmlTagNode;

public class XmlTagNodeWithContentCondition implements ICondition {

    private String textContent;

    public XmlTagNodeWithContentCondition(String textContent) {
        this.textContent = textContent;
    }

    @Override
    public boolean isSatisfiable(Node node) {
        if (node instanceof XmlTagNode) {
            String tagTextContent = ((XmlTagNode) node).getContent();
            return (tagTextContent != null && tagTextContent.trim().equals(textContent));
        }
        return false;
    }

}