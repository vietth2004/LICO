package com.example.xmlservice.condition;

import com.example.xmlservice.dom.Node;
import com.example.xmlservice.dom.Xml.XmlTagNode;

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