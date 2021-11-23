package com.example.xmlservice.Dom.Xml;

import com.example.xmlservice.Utils.Exception.JciaIgnore;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.xmlservice.Dom.FileNode;
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