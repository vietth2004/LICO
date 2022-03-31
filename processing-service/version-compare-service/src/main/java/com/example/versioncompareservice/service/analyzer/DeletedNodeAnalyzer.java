package com.example.versioncompareservice.service.analyzer;

import com.example.versioncompareservice.constant.CompareStatus;
import com.example.versioncompareservice.dom.Node;
import com.example.versioncompareservice.utils.CompareUtils;
import com.example.versioncompareservice.utils.ParserUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@Slf4j
public class DeletedNodeAnalyzer implements Callable {

    private List<Node> oldVer;
    private List<Node> newVer;

    public DeletedNodeAnalyzer(List<Node> oldVer, List<Node> newVer) {
        this.oldVer = oldVer;
        this.newVer = newVer;
    }

    @Override
    public List<Node> call() throws Exception {
        log.info("Analyzing deleted nodes...");
        List<Node> deletedNodes = new ArrayList<>();

        List<Node> children = ParserUtils.getChildrenLevel1XmlFileNode(oldVer);
        for(Node node : oldVer)
            if(CompareUtils.searchByFullyQualifiedName(newVer, node.getFullyQualifiedName()).size() <= 0)
                deletedNodes.add(node);
        for(Node child : children) {
            deletedNodes.addAll(analyzeChild(newVer, child));
        }
        deletedNodes.forEach(node -> {
            node.setStatus(CompareStatus.DELETED);
            node.setId(node.getId() * -1);
            node.setNodeChildren(new ArrayList<>());
        });
        return deletedNodes;
    }

    public List<Node> analyzeChild(List<Node> newVer, Node node) {
        List<Node> deletedNodes = new ArrayList<>();
        if(CompareUtils.searchByFullyQualifiedName(newVer, node.getFullyQualifiedName()).size() <= 0)
            deletedNodes.add(node);
        for(Node child : node.getChildren()) {
            deletedNodes.addAll(analyzeChild(newVer, child));
        }
        return deletedNodes;
    }
}
