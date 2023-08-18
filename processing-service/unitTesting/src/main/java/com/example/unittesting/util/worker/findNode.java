package com.example.unittesting.util.worker;

import com.fasterxml.jackson.databind.JsonNode;

public class findNode {
    public static JsonNode getNodeById(int targetId, JsonNode node){
        if (node.get("id").asInt() == targetId) {
            return node;
        }
        if (node.has("children")) {
            for (JsonNode child : node.get("children")) {
                JsonNode result = getNodeById(targetId, child);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }
    public static String getNodeContentById(int targetId, JsonNode rootNode) {
        JsonNode nodeWithId = getNodeById(targetId, rootNode);
        if (nodeWithId != null) {
            return nodeWithId.toString();
        }
        return "";
    }


}
