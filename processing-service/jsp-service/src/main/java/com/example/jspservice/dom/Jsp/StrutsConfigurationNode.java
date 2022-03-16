package com.example.jspservice.dom.Jsp;

import com.example.jspservice.dom.Xml.XmlFileNode;
import com.example.jspservice.dom.Xml.XmlFileNodeDecorator;
import com.example.jspservice.utils.Exception.JciaIgnore;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class StrutsConfigurationNode extends XmlFileNodeDecorator {

    @JsonIgnore
    @JciaIgnore
    protected List<StrutsPackage> strutsPackages;

    @JsonIgnore @JciaIgnore
    protected List<StrutsConfigurationNode> includedStrutsConfigurationNodes;

    public StrutsConfigurationNode() {
    }

    public StrutsConfigurationNode(XmlFileNode xmlFileNode) {
        super(xmlFileNode);
        strutsPackages = new ArrayList<>();
        includedStrutsConfigurationNodes = new ArrayList<>();
    }

    public List<StrutsPackage> getStrutsPackages() {
        return strutsPackages;
    }

    public void addAllStrutsPackage(List<StrutsPackage> strutsPackages) {
        this.strutsPackages.addAll(strutsPackages);
    }

    public List<StrutsConfigurationNode> getIncludedStrutsConfigurationNodes() {
        return includedStrutsConfigurationNodes;
    }

    public void addIncludedStrutsConfigurationNode(StrutsConfigurationNode strutsConfigurationNode) {
        this.includedStrutsConfigurationNodes.add(strutsConfigurationNode);
    }

    public void setStrutsPackages(List<StrutsPackage> list){
        this.strutsPackages = list;
    }

    public void setIncludedStrutsConfigurationNodes(List<StrutsConfigurationNode> list){
        this.includedStrutsConfigurationNodes = list;
    }
}
