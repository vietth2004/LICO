package com.jcia.xmlservice.Dom.Xml;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jcia.xmlservice.Dom.FileNode;
import com.jcia.xmlservice.Utils.Exception.JciaIgnore;
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