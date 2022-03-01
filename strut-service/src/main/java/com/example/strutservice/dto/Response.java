package com.example.strutservice.dto;

import com.example.strutservice.dom.Node;
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
public class Response {

    private List<Node> allXmlNodes = new ArrayList<>();

}
