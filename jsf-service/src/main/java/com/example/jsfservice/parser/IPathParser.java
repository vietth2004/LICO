package com.example.jsfservice.parser;

import com.example.jsfservice.dom.Node;
import com.example.jsfservice.utils.Exception.JciaNotFoundException;

public interface IPathParser {
    Node parse(String path) throws JciaNotFoundException;
}
