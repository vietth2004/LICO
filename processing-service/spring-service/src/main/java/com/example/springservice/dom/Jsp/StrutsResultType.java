package com.example.springservice.dom.Jsp;

import com.example.springservice.dom.Node;
import com.opensymphony.xwork2.config.entities.ResultTypeConfig;

import java.util.Map;

/**
 * Created by cuong on 4/14/2017.
 */
public class StrutsResultType extends ResultTypeConfig implements IStrutsElement {

    private ResultTypeConfig resultTypeConfig;
    private Node treeNode;

    public StrutsResultType(Node treeNode, ResultTypeConfig resultTypeConfig) {
        super("", "");
        this.treeNode = treeNode;
        this.resultTypeConfig = resultTypeConfig;
    }

    public void setDefaultResultParam(String defaultResultParam) {
        this.defaultResultParam = defaultResultParam;
    }

    @Override
    public String getDefaultResultParam() {
        return resultTypeConfig.getDefaultResultParam();
    }

    @Override
    public String getClassName() {
        return resultTypeConfig.getClassName();
    }

    @Override
    public String getName() {
        return resultTypeConfig.getName();
    }

    @Override
    public Map<String, String> getParams() {
        return resultTypeConfig.getParams();
    }

    @Override
    public boolean equals(Object o) {
        return resultTypeConfig.equals(o);
    }

    @Override
    public int hashCode() {
        return resultTypeConfig.hashCode();
    }

    public Node getTreeNode() {
        return treeNode;
    }

    public int getLineNumber() {
        return resultTypeConfig.getLocation().getLineNumber();
    }

    public int getLineColumn() {
        return resultTypeConfig.getLocation().getColumnNumber();
    }
}
