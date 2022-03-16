package com.example.strutsservice.dom.Xml;

import com.example.strutsservice.dom.FileNode;
import com.example.strutsservice.utils.Exception.JciaIgnore;
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