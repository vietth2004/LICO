package com.example.jspservice.parser;

import com.example.jspservice.dom.Jsp.*;
import com.example.jspservice.dom.Node;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by vy on 24-Mar-17.
 */
public class StrutsJspParser implements IParser {
    private static final Logger logger = LogManager.getLogger(StrutsJspParser.class);

    private JspFileNode jspFileNode;
    private SupportParserJsp supportParserJsp;

    public StrutsJspParser() {
        this.supportParserJsp = new SupportParserJsp();
    }

    @Override
    public Node parse(Node node) {
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

    /**
     * @param element
     * @param parentPath
     * @return JspTagNode
     * @function: create JspTagNode from JSOUP Element and parent path
     */
    private Node createJspTagNodeByElement(Element element, String parentPath) {
        JspTagNode result = new JspTagNode();
        result.setAbsolutePath(parentPath + File.separator + element.tagName());
        result.setTagName(element.tagName());
        result.setDomaNode(element);
        result.setOwnerText(element.ownText());
        result.setName(element.tagName());

        ActionJsp actionJsp = null;


        // if current tag is tag contain action : create an ActionJsp
        if (element.tagName().equals("s:form") || element.tagName().equals("s:url")
                || element.tagName().equals("s:action")) {
            actionJsp = new ActionJsp();
        }

        Map<String, String> attributes = new HashMap<>();
        Iterator iter = element.attributes().iterator();
        while (iter.hasNext()) {

            Map.Entry entry = (Map.Entry) iter.next();

            // check actionJsp if != null => set information for actionJsp
            if (actionJsp != null && "action".equals(entry.getKey().toString())) {
                actionJsp.setActionName(entry.getValue().toString().trim());
            }
            if (actionJsp != null && "namespace".equals(entry.getKey().toString())) {
                actionJsp.setActionNamespace(entry.getValue().toString().trim());
            }

            if (element.tagName().equals("jsp:include") && "page".equals(entry.getKey().toString())) {
                IncludeJsp includeJsp = new IncludeJsp();
                includeJsp.setPage(entry.getValue().toString().trim());
                includeJsp.setTagNode(result);
                this.jspFileNode.addRelativePathJspInclude(includeJsp);
            }
            if ((element.tagName().equals("s:if") || element.tagName().equals("s:elseif"))
                    && "test".equals(entry.getKey().toString())) {
                OgnlJsp ognlJspIf = new OgnlJsp();
                ognlJspIf.setJspTagNode(result);
                ognlJspIf.setOgnl(entry.getValue().toString());
                this.jspFileNode.addOgnl(ognlJspIf);
            }
            if (element.tagName().equals("s:param") && "value".equals(entry.getKey().toString())) {
                OgnlJsp ognlJspIf = new OgnlJsp();
                ognlJspIf.setJspTagNode(result);
                ognlJspIf.setOgnl(entry.getValue().toString());
                this.jspFileNode.addOgnl(ognlJspIf);
            }

            checkMappingNameForm(element, entry, result);

            /**
             * check tag is s:property. put ONGL use to listOgnl
             */
            if (element.tagName().equals("s:property") && entry.getKey().toString().equals("value")) {
                List<String> ognls = supportParserJsp.checkOgnlInAttribute(entry.getValue().toString().trim());

                OgnlJsp ognlJsp = new OgnlJsp();
                ognlJsp.setJspTagNode(result);
                if (ognls.size() > 0) {
                    ognlJsp.setOgnl(ognls.get(0));
                } else {
                    ognlJsp.setOgnl(entry.getValue().toString().trim());
                }
                this.jspFileNode.addOgnl(ognlJsp);
            } else {
                /**
                 * check other struts tag can be contain Ognl
                 */
                List<String> checkOgnl = supportParserJsp.checkOgnlInAttribute(entry.getValue().toString());
                if (checkOgnl != null && element.tagName().charAt(0) == 's') {
                    //logger.debug("los:" +checkOgnl);
                    for (String temp : checkOgnl) {
                        OgnlJsp ognlJsp = new OgnlJsp();
                        ognlJsp.setJspTagNode(result);
                        ognlJsp.setOgnl(temp);
                        this.jspFileNode.addOgnl(ognlJsp);
                    }
                }

                /**
                 * check attribute can contain tag
                 * To do: check and parser internal tag if exsiting
                 */
                boolean checkTagInAttr = supportParserJsp.checkContainTagInAttribute(entry.getValue().toString());
                if (checkTagInAttr) {
                    ActionJsp actionJsp1 = parserInternalTag(entry.getValue().toString());
                    if (actionJsp1 != null) {
                        actionJsp1.setJspTagNode(result);
                        this.jspFileNode.addAction(actionJsp1);
                    }
                    else {
                        OgnlJsp ognlJsp = partInternalOgnlInTag(entry.getValue().toString());
                        //logger.debug(ognlJsp);
                        if(ognlJsp != null) {
                            ognlJsp.setJspTagNode(result);
                            this.jspFileNode.addOgnl(ognlJsp);
                        }
                    }
                }
            }

            attributes.put(entry.getKey().toString(), entry.getValue().toString());
        }

        result.setAttributes(attributes);

        /**
         * if found action in an element
         * add current Action to listAction.
         */
        if (actionJsp != null) {
            /**
             * if action doesn't declare namespace, implicitly use root namespace
             */
            if (actionJsp.getActionNamespace() == null) {
                actionJsp.setActionNamespace("");
            }
            actionJsp.setJspTagNode(result);
            this.jspFileNode.addAction(actionJsp);

        }
        return result;
    }

    private void checkMappingNameForm(Element element, Map.Entry entry, JspTagNode result) {
        if ((element.tagName().equals("s:param") ||
                element.tagName().equals("s:hidden") ||
                element.tagName().equals("s:textfield") ||
                element.tagName().equals("s:textarea") ||
                element.tagName().equals("s:password") ||
                element.tagName().equals("s:file") ||
                element.tagName().equals("s:radio") ||
                element.tagName().equals("s:checkboxlist") ||
                element.tagName().equals("s:select") ||
                element.tagName().equals("s:combobox") ||
                element.tagName().equals("s:doubleselect") ||
                element.tagName().equals("s:text"))
                && "name".equals(entry.getKey().toString())) {
            OgnlJsp ognlJspIf = new OgnlJsp();
            ognlJspIf.setJspTagNode(result);
            ognlJspIf.setFormMapping(true);
            ognlJspIf.setOgnl(entry.getValue().toString());
            this.jspFileNode.addOgnl(ognlJspIf);
        }
    }


    /**
     * @param tag: tag can be contain action in attribute of parent tag
     * @return ActionJsp if found the Action, null if not
     * @function: parser tag internal an attribute in html orr struts tag
     */
    private ActionJsp parserInternalTag(String tag) {
        Document doc = Jsoup.parse(tag);
        if (doc.getAllElements().size() < 5) return null;
        Element element = doc.getAllElements().get(4);
        //logger.debug(element.nodeName());
        if(element.nodeName().equals("s:property")) return null;
        String namespace = element.attributes().get("namespace");
        String action = element.attributes().get("action");
        action = supportParserJsp.splitAction(action);

        if (namespace.equals("") || namespace == null) {
            List<String> re = supportParserJsp.getNamespacefromAction(action);
            if (re != null) {
                namespace = re.get(0);
                action = re.get(1);
            }

        } else {
            action = supportParserJsp.nomalizeActionString(action);
        }
        if (!action.equals("") && action != null) {
            ActionJsp actionJsp = new ActionJsp();
            actionJsp.setActionName(action);
            if (namespace == null) {
                namespace = "";
            }
            actionJsp.setActionNamespace(namespace);
            return actionJsp;
        }
        return null;
    }

    private OgnlJsp partInternalOgnlInTag(String internalTag){
        OgnlJsp result = new OgnlJsp();
        Document doc = Jsoup.parse(internalTag);
        if (doc.getAllElements().size() < 5) return null;
        Element element = doc.getAllElements().get(4);
        if(element.nodeName().equals("s:property")){
            result.setOgnl(element.attributes().get("value"));
        }
        else result = null;
        return  result;
    }


}
