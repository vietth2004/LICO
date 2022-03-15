package com.example.xmlservice.base;

import com.example.xmlservice.dom.Node;

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
