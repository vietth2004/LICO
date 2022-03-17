package com.example.springservice.dom.Properties;

import com.example.springservice.dom.Node;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertiesFileNode extends Node {
    List<PropertiesNode> properties;
}
