package com.example.jsfservice.dto;

import com.example.jsfservice.ast.dependency.Dependency;
import com.example.jsfservice.ast.node.JavaNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JavaNodeRequest {
    private List<JavaNode> allNodes;
    private List<Dependency> allDependencies;
    private JavaNode rootNode;
}
