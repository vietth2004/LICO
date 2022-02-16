package com.example.strutservice.condition;

import com.example.strutservice.dom.Jsp.JspFileNode;
import com.example.strutservice.dom.Node;

public class JspFileNodeCondition implements ICondition {
    @Override
    public boolean isSatisfiable(Node node) {
        if (node instanceof JspFileNode) {
            return true;
        }
        return false;
    }
}
