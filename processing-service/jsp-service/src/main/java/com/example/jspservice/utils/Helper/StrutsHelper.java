package com.example.jspservice.utils.Helper;

import com.example.jspservice.ast.dependency.Dependency;
import com.example.jspservice.condition.ICondition;
import com.example.jspservice.condition.XmlFileNodeCondition;
import com.example.jspservice.condition.XmlTagNodeLocationCondition;
import com.example.jspservice.constant.StrutsClassType;
import com.example.jspservice.constant.StrutsXmlTag;
import com.example.jspservice.dom.Jsp.IStrutsElementLevel1;
import com.example.jspservice.dom.Jsp.StrutsIncludeEntry;
import com.example.jspservice.dom.Jsp.StrutsPackage;
import com.example.jspservice.dom.Node;
import com.example.jspservice.dom.Xml.XmlFileNode;
import com.example.jspservice.dom.Xml.XmlTagNode;
import com.example.jspservice.search.NodeSearch;
import com.opensymphony.xwork2.config.entities.InterceptorConfig;
import com.opensymphony.xwork2.config.entities.InterceptorMapping;
import com.opensymphony.xwork2.config.entities.InterceptorStackConfig;
import com.opensymphony.xwork2.util.location.Located;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by dinht_000 on 3/22/2017.
 */
public class StrutsHelper {

    private static final Logger logger = LogManager.getLogger(StrutsHelper.class);

    public static StrutsIncludeEntry generateStrutsIncludeElement(XmlTagNode node) {
        StrutsIncludeEntry include = new StrutsIncludeEntry(node);

        String file = node.getAttributes().get(StrutsXmlTag.FILE_ATTR);

        if (file != null && !file.isEmpty())
            include.setFileName(file);

        return include;
    }

    public static Node getTagNodeByLocation(XmlFileNode strutsCfgFileNode, Located locatedComponent) {
        if (locatedComponent == null) return null;
        int lineNumber = locatedComponent.getLocation().getLineNumber();
        int columnNumber = locatedComponent.getLocation().getColumnNumber();
        ICondition xmlTagNodeCondition = new XmlTagNodeLocationCondition(lineNumber, columnNumber);
        return NodeSearch.searchOneNode(strutsCfgFileNode, xmlTagNodeCondition);
    }

    /**
     * Find interceptor or interceptor-stack in a package by given name
     *
     * @param strutsPackage
     * @param interceptorName
     * @return
     */
    public static Located getInterceptorByName(StrutsPackage strutsPackage, String interceptorName) {
        Map<String, Object> interceptorConfigs = strutsPackage.getAllInterceptorConfigs();
        for (String iName : interceptorConfigs.keySet()) {
            if (iName.equals(interceptorName)) {
                return (Located) interceptorConfigs.get(iName);
            }
        }
        return null;
    }

    public static String getLocationPath(Located locatedComponent) {
        String xmlPath = locatedComponent.getLocation().getURI();
        // support for xml file which is only in project scoped
        if (xmlPath.startsWith("file:/")) {
            xmlPath = xmlPath.substring(6);
            xmlPath = FileHelper.normalizePath(xmlPath);

            // for linux system uri
            if ("/".equals(File.separator)) {
                xmlPath = "/" + xmlPath;
            }

            // replace %20 by space character
            xmlPath = xmlPath.replace("%20", " ");

            return xmlPath;
        } else {
//            logger.warn("Struts configuration resources is ignored [" + xmlPath + "]");
            return null;
        }
    }

    public static String getDefaultResultParam(String resultTypeClass) {
        switch (resultTypeClass) {
            case StrutsClassType.STRUTS_RESULT_SUPPORT:
                return "location";

            // 11 default struts 2 result type classes
            case StrutsClassType.ACTION_CHAIN_RESULT_TYPE:
            case StrutsClassType.SERVLET_ACTION_REDIRECT_RESULT_TYPE:
                return "actionName";
            case StrutsClassType.HTTP_HEADER_RESULT_TYPE:
                return "";
            case StrutsClassType.STREAM_RESULT_TYPE:
                return "inputName";
            case StrutsClassType.XSLT_RESULT_TYPE:
                return "stylesheetLocation";
            case StrutsClassType.SERVLET_DISPATCHER_RESULT_TYPE:
            case StrutsClassType.FREE_MARKER_RESULT_TYPE:
            case StrutsClassType.SERVLET_REDIRECT_RESULT_TYPE:
            case StrutsClassType.VELOCITY_RESULT_TYPE:
            case StrutsClassType.PLAIN_TEXT_RESULT_TYPE:
            case StrutsClassType.POST_BACK_RESULT_TYPE:
                return "location";

            // tiles plugin
            case StrutsClassType.TITLES_RESULT_TYPE:
                return "location";
            default:
                return "";
        }
    }

    public static void analyzeInterceptorRef(InterceptorMapping mapping,
                                             IStrutsElementLevel1 strutsElement, List<Node> strutsXmlFileNodes) {
        String interceptorName = mapping.getName();
        Located interceptorConfig = StrutsHelper.getInterceptorByName(
                strutsElement.getStrutsPackage(), interceptorName);

        String strutsXmlPath = StrutsHelper.getLocationPath(interceptorConfig);
        if (strutsXmlPath != null) {
            XmlFileNode strutsXmlNode = (XmlFileNode) NodeSearch.searchOneNode(
                    strutsXmlFileNodes, new XmlFileNodeCondition(), strutsXmlPath);
            if (strutsXmlNode != null) {
                Node interceptorTagNode = StrutsHelper.getTagNodeByLocation(strutsXmlNode, interceptorConfig);
                if (interceptorTagNode != null) {

                    Node interceptorRefTagNode = findInterceptorRefTagNode(strutsElement.getTreeNode(), interceptorName);
                    if (interceptorRefTagNode != null) {
                        if (interceptorConfig instanceof InterceptorConfig) {
                            generateInterceptorRefDependency(interceptorRefTagNode, interceptorTagNode);
                        } else if (interceptorConfig instanceof InterceptorStackConfig) {
                            generateInterceptorStackRefDependency(interceptorRefTagNode, interceptorTagNode);
                        }
                    }
                }
            }
        }

    }

    private static Node findInterceptorRefTagNode(Node parentTagNode, String interceptorName) {
        if (parentTagNode instanceof XmlTagNode) {
            List<Node> allTagNodes = parentTagNode.getAllChildren();
            allTagNodes.add(parentTagNode);

            for (Node tagNode : allTagNodes) {
                XmlTagNode interceptorRefTagNode = (XmlTagNode) tagNode;
                if ("interceptor-ref".equals(interceptorRefTagNode.getTagName())) {
                    if (interceptorRefTagNode.getAttributes().get("name").equals(interceptorName)) {
                        return interceptorRefTagNode;
                    }
                }
            }
        }
        return null;

    }

    private static Dependency generateInterceptorRefDependency(Node positive, Node negative) {
        return new Dependency(positive.getId(), negative.getId());
    }

    private static Dependency generateInterceptorStackRefDependency(Node positive, Node negative) {
        return new Dependency(positive.getId(), negative.getId());
    }
}
