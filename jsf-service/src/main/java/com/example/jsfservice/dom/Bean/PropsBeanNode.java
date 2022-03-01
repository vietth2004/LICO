package com.example.jsfservice.dom.Bean;

import com.example.jsfservice.dom.Properties.PropertiesFileNode;
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
