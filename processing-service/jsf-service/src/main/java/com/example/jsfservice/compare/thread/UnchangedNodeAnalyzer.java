package com.example.jsfservice.compare.thread;

import com.example.jsfservice.constant.CompareStatus;
import com.example.jsfservice.dom.Node;
import com.example.jsfservice.utils.CompareUtils;
import com.example.jsfservice.utils.ParserUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

@Slf4j
public class UnchangedNodeAnalyzer implements Callable {

    private List<Node> oldVer;
    private List<Node> newVer;

    public UnchangedNodeAnalyzer(List<Node> oldVer, List<Node> newVer) {
        this.oldVer = oldVer;
        this.newVer = newVer;
    }

    @Override
    public Set<Node> call() throws Exception {
        log.info("Analyzing unchanged nodes...");
        Set<Node> unchangedNodes = new HashSet<>();

        List<Node> children = ParserUtils.getChildrenLevel1XmlFileNode(newVer);
        for(Node node : newVer)
            if(CompareUtils.searchByFullyQualifiedName(oldVer, node.getFullyQualifiedName()).size() > 0) {
                List<Node> found = CompareUtils.searchByFullyQualifiedName(oldVer, node.getFullyQualifiedName());
                found.forEach(el -> {
                    if(node.toString().equals(el.toString())) {
                        unchangedNodes.add(node);
                    }
                });
            }
        for(Node child : children) {
            unchangedNodes.addAll(analyzeChild(oldVer, child));
        }
        unchangedNodes.forEach(node -> {
            node.setStatus(CompareStatus.UNCHANGED);
            node.setNodeChildren(new ArrayList<>());
        });
        return unchangedNodes;
    }

    public Set<Node> analyzeChild(List<Node> oldVer, Node node) {
        Set<Node> unchangedNodes = new HashSet<>();
        if(CompareUtils.searchByFullyQualifiedName(oldVer, node.getFullyQualifiedName()).size() > 0) {
            List<Node> found = CompareUtils.searchByFullyQualifiedName(oldVer, node.getFullyQualifiedName());
            found.forEach(el -> {
                if(node.toString().equals(el.toString())) {
                    unchangedNodes.add(node);
                }
            });
        }
        for(Node child : node.getChildren()) {
            unchangedNodes.addAll(analyzeChild(oldVer, child));
        }
        return unchangedNodes;
    }

}
