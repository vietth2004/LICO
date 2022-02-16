package com.example.strutservice.dom.Jsp;

import com.example.strutservice.dom.Xml.XmlTagNode;

/**
 * Created by dinht_000 on 3/23/2017.
 */
public class StrutsIncludeEntry extends StrutsElement {
    protected String fileName;
    protected StrutsConfigurationNode innerEntryNode;

    public StrutsIncludeEntry() {

    }

    public StrutsIncludeEntry(XmlTagNode tagNode) {
        super(tagNode);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public StrutsConfigurationNode getInnerEntryNode() {
        return innerEntryNode;
    }

    public void setInnerEntryNode(StrutsConfigurationNode innerEntryNode) {
        this.innerEntryNode = innerEntryNode;
    }

    @Override
    public String toString() {
        return "StrutsIncludeEntry{" +
                "fileName='" + fileName + '\'' +
                ", innerEntryNode=" + innerEntryNode +
                '}';
    }
}
