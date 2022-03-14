package com.example.jsfservice.dto.compare;

import com.example.jsfservice.ast.dependency.Dependency;
import com.example.jsfservice.dom.Node;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompareResponse {
    private List<Node> addedNodes = new ArrayList<>();
    private List<Node> deletedNodes = new ArrayList<>();
    private List<Node> changedNodes = new ArrayList<>();
    private List<Node> unchanged = new ArrayList<>();
    private List<Node> allNodes;
}
