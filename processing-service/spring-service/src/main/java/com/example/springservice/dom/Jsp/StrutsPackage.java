package com.example.springservice.dom.Jsp;

import com.example.springservice.dom.Node;
import com.opensymphony.xwork2.util.location.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by dinht_000 on 3/22/2017.
 */
public class StrutsPackage extends PackageConfig implements IStrutsElement {

    public static final String DEFAULT_NAMESPACE = "";

    private PackageConfig packageConfig;
    private List<StrutsAction> strutsActions;
    private List<StrutsResultType> strutsResultTypes;
    private List<StrutsResult> strutsGlobalResults;
    private List<StrutsInterceptor> strutsInterceptors;
    private List<StrutsInterceptorStack> strutsInterceptorStacks;
    private Node treeNode;

    public StrutsPackage(Node treeNode, PackageConfig packageConfig) {
        super("");
        this.treeNode = treeNode;
        this.packageConfig = packageConfig;
        strutsActions = new ArrayList<>();
        strutsResultTypes = new ArrayList<>();
        strutsGlobalResults = new ArrayList<>();
        strutsInterceptors = new ArrayList<>();
        strutsInterceptorStacks = new ArrayList<>();
    }

    public void addStrutsInterceptorStack(StrutsInterceptorStack strutsInterceptorStack) {
        this.strutsInterceptorStacks.add(strutsInterceptorStack);
    }

    public void addStrutsInterceptor(StrutsInterceptor strutsInterceptor) {
        this.strutsInterceptors.add(strutsInterceptor);
    }

    public List<StrutsInterceptor> getStrutsInterceptors() {
        return strutsInterceptors;
    }

    public List<StrutsResult> getStrutsGlobalResults() {
        return strutsGlobalResults;
    }

    public void addStrutsGlobalResult(StrutsResult strutsGlobalResult) {
        this.strutsGlobalResults.add(strutsGlobalResult);
    }

    public List<StrutsResultType> getStrutsResultTypes() {
        return strutsResultTypes;
    }

    public void addStrutsResultTypes(StrutsResultType strutsResultType) {
        this.strutsResultTypes.add(strutsResultType);
    }

    public Node getTreeNode() {
        return treeNode;
    }

    public List<StrutsAction> getStrutsActions() {
        return strutsActions;
    }

    public void addStrutsAction(StrutsAction strutsAction) {
        this.strutsActions.add(strutsAction);
    }

    @Override
    public boolean isAbstract() {
        return packageConfig.isAbstract();
    }

    @Override
    public Map<String, ActionConfig> getActionConfigs() {
        return packageConfig.getActionConfigs();
    }

    @Override
    public Map<String, ActionConfig> getAllActionConfigs() {
        return packageConfig.getAllActionConfigs();
    }

    @Override
    public Map<String, ResultConfig> getAllGlobalResults() {
        return packageConfig.getAllGlobalResults();
    }

    @Override
    public Map<String, Object> getAllInterceptorConfigs() {
        return packageConfig.getAllInterceptorConfigs();
    }

    @Override
    public Map<String, ResultTypeConfig> getAllResultTypeConfigs() {
        return packageConfig.getAllResultTypeConfigs();
    }

    @Override
    public List<ExceptionMappingConfig> getAllExceptionMappingConfigs() {
        return packageConfig.getAllExceptionMappingConfigs();
    }

    @Override
    public String getDefaultInterceptorRef() {
        return packageConfig.getDefaultInterceptorRef();
    }

    @Override
    public String getDefaultActionRef() {
        return packageConfig.getDefaultActionRef();
    }

    @Override
    public String getDefaultClassRef() {
        return packageConfig.getDefaultClassRef();
    }

    @Override
    public String getDefaultResultType() {
        return packageConfig.getDefaultResultType();
    }

    @Override
    public String getFullDefaultInterceptorRef() {
        return packageConfig.getFullDefaultInterceptorRef();
    }

    @Override
    public String getFullDefaultActionRef() {
        return packageConfig.getFullDefaultActionRef();
    }

    @Override
    public String getFullDefaultResultType() {
        return packageConfig.getFullDefaultResultType();
    }

    @Override
    public Map<String, ResultConfig> getGlobalResultConfigs() {
        return packageConfig.getGlobalResultConfigs();
    }

    @Override
    public Map<String, Object> getInterceptorConfigs() {
        return packageConfig.getInterceptorConfigs();
    }

    @Override
    public String getName() {
        return packageConfig.getName();
    }

    @Override
    public String getNamespace() {
        return packageConfig.getNamespace();
    }

    @Override
    public List<PackageConfig> getParents() {
        return packageConfig.getParents();
    }

    @Override
    public Map<String, ResultTypeConfig> getResultTypeConfigs() {
        return packageConfig.getResultTypeConfigs();
    }

    @Override
    public boolean isNeedsRefresh() {
        return packageConfig.isNeedsRefresh();
    }

    @Override
    public List<ExceptionMappingConfig> getGlobalExceptionMappingConfigs() {
        return packageConfig.getGlobalExceptionMappingConfigs();
    }

    @Override
    public Set<String> getGlobalAllowedMethods() {
        return packageConfig.getGlobalAllowedMethods();
    }

    @Override
    public boolean isStrictMethodInvocation() {
        return packageConfig.isStrictMethodInvocation();
    }

    @Override
    public boolean equals(Object o) {
        return packageConfig.equals(o);
    }

    @Override
    public int hashCode() {
        return packageConfig.hashCode();
    }

    @Override
    public String toString() {
        return packageConfig.toString();
    }

    @Override
    public int compareTo(Object o) {
        return packageConfig.compareTo(o);
    }

    @Override
    public Object getInterceptorConfig(String name) {
        return packageConfig.getInterceptorConfig(name);
    }

    @Override
    public Location getLocation() {
        return packageConfig.getLocation();
    }

    @Override
    public void setLocation(Location loc) {
        packageConfig.setLocation(loc);
    }

    public int getLineNumber() {
        return packageConfig.getLocation().getLineNumber();
    }

    public int getLineColumn() {
        return packageConfig.getLocation().getColumnNumber();
    }
}
