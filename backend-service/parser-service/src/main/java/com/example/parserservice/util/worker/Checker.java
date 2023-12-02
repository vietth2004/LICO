package com.example.parserservice.util.worker;

import com.example.parserservice.ast.dependency.Dependency;

public class Checker {

    public static Boolean isDependency(Dependency base, Dependency dependency) {
        if (base.getCalleeNode().equals(dependency.getCalleeNode())
                && base.getCallerNode().equals(dependency.getCallerNode())) {
            return true;
        }
        return false;
    }

}
