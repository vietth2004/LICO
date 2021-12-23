package com.example.xmlservice.dom.Bean;

import com.example.xmlservice.dom.Properties.PropertiesFileNode;
import com.example.xmlservice.dom.Properties.PropertiesNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropsBeanNode {

    private String beanName;
    private PropertiesFileNode value;

}
