package com.example.xmlservice.parser;

import com.example.xmlservice.dom.Node;
import com.example.xmlservice.utils.Exception.JciaNotFoundException;

public interface IParser {
    Node parse(Node rootNode) throws JciaNotFoundException;
}
