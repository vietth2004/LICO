package com.example.xmlservice.Parser;

import com.example.xmlservice.Dom.Node;
import com.example.xmlservice.Utils.Exception.JciaNotFoundException;

public interface IPathParser {
    Node parse(String path) throws JciaNotFoundException;
}
