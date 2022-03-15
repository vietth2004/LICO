package com.example.xmlservice.ast.node;

import java.util.ArrayList;
import java.util.List;

public class StrutsNode extends Node {

    private List children = new ArrayList();

    public List getChildren() {
        return children;
    }

    public void setChildren(List children) {
        this.children = children;
    }

    public void addChildren(Object child) {
        children.add(child);
    }
}
