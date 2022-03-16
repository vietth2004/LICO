package com.example.jsfservice.compare.thread;

import com.example.jsfservice.constant.CompareStatus;
import com.example.jsfservice.dom.Node;
import com.example.jsfservice.utils.CompareUtils;
import com.example.jsfservice.utils.ParserUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@Slf4j
public class AddedNodeAnalyzer implements Callable {

    private List<Node> oldVer;
    private List<Node> newVer;

    public AddedNodeAnalyzer(List<Node> oldVer, List<Node> newVer) {
        this.oldVer = oldVer;
        this.newVer = newVer;
    }

    @Override
    public List<Node> call() throws Exception {
        log.info("Analyzing added nodes...");
        List<Node> addedNodes = new ArrayList<>();

        List<Node> children = ParserUtils.getChildrenLevel1XmlFileNode(newVer);
        for(Node node : newVer)
            if(CompareUtils.searchByFullyQualifiedName(oldVer, node.getFullyQualifiedName()).size() <= 0)
                addedNodes.add(node);
        for(Node child : children) {
            addedNodes.addAll(analyzeChild(oldVer, child));
        }
        addedNodes.forEach(node -> {
            node.setStatus(CompareStatus.ADDED);
            node.setChildren(new ArrayList<>());
        });
        return addedNodes;
    }

    public List<Node> analyzeChild(List<Node> oldVer, Node node) {
        List<Node> addedNodes = new ArrayList<>();
        if(CompareUtils.searchByFullyQualifiedName(oldVer, node.getFullyQualifiedName()).size() <= 0)
            addedNodes.add(node);
        for(Node child : node.getChildren()) {
            addedNodes.addAll(analyzeChild(oldVer, child));
        }
        return addedNodes;
    }

}
