package com.example.strutservice.dom.Jsp;

import com.example.strutservice.dom.Node;
import com.opensymphony.xwork2.config.entities.InterceptorMapping;
import com.opensymphony.xwork2.config.entities.InterceptorStackConfig;

import java.util.Collection;

public class StrutsInterceptorStack extends InterceptorStackConfig implements IStrutsElement, IStrutsElementLevel1 {

    private InterceptorStackConfig interceptorStackConfig;
    private StrutsPackage strutsPackage;
    private Node treeNode;

    public StrutsInterceptorStack(Node treeNode, InterceptorStackConfig interceptorStackConfig) {
        this.interceptorStackConfig = interceptorStackConfig;
        this.treeNode = treeNode;
    }

    public Node getTreeNode() {
        return treeNode;
    }

    public void setTreeNode(Node treeNode) {
        this.treeNode = treeNode;
    }

    public StrutsPackage getStrutsPackage() {
        return strutsPackage;
    }

    public void setStrutsPackage(StrutsPackage strutsPackage) {
        this.strutsPackage = strutsPackage;
    }

    @Override
    public Collection<InterceptorMapping> getInterceptors() {
        return interceptorStackConfig.getInterceptors();
    }

    @Override
    public String getName() {
        return interceptorStackConfig.getName();
    }
}
