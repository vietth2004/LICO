package com.example.jspservice.dom.Xml;

import com.example.jspservice.dom.FileNode;
import com.example.jspservice.utils.Exception.JciaIgnore;
import com.fasterxml.jackson.annotation.JsonIgnore;
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