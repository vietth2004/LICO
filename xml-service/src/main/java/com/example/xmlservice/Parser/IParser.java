package com.example.xmlservice.Parser;

import com.example.xmlservice.Dom.Node;
import com.example.xmlservice.Utils.Exception.JciaNotFoundException;

public interface IParser {
    Node parse(Node rootNode) throws JciaNotFoundException;
}
