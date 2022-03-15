package com.example.jspservice.tiles.model;

import com.example.jspservice.dom.Node;
import org.apache.tiles.Attribute;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.BasicAttributeContext;
import org.apache.tiles.Definition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by cuong on 5/18/2017.
 */
public class TilesDefinition extends Definition implements ITilesElement {

    private Node treeNode;
    private Definition definition;
    private List<TilesAttribute> tilesAttributes;

    public TilesDefinition(Node treeNode, Definition definition) {
        this.treeNode = treeNode;
        this.definition = definition;
        this.tilesAttributes = new ArrayList<>();
    }

    public TilesDefinition() {
        definition = new Definition();
        this.tilesAttributes = new ArrayList<>();
    }

    public void addTilesAttribute(TilesAttribute tilesAttribute) {
        this.tilesAttributes.add(tilesAttribute);
    }

    public void setTreeNode(Node treeNode) {
        this.treeNode = treeNode;
    }

    @Override
    public Node getTreeNode() {
        return treeNode;
    }

    public List<TilesAttribute> getTilesAttributes() {
        return tilesAttributes;
    }

    @Override
    public Attribute getTemplateAttribute() {
        return definition.getTemplateAttribute();
    }

    @Override
    public void setTemplateAttribute(Attribute templateAttribute) {
        definition.setTemplateAttribute(templateAttribute);
    }

    @Override
    public String getPreparer() {
        return definition.getPreparer();
    }

    @Override
    public void setPreparer(String url) {
        definition.setPreparer(url);
    }

    @Override
    public void inheritCascadedAttributes(AttributeContext context) {
        definition.inheritCascadedAttributes(context);
    }

    @Override
    public void inherit(AttributeContext parent) {
        definition.inherit(parent);
    }

    @Override
    public void inherit(BasicAttributeContext parent) {
        definition.inherit(parent);
    }

    @Override
    public void addAll(Map<String, Attribute> newAttributes) {
        definition.addAll(newAttributes);
    }

    @Override
    public void addMissing(Map<String, Attribute> defaultAttributes) {
        definition.addMissing(defaultAttributes);
    }

    @Override
    public Attribute getAttribute(String name) {
        return definition.getAttribute(name);
    }

    @Override
    public Attribute getLocalAttribute(String name) {
        return definition.getLocalAttribute(name);
    }

    @Override
    public Attribute getCascadedAttribute(String name) {
        return definition.getCascadedAttribute(name);
    }

    @Override
    public Set<String> getLocalAttributeNames() {
        return definition.getLocalAttributeNames();
    }

    @Override
    public Set<String> getCascadedAttributeNames() {
        return definition.getCascadedAttributeNames();
    }

    @Override
    public void putAttribute(String name, Attribute value) {
        definition.putAttribute(name, value);
    }

    @Override
    public void putAttribute(String name, Attribute value, boolean cascade) {
        definition.putAttribute(name, value, cascade);
    }

    @Override
    public void clear() {
        definition.clear();
    }

    @Override
    public String getName() {
        return definition.getName();
    }

    @Override
    public void setName(String aName) {
        definition.setName(aName);
    }

    @Override
    public void setExtends(String name) {
        definition.setExtends(name);
    }

    @Override
    public String getExtends() {
        return definition.getExtends();
    }

    @Override
    public boolean equals(Object obj) {
        return definition.equals(obj);
    }

    @Override
    public int hashCode() {
        return definition.hashCode();
    }

    @Override
    public boolean isExtending() {
        return definition.isExtending();
    }

    @Override
    public String toString() {
        return definition.toString();
    }
}
