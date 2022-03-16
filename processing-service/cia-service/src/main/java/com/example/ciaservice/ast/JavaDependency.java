package com.example.ciaservice.ast;

import java.util.List;

public enum JavaDependency {
    USE,
    MEMBER,
    INHERITANCE,
    INVOCATION,
    OVERRIDE;

    public static final List<JavaDependency> VALUE_LIST = List.of(values());

    private JavaDependency() {
    }
}
