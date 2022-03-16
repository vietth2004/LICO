package com.example.jsfservice.parser;

import com.example.jsfservice.dom.Node;
import com.example.jsfservice.utils.Exception.JciaNotFoundException;

public interface IParser {
    Node parse(Node rootNode) throws JciaNotFoundException;
}
