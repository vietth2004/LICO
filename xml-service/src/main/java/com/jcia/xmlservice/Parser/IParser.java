package com.jcia.xmlservice.Parser;

import com.jcia.xmlservice.Dom.Node;
import com.jcia.xmlservice.Utils.Exception.JciaNotFoundException;

public interface IParser {
    Node parse(Node rootNode) throws JciaNotFoundException;
}
