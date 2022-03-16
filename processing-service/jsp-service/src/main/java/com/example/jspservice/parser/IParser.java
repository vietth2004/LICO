package com.example.jspservice.parser;

import com.example.jspservice.dom.Node;
import com.example.jspservice.utils.Exception.JciaNotFoundException;

public interface IParser {
    Node parse(Node rootNode) throws JciaNotFoundException;
}
