package com.example.strutsservice.search;

import com.example.strutsservice.condition.ICondition;
import com.example.strutsservice.dom.Node;
import com.example.strutsservice.utils.Helper.FileHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: improve search engine
 * Created by jcia on 23/03/2017.
 */
public class NodeSearch {

    public static List<Node> searchNode(Node n, ICondition condition, String relativePath) {
        List<Node> listNode = new ArrayList<>();

        if (n != null && n.getNodeChildren() != null) {
            for (Node child : n.getNodeChildren()) {
                if (condition.isSatisfiable(child)) {
                    relativePath = FileHelper.normalizePath(relativePath);
                    String absolutePath = child.getAbsolutePath();

                    if (absolutePath != null && absolutePath.endsWith(relativePath)) {
                        listNode.add(child);
                    }
                }
                // recursive
                listNode.addAll(searchNode(child, condition, relativePath));
            }
        }
        return listNode;
    }

    public static List<Node> searchNode(Node n, ICondition condition) {
        List<Node> listNode = new ArrayList<>();
        if (n != null && n.getNodeChildren() != null)
            for (Node child : n.getNodeChildren()) {
                if (condition.isSatisfiable(child))
                    listNode.add(child);

                listNode.addAll(searchNode(child, condition));
            }
        return listNode;
    }

    public static List<Node> searchNode(Node rootNode, String relativePath) {
        List<Node> listNode = new ArrayList<>();
        if (rootNode != null) {
            for (Node child : rootNode.getNodeChildren()) {
                relativePath = FileHelper.normalizePath(relativePath);
                String absolutePath = child.getAbsolutePath();
                if (absolutePath != null && absolutePath.endsWith(relativePath)) {
                    listNode.add(child);
                }
                listNode.addAll(searchNode(child, relativePath));
            }
        }
        return listNode;
    }

    public static List<Node> searchNode(List<Node> listNode, String relativePath) {
        List<Node> output = new ArrayList<>();
        for (Node item : listNode) {
            relativePath = FileHelper.normalizePath(relativePath);
            String absolutePath = item.getAbsolutePath();
            if (absolutePath != null && absolutePath.endsWith(relativePath)) {
                output.add(item);
            }
        }
        return output;
    }

    public static List<Node> searchNode(List<Node> listNode, ICondition condition, String relativePath) {
        List<Node> output = new ArrayList<>();
        for (Node item : listNode) {
            if (condition.isSatisfiable(item)) {
                relativePath = FileHelper.normalizePath(relativePath);
                String absolutePath = item.getAbsolutePath();
                if (absolutePath != null && absolutePath.endsWith(relativePath)) {
                    output.add(item);
                }
            }
        }
        return output;
    }

    public static List<Node> searchNode(List<Node> listNode, ICondition condition) {
        List<Node> output = new ArrayList<>();
        for (Node item : listNode) {
            if (condition.isSatisfiable(item)) {
                output.add(item);
            }
        }
        return output;
    }

    public static Node searchById(Node rootNode, int nodeId) {
        if (rootNode == null || rootNode.getId() == nodeId) {
            return rootNode;
        }

        for (int i = rootNode.getNodeChildren().size() - 1; i >= 0; i--) {
            Node child = rootNode.getNodeChildren().get(i);
            if (child.getId() <= nodeId) {
                Node res = searchById(child, nodeId);
                if (res != null) return res;
            }
        }
        return null;
    }

    public static Node searchOneNode(Node n, ICondition condition) {
        List<Node> list = searchNode(n, condition);
        logWarn(list);
        return (list.size() == 1) ? list.get(0) : null;
    }

    public static Node searchOneNode(Node n, ICondition condition, String relativePath) {
        List<Node> list = searchNode(n, condition, relativePath);
        logWarn(list);
        return (list.size() == 1) ? list.get(0) : null;
    }

    public static Node searchOneNode(List<Node> listNode, ICondition condition, String relativePath) {
        List<Node> list = searchNode(listNode, condition, relativePath);
        logWarn(list);
        return (list.size() == 1) ? list.get(0) : null;
    }

    public static Node searchOneNode(List<Node> listNode, String relativePath) {
        List<Node> list = searchNode(listNode, relativePath);
        logWarn(list);
        return (list.size() == 1) ? list.get(0) : null;
    }

    public static Node searchOneNode(List<Node> listNode, ICondition condition) {
        List<Node> list = searchNode(listNode, condition);
        logWarn(list);
        return (list.size() == 1) ? list.get(0) : null;
    }

    public static Node searchOneNode(Node rootNode, String relativePath) {

        List<Node> list = searchNode(rootNode, relativePath);
        logWarn(list);
        return (list.size() == 1) ? list.get(0) : null;
    }

    public static void logWarn(List<Node> list) {
        if (list.size() > 1) {
            ArrayList<String> pathList = new ArrayList<>();
            for (Node node : list)
                pathList.add(node.getRelativePath());
        }
    }
}
