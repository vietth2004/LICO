package com.example.strutservice.tiles.analyzer;

import com.example.strutservice.ast.dependency.Dependency;
import com.example.strutservice.base.BaseAnalyzer;
import com.example.strutservice.dom.Node;
import com.example.strutservice.search.NodeSearch;
import com.example.strutservice.tiles.model.TilesAttribute;
import com.example.strutservice.tiles.model.TilesDefinition;
import com.example.strutservice.tiles.parser.TilesParser;
import com.example.strutservice.tiles.validator.StrutsTitlesPluginValidator;
import com.example.strutservice.utils.Exception.JciaNotFoundException;
import com.example.strutservice.utils.Exception.JciaNotSupportedException;
import com.example.strutservice.utils.JciaData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tiles.Attribute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cuong on 4/15/2017.
 */
public class TilesAnalyzer extends BaseAnalyzer {

    private List<Node> jspFileNodes;
    private Map<String, TilesDefinition> defsMap;
    private Node webXmlNode;

    public TilesAnalyzer(Node webXmlNode) {
        defsMap = new HashMap<>();
        this.webXmlNode = webXmlNode;

    }

    @Override
    public Node analyzeDependencies(Node rootNode) {

        try {


            validator = new StrutsTitlesPluginValidator(webXmlNode);
            if (!validator.isSupported(rootNode)) {
                throw new JciaNotSupportedException("Could not find any tiles listener in web.xml");
            }

            // collect jsp file nodes
            this.jspFileNodes = (List<Node>) JciaData.getInstance().getData(JciaData.JSP_FILE_NODES);

            TilesParser tilesParser = new TilesParser(rootNode);
            defsMap = tilesParser.loadDefinitions();

            // add definitions map to data holder
            JciaData.getInstance().putData(JciaData.TILES_DEFINITIONS_MAP, defsMap);

            //!!!
            doAnalyzing();


        } catch (JciaNotSupportedException e) {

        } catch (JciaNotFoundException e) {

        }

        return rootNode;
    }

    private void doAnalyzing() {
        for (String defName : defsMap.keySet()) {
            TilesDefinition definition = defsMap.get(defName);

            // analyze simple attribute
            for (TilesAttribute attr : definition.getTilesAttributes()) {
                analyzeAttribute(attr);
            }

            // analyze template attribute
            Attribute templateAttr = definition.getTemplateAttribute();
            if (templateAttr != null) {
                TilesAttribute tileTplAttr = new TilesAttribute(definition.getTreeNode(), templateAttr);
                analyzeAttribute(tileTplAttr);
            }

            // analyze definition extension
            analyzeDefinitionExtension(definition);
        }
    }

    private void analyzeDefinitionExtension(TilesDefinition currentDef) {
        String extendsAsStr = currentDef.getExtends();
        if (extendsAsStr == null) return;
        String[] parentDefNames = extendsAsStr.split(",");
        for (String parentDefName : parentDefNames) {
            TilesDefinition parentDef = defsMap.get(parentDefName);
            if (parentDef != null) {
                new Dependency(currentDef.getTreeNode().getId(), parentDef.getTreeNode().getId());
            }
        }
    }

    private void analyzeAttribute(TilesAttribute attr) {
        Object val = attr.getValue();
        if (val instanceof String) {

            String attrVal = (String) val;
            // support for jsp file
            if (attrVal.endsWith(".jsp")) {
                Node attrJspNode = NodeSearch.searchOneNode(jspFileNodes, attrVal);
                if (attrJspNode != null) {
                    new Dependency(attr.getTreeNode().getId(), attrJspNode.getId());
                }
            }
        }
    }

    public Map<String, TilesDefinition> getDefsMap() {
        return defsMap;
    }
}
