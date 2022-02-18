package com.example.strutservice.base;

import com.example.strutservice.dom.Node;

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
