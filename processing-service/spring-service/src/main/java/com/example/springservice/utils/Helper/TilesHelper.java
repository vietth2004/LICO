package com.example.springservice.utils.Helper;

import com.example.springservice.condition.ICondition;
import com.example.springservice.condition.XmlTagNodeLocationCondition;
import com.example.springservice.dom.Node;
import com.example.springservice.dom.Xml.XmlFileNode;
import com.example.springservice.search.NodeSearch;
import com.example.springservice.tiles.parser.JciaLocator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by jcia on 20/05/2017.
 */
public class TilesHelper {

    private static final Logger logger = LogManager.getLogger(TilesHelper.class);

    public static Node getTagNodeByLocation(XmlFileNode tilesXmlNode, JciaLocator locator) {
        if (tilesXmlNode == null || locator == null) return null;
        int lineNumber = locator.getLineNumber();
        int columnNumber = locator.getColumnNumber();
        ICondition xmlTagNodeCondition = new XmlTagNodeLocationCondition(lineNumber, columnNumber);
        return NodeSearch.searchOneNode(tilesXmlNode, xmlTagNodeCondition);
    }
}
