package com.example.jspservice.dom.Jsp;

import com.example.jspservice.dom.Node;

import com.example.jspservice.dom.Element;
import com.example.jspservice.dom.Xml.XmlFileNode;
import com.example.jspservice.dom.Xml.XmlTagNode;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by cuong on 3/19/2017.
 */
public class StrutsElement extends Element implements IStrutsElement {

    protected String name;
    protected XmlTagNode tagNode;
    protected XmlFileNode rootFileNode;

    @JsonIgnore
    protected StrutsElement parent;

    public StrutsElement() {

    }

    public StrutsElement(XmlTagNode tagNode) {this.tagNode = tagNode;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public XmlTagNode getTagNode() {
        return tagNode;
    }

    public void setTagNode(XmlTagNode tagNode) {
        this.tagNode = tagNode;
    }

    public StrutsElement getParent() {
        return parent;
    }

    public void setParent(StrutsElement parent) {
        this.parent = parent;
    }

    public XmlFileNode getRootFileNode() {
        return rootFileNode;
    }

    public void setRootFileNode(XmlFileNode rootFileNode) {
        this.rootFileNode = rootFileNode;
    }

    @Override
    public Node getTreeNode() {
        return null;
    }
}