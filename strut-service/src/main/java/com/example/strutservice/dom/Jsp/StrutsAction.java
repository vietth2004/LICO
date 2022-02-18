package com.example.strutservice.dom.Jsp;

import com.example.strutservice.dom.Node;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.config.entities.ExceptionMappingConfig;
import com.opensymphony.xwork2.config.entities.InterceptorMapping;
import com.opensymphony.xwork2.config.entities.ResultConfig;
import com.opensymphony.xwork2.util.location.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StrutsAction extends ActionConfig implements IStrutsElement, IStrutsElementLevel1 {

    private ActionConfig actionConfig;
    private List<StrutsResult> strutsResults;
    private Node treeNode;

    private StrutsPackage strutsPackage;

    public StrutsAction(Node treeNode, ActionConfig actionConfig) {
        super("", "", "");
        this.treeNode = treeNode;
        this.actionConfig = actionConfig;
        strutsResults = new ArrayList<>();
    }

    public StrutsPackage getStrutsPackage() {
        return strutsPackage;
    }

    public void setStrutsPackage(StrutsPackage strutsPackage) {
        this.strutsPackage = strutsPackage;
    }

    public Node getTreeNode() {
        return treeNode;
    }

    public List<StrutsResult> getStrutsResults() {
        return strutsResults;
    }

    public void addStrutsResult(StrutsResult strutsResult) {
        this.strutsResults.add(strutsResult);
    }

    @Override
    public String getName() {
        return actionConfig.getName();
    }

    @Override
    public String getClassName() {
        return actionConfig.getClassName();
    }

    @Override
    public List<ExceptionMappingConfig> getExceptionMappings() {
        return actionConfig.getExceptionMappings();
    }

    @Override
    public List<InterceptorMapping> getInterceptors() {
        return actionConfig.getInterceptors();
    }

    @Override
    public Set<String> getAllowedMethods() {
        return actionConfig.getAllowedMethods();
    }

    @Override
    public String getMethodName() {
        if (actionConfig.getMethodName() == null || actionConfig.getMethodName().isEmpty())
            return "execute";
        else
            return actionConfig.getMethodName();
    }

    @Override
    public String getPackageName() {
        return actionConfig.getPackageName();
    }

    @Override
    public Map<String, String> getParams() {
        return actionConfig.getParams();
    }

    @Override
    public Map<String, ResultConfig> getResults() {
        return actionConfig.getResults();
    }

    @Override
    public boolean isAllowedMethod(String method) {
        return actionConfig.isAllowedMethod(method);
    }

    @Override
    public boolean isStrictMethodInvocation() {
        return actionConfig.isStrictMethodInvocation();
    }

    @Override
    public boolean equals(Object o) {
        return actionConfig.equals(o);
    }

    @Override
    public int hashCode() {
        return actionConfig.hashCode();
    }

    @Override
    public String toString() {
        return actionConfig.toString();
    }

    @Override
    public Location getLocation() {
        return actionConfig.getLocation();
    }

    @Override
    public void setLocation(Location loc) {
        actionConfig.setLocation(loc);
    }

    public int getLineNumber() {
        return actionConfig.getLocation().getLineNumber();
    }

    public int getLineColumn() {
        return actionConfig.getLocation().getColumnNumber();
    }
}
