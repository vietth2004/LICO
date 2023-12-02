package com.example.strutsservice.tiles.model;

import com.example.strutsservice.dom.Node;
import org.apache.tiles.Attribute;
import org.apache.tiles.Expression;
import org.apache.tiles.request.Request;

import java.util.Set;

/**
 * Created by cuong on 5/18/2017.
 */
public class TilesAttribute extends Attribute implements ITilesElement {

    private Node treeNode;
    private Attribute attribute;

    public TilesAttribute(Node treeNode, Attribute attribute) {
        this.treeNode = treeNode;
        this.attribute = attribute;
    }

    public TilesAttribute() {
        attribute = new Attribute();
    }

    public static TilesAttribute createTemplateAttribute(String template) {
        TilesAttribute attribute = new TilesAttribute();
        attribute.setValue(template);
        attribute.setRenderer("template");
        return attribute;
    }

    public static TilesAttribute createTemplateAttribute(String template, String templateExpression, String templateType, String role) {
        TilesAttribute templateAttribute = createTemplateAttribute(template);
        templateAttribute.setRole(role);
        if (templateType != null) {
            templateAttribute.setRenderer(templateType);
        }

        templateAttribute.setExpressionObject(Expression.createExpressionFromDescribedExpression(templateExpression));
        return templateAttribute;
    }

    public static TilesAttribute createTemplateAttributeWithExpression(String templateExpression) {
        TilesAttribute attribute = new TilesAttribute();
        attribute.setExpressionObject(new Expression(templateExpression));
        attribute.setRenderer("template");
        return attribute;
    }

    public void setTreeNode(Node treeNode) {
        this.treeNode = treeNode;
    }

    @Override
    public Node getTreeNode() {
        return treeNode;
    }

    @Override
    public String getRole() {
        return attribute.getRole();
    }

    @Override
    public Set<String> getRoles() {
        return attribute.getRoles();
    }

    @Override
    public void setRole(String role) {
        attribute.setRole(role);
    }

    @Override
    public void setRoles(Set<String> roles) {
        attribute.setRoles(roles);
    }

    @Override
    public Object getValue() {
        return attribute.getValue();
    }

    @Override
    public void setValue(Object value) {
        attribute.setValue(value);
    }

    @Override
    public Expression getExpressionObject() {
        return attribute.getExpressionObject();
    }

    @Override
    public void setExpressionObject(Expression expressionObject) {
        attribute.setExpressionObject(expressionObject);
    }

    @Override
    public String toString() {
        return attribute.toString();
    }

    @Override
    public String getRenderer() {
        return attribute.getRenderer();
    }

    @Override
    public void setRenderer(String rendererName) {
        attribute.setRenderer(rendererName);
    }

    @Override
    public void inherit(Attribute attribute) {
        attribute.inherit(attribute);
    }

    @Override
    public boolean equals(Object obj) {
        return attribute.equals(obj);
    }

    @Override
    public boolean isPermitted(Request request) {
        return attribute.isPermitted(request);
    }

    @Override
    public int hashCode() {
        return attribute.hashCode();
    }
}
