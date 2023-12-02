package com.example.parserservice.model.jsf;

import com.example.parserservice.ast.dependency.Dependency;
import com.example.parserservice.dom.Node;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JSFResponse {

    private List<Node> allNodes;
    private List<Dependency> allDependencies;

}
