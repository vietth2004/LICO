package com.example.strutservice.base;

import com.example.strutservice.dom.Node;

/**
 * Created by locdt on 09/12/2017.
 */
public abstract class BaseAnalyzer {
    protected Validator validator;

    public abstract Node analyzeDependencies(Node rootNode);

    public Validator getValidator() {
        return validator;
    }
}
