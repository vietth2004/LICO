package com.jcia.xmlservice.Condition;

import com.jcia.xmlservice.Dom.Jsp.JspFileNode;
import com.jcia.xmlservice.Dom.Node;

public class JspFileNodeCondition implements ICondition {
    @Override
    public boolean isSatisfiable(Node node) {
        if (node instanceof JspFileNode) {
            return true;
        }
        return false;
    }
}
