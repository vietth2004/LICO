package com.example.versioncompareservice.service.analyzer;

import com.example.versioncompareservice.constant.CompareStatus;
import com.example.versioncompareservice.dom.Node;
import com.example.versioncompareservice.utils.CompareUtils;
import com.example.versioncompareservice.utils.ParserUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

@Slf4j
public class ChangedNodeAnalyzer implements Callable {

    private List<Node> oldVer;
    private List<Node> newVer;

    public ChangedNodeAnalyzer(List<Node> oldVer, List<Node> newVer) {
        this.oldVer = oldVer;
        this.newVer = newVer;
    }

    @Override
    public Set<Node> call() throws Exception {
        log.info("Analyzing changed nodes...");
        Set<Node> changedNodes = new HashSet<>();

        List<Node> children = ParserUtils.getChildrenLevel1XmlFileNode(newVer);
        for(Node node : newVer)
            if(CompareUtils.searchByFullyQualifiedName(oldVer, node.getFullyQualifiedName()).size() > 0) {
                List<Node> found = CompareUtils.searchByFullyQualifiedName(oldVer, node.getFullyQualifiedName());
                found.forEach(el -> {
                    if(!node.toString().equals(el.toString())) {
                        changedNodes.add(node);
                    }
                });
            }
        for(Node child : children) {
            changedNodes.addAll(analyzeChild(oldVer, child));
        }
        changedNodes.forEach(node -> {
            node.setStatus(CompareStatus.CHANGED);
            node.setNodeChildren(new ArrayList<>());
        });
        return changedNodes;
    }

    public Set<Node> analyzeChild(List<Node> oldVer, Node node) {
        Set<Node> changedNodes = new HashSet<>();
        if(CompareUtils.searchByFullyQualifiedName(oldVer, node.getFullyQualifiedName()).size() > 0){
            List<Node> found = CompareUtils.searchByFullyQualifiedName(oldVer, node.getFullyQualifiedName());
            found.forEach(el -> {
                if(!node.toString().equals(el.toString())) {
                    changedNodes.add(node);
                }
            });
        }
        for(Node child : node.getChildren()) {
            changedNodes.addAll(analyzeChild(oldVer, child));
        }
        return changedNodes;
    }

}
