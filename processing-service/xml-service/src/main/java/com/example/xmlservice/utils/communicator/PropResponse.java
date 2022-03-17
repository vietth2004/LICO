package com.example.xmlservice.utils.communicator;

import com.example.xmlservice.dom.Properties.PropertiesFileNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PropResponse {
    List<PropertiesFileNode> propertiesNodes;
}
