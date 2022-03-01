package com.example.strutservice.parser;

import com.example.strutservice.dom.Node;
import com.example.strutservice.utils.Exception.JciaNotFoundException;

public interface IParser {
    Node parse(Node rootNode) throws JciaNotFoundException;
}
