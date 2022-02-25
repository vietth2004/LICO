package com.example.gitservice.constant;

import mrmathami.cia.java.tree.dependency.JavaDependency;
import mrmathami.cia.java.tree.dependency.JavaDependencyWeightTable;

import java.util.Map;

public class DependencyCountTableConstant {

    public static final JavaDependencyWeightTable DEPENDENCY_WEIGHT_TABLE = JavaDependencyWeightTable.of(Map.of(
            JavaDependency.USE, 1.0,
            JavaDependency.MEMBER, 1.0,
            JavaDependency.INHERITANCE, 4.0,
            JavaDependency.INVOCATION, 4.0,
            JavaDependency.OVERRIDE, 1.0
    ));
    public static final JavaDependencyWeightTable DEPENDENCY_IMPACT_TABLE = JavaDependencyWeightTable.of(Map.of(
            JavaDependency.USE, 0.4,
            JavaDependency.MEMBER, 0.2,
            JavaDependency.INHERITANCE, 0.3,
            JavaDependency.INVOCATION, 0.3,
            JavaDependency.OVERRIDE, 0.3
    ));

}
