package com.jcia.xml.dom.Xml;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jcia.xml.dom.FileNode;
import com.jcia.xml.utils.Exception.JciaIgnore;
import org.w3c.dom.Document;

public class XmlFileNode extends FileNode {

    @JsonIgnore
    @JciaIgnore
    protected Document document;

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}