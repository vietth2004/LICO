package com.example.strutsservice.base;

import com.example.strutsservice.dom.Node;

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
