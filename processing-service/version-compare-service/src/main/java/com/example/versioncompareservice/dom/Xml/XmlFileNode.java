package com.example.versioncompareservice.dom.Xml;

import com.example.versioncompareservice.dom.FileNode;
import com.example.versioncompareservice.utils.Exception.JciaIgnore;
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