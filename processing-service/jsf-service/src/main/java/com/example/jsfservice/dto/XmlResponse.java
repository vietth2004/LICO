package com.example.jsfservice.dto;

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
public class XmlResponse {

    private List<Node> allXmlNodes = new ArrayList<>();

}
