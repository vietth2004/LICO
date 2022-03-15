package com.example.jspservice.utils.Helper;

import com.example.jspservice.condition.ICondition;
import com.example.jspservice.condition.XmlTagNodeLocationCondition;
import com.example.jspservice.dom.Node;
import com.example.jspservice.dom.Xml.XmlFileNode;
import com.example.jspservice.search.NodeSearch;
import com.example.jspservice.tiles.parser.JciaLocator;
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
