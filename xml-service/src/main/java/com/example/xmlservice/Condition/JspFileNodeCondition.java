package com.example.xmlservice.Condition;

import com.example.xmlservice.Dom.Jsp.JspFileNode;
import com.example.xmlservice.Dom.Node;

public class JspFileNodeCondition implements ICondition {
    @Override
    public boolean isSatisfiable(Node node) {
        if (node instanceof JspFileNode) {
            return true;
        }
        return false;
    }
}
