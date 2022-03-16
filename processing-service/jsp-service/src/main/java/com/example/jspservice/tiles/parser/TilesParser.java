package com.example.jspservice.tiles.parser;

import com.example.jspservice.dom.Node;
import com.example.jspservice.dom.Xml.XmlFileNode;
import com.example.jspservice.search.NodeSearch;
import com.example.jspservice.tiles.model.TilesDefinition;
import com.example.jspservice.utils.Exception.JciaNotFoundException;
import com.example.jspservice.utils.JciaData;
import com.example.jspservice.utils.Type.ComponentType;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by cuong on 5/13/2017.
 */
public class TilesParser {

    private Node rootNode;
    public static final String TILES_FILE_NAME = "tiles.xml";

    public TilesParser(Node rootNode) {
        this.rootNode = rootNode;
    }

    public Map<String, TilesDefinition> loadDefinitions() throws JciaNotFoundException {
        JciaDigesterDefinitionsReader reader = new JciaDigesterDefinitionsReader();
        Map<String, TilesDefinition> defsMap;

        if (rootNode == null || rootNode.getAbsolutePath() == null) {
            throw new JciaNotFoundException("Root node path not found");
        }

        Node tilesXmlNode = NodeSearch.searchOneNode(
                (List<Node>) JciaData.getInstance().getData(JciaData.XML_FILE_NODES), "/" + TILES_FILE_NAME);

        if (tilesXmlNode == null) {
            throw new JciaNotFoundException("tiles.xml not found");
        }

        try {
            defsMap = reader.read2((XmlFileNode) tilesXmlNode);

            // add tier for tiles.xml
            tilesXmlNode.addComponentType(ComponentType.TILES_CONFIG);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new JciaNotFoundException(String.format("Not found [%s]", tilesXmlNode.getAbsolutePath()));
        } catch (IOException e) {
            throw new JciaNotFoundException(String.format("IOException [%s]", e.getMessage()));
        }

        return defsMap;
    }
}
