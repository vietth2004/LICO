package com.example.springservice.dom.Jsp;

import com.example.springservice.dom.Node;
import com.opensymphony.xwork2.config.entities.InterceptorConfig;

import java.util.Map;

public class StrutsInterceptor extends InterceptorConfig implements IStrutsElement {

    private InterceptorConfig interceptorConfig;
    private Node treeNode;

    public StrutsInterceptor(Node treeNode, InterceptorConfig interceptorConfig) {
        super("", "");
        this.treeNode = treeNode;
        this.interceptorConfig = interceptorConfig;
    }

    public Node getTreeNode() {
        return treeNode;
    }

    @Override
    public String getClassName() {
        return interceptorConfig.getClassName();
    }

    @Override
    public String getName() {
        return interceptorConfig.getName();
    }

    @Override
    public Map<String, String> getParams() {
        return interceptorConfig.getParams();
    }

    @Override
    public boolean equals(Object o) {
        return interceptorConfig.equals(o);
    }

    @Override
    public int hashCode() {
        return interceptorConfig.hashCode();
    }

    @Override
    public String toString() {
        return interceptorConfig.toString();
    }

    public int getLineNumber() {
        return interceptorConfig.getLocation().getLineNumber();
    }

    public int getLineColumn() {
        return interceptorConfig.getLocation().getColumnNumber();
    }
}
