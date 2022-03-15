package com.example.strutsservice.dom.Properties;

import com.example.strutsservice.dom.Node;
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
