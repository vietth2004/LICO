package com.example.springservice.condition;

import com.example.springservice.dom.Jsp.JspFileNode;
import com.example.springservice.dom.Node;

public class JspFileNodeCondition implements ICondition {
    @Override
    public boolean isSatisfiable(Node node) {
        if (node instanceof JspFileNode) {
            return true;
        }
        return false;
    }
}
