package com.example.springservice.utils.worker;

import com.example.springservice.dom.Node;

import java.util.ArrayList;
import java.util.List;

public class Filter {

    public static List<Node> filteringNullNodes(List<Node> xmlNodes) {

        List<Node> newXmlNodes = new ArrayList<>();

        for (Node node : xmlNodes) {
            if (node != null) {
                newXmlNodes.add(node);
            }
        }

        return newXmlNodes;

    }
}
