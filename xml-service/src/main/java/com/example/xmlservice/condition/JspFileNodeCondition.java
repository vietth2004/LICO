package com.example.xmlservice.condition;

import com.example.xmlservice.dom.Jsp.JspFileNode;
import com.example.xmlservice.dom.Node;

public class JspFileNodeCondition implements ICondition {
    @Override
    public boolean isSatisfiable(Node node) {
        if (node instanceof JspFileNode) {
            return true;
        }
        return false;
    }
}
