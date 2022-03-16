package com.example.jspservice.tiles.validator;

import com.example.jspservice.condition.ICondition;
import com.example.jspservice.condition.XmlTagNodeWithContentCondition;
import com.example.jspservice.constant.StrutsClassType;
import com.example.jspservice.dom.Node;
import com.example.jspservice.search.NodeSearch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by cuong on 4/15/2017.
 */
public class StrutsTitlesPluginValidator extends StrutsValidator {

    private static final Logger logger = LogManager.getLogger(StrutsTitlesPluginValidator.class);

    public StrutsTitlesPluginValidator(Node webXmlNode) {
        super(webXmlNode);
    }

    @Override
    protected boolean hasConfigurationInWebXml() {
        ICondition strutsFilterTagNodeCondition =
                new XmlTagNodeWithContentCondition(StrutsClassType.STRUTS_TILES_LISTENER);
        Node strutsFilterTagNode = NodeSearch.searchOneNode(webXmlNode, strutsFilterTagNodeCondition);
        candidates = new Node[]{strutsFilterTagNode};
        if (strutsFilterTagNode != null) return true;
        return false;
    }
}
