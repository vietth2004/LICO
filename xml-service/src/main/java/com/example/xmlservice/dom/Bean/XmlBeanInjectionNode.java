package com.example.xmlservice.dom.Bean;

import com.example.xmlservice.dom.Node;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class XmlBeanInjectionNode {
    private String beanInjection;
    private Node value;
}
