package com.example.jspservice.dom.Jsp;

import com.example.jspservice.dom.Node;
import com.opensymphony.xwork2.config.entities.ResultConfig;
import com.opensymphony.xwork2.util.location.Location;

import java.util.Map;

/**
 * Created by cuong on 3/19/2017.
 */
public class StrutsResult extends ResultConfig implements IStrutsElement {

    private Node treeNode;
    private ResultConfig resultConfig;

    public StrutsResult(Node treeNode, ResultConfig resultConfig) {
        super("", "");
        this.treeNode = treeNode;
        this.resultConfig = resultConfig;
    }

    public Node getTreeNode() {
        return treeNode;
    }

    @Override
    public String getClassName() {
        return resultConfig.getClassName();
    }

    @Override
    public String getName() {
        return resultConfig.getName();
    }

    @Override
    public Map<String, String> getParams() {
        return resultConfig.getParams();
    }

    @Override
    public boolean equals(Object o) {
        return resultConfig.equals(o);
    }

    @Override
    public int hashCode() {
        return resultConfig.hashCode();
    }

    @Override
    public String toString() {
        return resultConfig.toString();
    }

    @Override
    public Location getLocation() {
        return resultConfig.getLocation();
    }

    @Override
    public void setLocation(Location loc) {
        resultConfig.setLocation(loc);
    }

    public int getLineNumber() {
        return resultConfig.getLocation().getLineNumber();
    }

    public int getLineColumn() {
        return resultConfig.getLocation().getColumnNumber();
    }
}
