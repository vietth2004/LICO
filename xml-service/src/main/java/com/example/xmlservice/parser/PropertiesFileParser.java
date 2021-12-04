package com.example.xmlservice.parser;

import com.example.xmlservice.dom.Node;
import com.example.xmlservice.dom.Properties.PropertiesFileNode;
import com.example.xmlservice.dom.Properties.PropertiesNode;
import com.example.xmlservice.utils.Exception.JciaNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.InputSource;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PropertiesFileParser implements IParser, IPathParser{

    private static final Logger logger = LogManager.getLogger(PropertiesFileParser.class);

    @Override
    public Node parse(String path) throws JciaNotFoundException {
        PropertiesFileNode fileNode = new PropertiesFileNode();
        fileNode.setAbsolutePath(path);
        fileNode.setEntityClass("PropertiesFileNode");
        return parse(fileNode);
    }

    @Override
    public Node parse(Node node) throws JciaNotFoundException {
        try {
            PropertiesFileNode propertiesFileNode = (PropertiesFileNode) node;
            FileInputStream fis = new FileInputStream(node.getAbsolutePath());
            InputSource is = new InputSource(fis);

            propertiesFileNode.setName(new File((node.getAbsolutePath())).getName());
            List<PropertiesNode> propertiesNodes = parsePropertiesNode(node.getAbsolutePath());
            propertiesFileNode.setProperties(propertiesNodes);

            return propertiesFileNode;

        } catch (FileNotFoundException e) {
            logger.error(String.format("not found file [%s]", node.getAbsolutePath()));
        }
        return null;
    }

    public List<PropertiesNode> parsePropertiesNode(String path) {
        List<PropertiesNode> nodes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    if(line.charAt(0) != 35) {
                        PropertiesNode propertiesNode = new PropertiesNode();
                        propertiesNode.setName(line.split("=")[0]);
                        propertiesNode.setValue(line.split("=")[1]);
                        nodes.add(propertiesNode);
                    }
                } catch (StringIndexOutOfBoundsException e) {
//                    e.printStackTrace();
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return nodes;
    }

}
