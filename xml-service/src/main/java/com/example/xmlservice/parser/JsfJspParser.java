package com.example.xmlservice.parser;

import com.example.xmlservice.dom.Jsp.JspFileNode;
import com.example.xmlservice.dom.Jsp.JspTagNode;
import com.example.xmlservice.dom.Node;
import com.example.xmlservice.utils.Exception.JciaNotFoundException;
import com.example.xmlservice.utils.SupportParserJsp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;

public class JsfJspParser implements IParser {

    private static final Logger logger = LogManager.getLogger(JsfJspParser.class);

    private JspFileNode jspFileNode;
    private SupportParserJsp supportParserJsp;

    @Override
    public Node parse(Node node) throws JciaNotFoundException {
        if (node == null) return node;
        this.jspFileNode = (JspFileNode) node;

        String contentFile = supportParserJsp.getContentFileWithoutComment(node);
        Document document = Jsoup.parse(contentFile, "utf-8");
        jspFileNode.setDocument(document);
        jspFileNode = (JspFileNode) generateTreeNodeJsp(jspFileNode);

        return jspFileNode;
    }

    /**
     * @param rootNode
     * @return
     * @function: create Tree node from rootnode input
     */
    private Node generateTreeNodeJsp(Node rootNode) {
        List<Element> elements;
        if (rootNode instanceof JspFileNode) {
            elements = ((JspFileNode) rootNode).getDocument().children();
        } else {
            elements = ((JspTagNode) rootNode).getDomaNode().children();
        }
        if (elements.size() == 0) {
            return rootNode;
        }
        /**
         * recure here to check all of element
         */
        for (Element element : elements) {
            Node child = createJspTagNodeByElement(element, rootNode.getAbsolutePath());
            rootNode.addChild(child);
            child.setParent(rootNode);
            generateTreeNodeJsp(child);
        }

        return rootNode;
    }

    //TODO: continue work here
    /**
     * @param element
     * @param parentPath
     * @return JspTagNode
     * @function: create JspTagNode from JSOUP Element and parent path
     */
    private Node createJspTagNodeByElement(Element element, String parentPath){
        return null;
    }

}
