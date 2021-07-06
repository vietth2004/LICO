package com.example.springservice.dependency;

import com.example.springservice.ast.node.Node;

public class Dependency {
    public Node callerNode = new Node();

    public Node calleeNode = new Node();

    public Dependency(Node callerNode, Node calleeNode) {
        this.callerNode = callerNode;
        this.calleeNode = calleeNode;
    }

    public Node getCallerNode() {
        return callerNode;
    }

    public void setCallerNode(Node callerNode) {
        this.callerNode = callerNode;
    }

    public Node getCalleeNode() {
        return calleeNode;
    }

    public void setCalleeNode(Node calleeNode) {
        this.calleeNode = calleeNode;
    }
}
