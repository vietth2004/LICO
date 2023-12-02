package com.example.versioncompareservice.utils;

import com.example.versioncompareservice.dom.Node;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompareUtils {

    public int getMaxIdNode(Node fileNode) {
        int max = 0;
        max = fileNode.getId();
        for (Node tagNode : fileNode.getChildren()) {
            if (max <= tagNode.getId())
                max = tagNode.getId();
        }
        return max;
    }

    /**
     * Recalculate id in all xml nodes by total javaNods
     *
     * @param javaTotalNodesId
     * @param nodes
     */
    public static void reCalculateXmlNodesId(int javaTotalNodesId, List<Node> nodes) {
        nodes.forEach(node -> {
            int id = node.getId();
            node.setId(id -= javaTotalNodesId);
            if (node.getNodeChildren().size() > 0) {
                reCalculateXmlNodesId(javaTotalNodesId, node.getNodeChildren());
            }
        });
    }

    public Node searchById(List<Node> nodes, int id) {
        for (Node node : nodes) {
            if (node.getId() == id)
                return node;
            else
                searchById(node.getNodeChildren(), id);
        }
        return null;
    }

    public static List<Node> searchByFullyQualifiedName(List<Node> nodes, String query) {
        List<Node> result = new ArrayList<>();
        for (Node node : nodes) {
            if (node.getFullyQualifiedName().equals(query))
                result.add(node);
            else
                result.addAll(searchByFullyQualifiedName(node.getNodeChildren(), query));
        }
        return result;
    }

}
