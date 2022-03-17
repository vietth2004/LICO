package com.example.springservice.tiles.parser;

import com.example.springservice.dom.Node;
import com.example.springservice.dom.Xml.XmlFileNode;
import com.example.springservice.search.NodeSearch;
import com.example.springservice.tiles.model.TilesDefinition;
import com.example.springservice.utils.Exception.JciaNotFoundException;
import com.example.springservice.utils.JciaData;
import com.example.springservice.utils.Type.ComponentType;

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
