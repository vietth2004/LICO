package com.example.xmlservice.dto;

import com.example.xmlservice.ast.node.JavaNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JavaNodeRequest {
    private JavaNode rootNode;
    private int totalNodes;
    private String address;
}
