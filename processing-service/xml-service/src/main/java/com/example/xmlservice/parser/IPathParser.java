package com.example.xmlservice.parser;

import com.example.xmlservice.dom.Node;
import com.example.xmlservice.utils.Exception.JciaNotFoundException;

public interface IPathParser {
    Node parse(String path) throws JciaNotFoundException;
}
