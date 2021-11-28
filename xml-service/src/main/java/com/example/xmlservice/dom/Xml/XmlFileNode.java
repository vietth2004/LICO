package com.example.xmlservice.dom.Xml;

import com.example.xmlservice.utils.Exception.JciaIgnore;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.xmlservice.dom.FileNode;
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