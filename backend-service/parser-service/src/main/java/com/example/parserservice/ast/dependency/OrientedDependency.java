package com.example.parserservice.ast.dependency;

import java.util.List;

public class OrientedDependency {


    private Integer callerNode;

    private List<OrientedPair> calleeNodes;

    public OrientedDependency() {
    }

    public OrientedDependency(Integer callerNode, OrientedPair calleeNodes) {
        this.callerNode = callerNode;
        this.calleeNodes.add(calleeNodes);
    }

    public OrientedDependency(Integer callerNode, List<OrientedPair> calleeNodes) {
        this.callerNode = callerNode;
        this.calleeNodes = calleeNodes;
    }

    public Integer getCallerNode() {
        return callerNode;
    }

    public void setCallerNode(Integer callerNode) {
        this.callerNode = callerNode;
    }

    public List<OrientedPair> getCalleeNodes() {
        return calleeNodes;
    }

    public void setCalleeNodes(List<OrientedPair> calleeNodes) {
        this.calleeNodes = calleeNodes;
    }

    public boolean contains(Integer id) {

        if (this.callerNode.equals(id)) {
            return true;
        }

        return false;
    }
}
