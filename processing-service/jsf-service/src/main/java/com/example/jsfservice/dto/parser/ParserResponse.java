package com.example.jsfservice.dto.parser;

import com.example.jsfservice.ast.dependency.Dependency;
import com.example.jsfservice.dom.Node;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParserResponse {

    private List<Node> allNodes;
    private List<Dependency> allDependencies;

}
