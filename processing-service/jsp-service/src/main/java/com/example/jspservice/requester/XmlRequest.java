package com.example.jspservice.requester;

import com.example.jspservice.dom.Node;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class XmlRequest {

    private String path;

    public XmlRequest() {
    }

    public XmlRequest(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<Node> getXmlNode(String address){

        RestTemplate restTemplate = new RestTemplate();
        List<Node> xmlNodes = restTemplate.postForObject(
                "http://localhost:7006/api/xml-service/pathParse/old" //xml-service
                , new XmlRequest(address)
                , List.class
                );

        return xmlNodes;

    }
}
