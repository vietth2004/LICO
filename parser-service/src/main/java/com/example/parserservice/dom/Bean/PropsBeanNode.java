package com.example.parserservice.dom.Bean;

import com.example.parserservice.dom.Properties.PropertiesFileNode;
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
