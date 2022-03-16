package com.example.strutsservice.condition;

import com.example.strutsservice.dom.Jsp.JspFileNode;
import com.example.strutsservice.dom.Node;

public class JspFileNodeCondition implements ICondition {
    @Override
    public boolean isSatisfiable(Node node) {
        if (node instanceof JspFileNode) {
            return true;
        }
        return false;
    }
}
