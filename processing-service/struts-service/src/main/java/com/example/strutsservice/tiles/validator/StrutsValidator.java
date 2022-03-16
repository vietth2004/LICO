package com.example.strutsservice.tiles.validator;

import com.example.strutsservice.base.Validator;
import com.example.strutsservice.condition.ICondition;
import com.example.strutsservice.condition.XmlTagNodeWithContentCondition;
import com.example.strutsservice.constant.StrutsClassType;
import com.example.strutsservice.dom.Node;
import com.example.strutsservice.search.NodeSearch;

/**
 * Created by cuong on 4/15/2017.
 */
public class StrutsValidator extends Validator {
    protected Node webXmlNode;

    public StrutsValidator(Node webXmlNode) {
        super();
        this.webXmlNode = webXmlNode;
    }

    @Override
    public boolean isSupported(Node rootNode) {
        return hasConfigurationInWebXml();
    }

    protected boolean hasConfigurationInWebXml() {
        if (checkFilterNode(StrutsClassType.STRUTS_PREPARE_AND_EXECUTE_FILTER)
                || checkFilterNode(StrutsClassType.STRUTS_PREPARE_AND_EXECUTE_FILTER_NG)
                || checkFilterNode(StrutsClassType.STRUTS_FILTER_DISPATCHER)) {
            return true;
        }
        //log for client

        return false;
    }

    protected boolean checkFilterNode(String filterName) {
        ICondition strutsFilterTagNodeCondition = new XmlTagNodeWithContentCondition(filterName);
        Node strutsFilterTagNode = NodeSearch.searchOneNode(webXmlNode, strutsFilterTagNodeCondition);
        return (strutsFilterTagNode != null);
    }
}
