package com.example.jsfservice.condition;

import com.example.jsfservice.dom.Jsp.JspFileNode;
import com.example.jsfservice.dom.Node;

public class JspFileNodeCondition implements ICondition {
    @Override
    public boolean isSatisfiable(Node node) {
        if (node instanceof JspFileNode) {
            return true;
        }
        return false;
    }
}
