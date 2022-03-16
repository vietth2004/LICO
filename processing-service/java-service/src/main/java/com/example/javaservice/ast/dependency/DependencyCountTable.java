package com.example.javaservice.ast.dependency;


import java.io.Serializable;

import mrmathami.cia.java.tree.dependency.JavaDependency;

public class DependencyCountTable implements Serializable {
    private Integer USE = 0;
    private Integer MEMBER = 0;
    private Integer INHERITANCE = 0;
    private Integer INVOCATION = 0;
    private Integer OVERRIDE = 0;


    public DependencyCountTable() {
    }

    public DependencyCountTable(mrmathami.cia.java.jdt.project.tree.dependency.DependencyCountTable dependencyCountTable) {
        this.USE = dependencyCountTable.getCount(JavaDependency.USE);
        this.MEMBER = dependencyCountTable.getCount(JavaDependency.MEMBER);
        this.INHERITANCE = dependencyCountTable.getCount(JavaDependency.INHERITANCE);
        this.INVOCATION = dependencyCountTable.getCount(JavaDependency.INVOCATION);
        this.OVERRIDE = dependencyCountTable.getCount(JavaDependency.OVERRIDE);
    }

    public Integer getUSE() {
        return USE;
    }

    public void setUSE(Integer USE) {
        this.USE = USE;
    }

    public Integer getMEMBER() {
        return MEMBER;
    }

    public void setMEMBER(Integer MEMBER) {
        this.MEMBER = MEMBER;
    }

    public Integer getINHERITANCE() {
        return INHERITANCE;
    }

    public void setINHERITANCE(Integer INHERITANCE) {
        this.INHERITANCE = INHERITANCE;
    }

    public Integer getINVOCATION() {
        return INVOCATION;
    }

    public void setINVOCATION(Integer INVOCATION) {
        this.INVOCATION = INVOCATION;
    }

    public Integer getOVERRIDE() {
        return OVERRIDE;
    }

    public void setOVERRIDE(Integer OVERRIDE) {
        this.OVERRIDE = OVERRIDE;
    }
}
