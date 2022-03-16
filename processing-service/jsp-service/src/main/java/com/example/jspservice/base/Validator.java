package com.example.jspservice.base;

import com.example.jspservice.dom.Node;

/**
 * Created by locdt on 09/12/2017.
 */
public abstract class Validator implements Supportable {
    protected Node[] candidates;

    public Validator() {
        candidates = new Node[]{};
    }

    public Node[] getCandidates() {
        return candidates;
    }
}
