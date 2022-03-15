package com.example.jspservice.condition;

import com.example.jspservice.dom.Jsp.JspFileNode;
import com.example.jspservice.dom.Node;

public class JspFileNodeCondition implements ICondition {
    @Override
    public boolean isSatisfiable(Node node) {
        if (node instanceof JspFileNode) {
            return true;
        }
        return false;
    }
}
