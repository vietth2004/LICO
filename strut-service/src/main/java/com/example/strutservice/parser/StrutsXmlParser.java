package com.example.strutservice.parser;


import com.example.strutservice.condition.XmlFileNodeCondition;
import com.example.strutservice.constant.StrutsXmlTag;
import com.example.strutservice.dom.Jsp.StrutsConfigurationNode;
import com.example.strutservice.dom.Jsp.StrutsIncludeEntry;
import com.example.strutservice.dom.Node;
import com.example.strutservice.dom.Xml.XmlFileNode;
import com.example.strutservice.dom.Xml.XmlTagNode;
import com.example.strutservice.search.NodeSearch;
import com.example.strutservice.utils.Exception.JciaNotFoundException;
import com.example.strutservice.utils.Helper.NodeHelper;
import com.example.strutservice.utils.Helper.StrutsHelper;
import com.example.strutservice.utils.Log.ClientLevel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuong on 3/19/2017.
 */
public class StrutsXmlParser extends XmlFileParser {

    private static final Logger logger = LogManager.getLogger(StrutsXmlParser.class);
    private StrutsConfigurationNode rootNode;

    public StrutsXmlParser() {
    }

    @Override
    public Node parse(String xmlPath) throws JciaNotFoundException {
        XmlFileNode xmlFileNode = new XmlFileNode();
        xmlFileNode.setAbsolutePath(xmlPath);
        xmlFileNode = (XmlFileNode) super.parse(xmlFileNode);
        return parse(xmlFileNode);
    }

    @Override
    public Node parse(Node xmlFileNode) {
        rootNode = parsingStrutsXmlNode(xmlFileNode);
        // replace xmlFileNode by rootNode
        NodeHelper.replaceNode(xmlFileNode, rootNode);
        return rootNode;
    }

    private StrutsConfigurationNode parsingStrutsXmlNode(Node xmlFileNode) {
        rootNode = new StrutsConfigurationNode((XmlFileNode) xmlFileNode);
        collectingStrutsElement();
        return rootNode;
    }

    private void collectingStrutsElement() {
        for (Node child : rootNode.getChildren()) {
            if (child instanceof XmlTagNode) {
                XmlTagNode childXmlNode = (XmlTagNode) child;
                if (isNodeUsingTag(childXmlNode, StrutsXmlTag.STRUTS_TAG)) {
                    List<StrutsIncludeEntry> includeEntries = collectingStrutsEntryNode(childXmlNode);
                    for (StrutsIncludeEntry entry : includeEntries) {
                        rootNode.addIncludedStrutsConfigurationNode(entry.getInnerEntryNode());
                    }
                }
            }
        }
    }

    private List<StrutsIncludeEntry> collectingStrutsEntryNode(XmlTagNode node) {
        List<StrutsIncludeEntry> entries = collectingIncludeElement(node);

        for (StrutsIncludeEntry entry : entries) {
            StrutsConfigurationNode entryNode = parsingIncludeEntryElement(rootNode.getParent(), entry);
            entry.setInnerEntryNode(entryNode);
        }
        return entries;
    }

    private List<StrutsIncludeEntry> collectingIncludeElement(XmlTagNode node) {
        List<StrutsIncludeEntry> result = new ArrayList<>();
        for (Node child : node.getChildren()) {
            if (child instanceof XmlTagNode) {
                XmlTagNode childXmlNode = (XmlTagNode) child;
                if (isNodeUsingTag(childXmlNode, StrutsXmlTag.INCLUDE_TAG)) {
                    result.add(StrutsHelper.generateStrutsIncludeElement(childXmlNode));
                }
            }
        }
        return result;
    }

    /**
     * Find secondary struts configuration file which declared in <include> tag in struts.xml
     * Recursion to parse included node!
     * @param root
     * @param entry
     * @return null if secondary configuration file not found, a StrutsConfigurationNode instance if success
     */
    private StrutsConfigurationNode parsingIncludeEntryElement(Node root, StrutsIncludeEntry entry) {
        String relativePath = entry.getFileName();

        XmlFileNode childXml = (XmlFileNode) NodeSearch.searchOneNode(root, new XmlFileNodeCondition(), relativePath);
        if (childXml == null) {
            logger.error(String.format("Could not load struts configuration file [%s]", relativePath));
            //log for client
            logger.log(ClientLevel.C_ERROR, String.format("Could not load struts configuration file [%s]", relativePath));
            return null;
        }

        // Recursion here!
        StrutsXmlParser childParser = new StrutsXmlParser();
        return (StrutsConfigurationNode) childParser.parse(childXml);
    }

    private boolean isNodeUsingTag(XmlTagNode node, String tag) {
        return node.getTagName().equals(tag);
    }
}
