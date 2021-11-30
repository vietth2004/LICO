package com.example.xmlservice.dom.Bean;

import com.example.xmlservice.ast.node.JavaNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsfBeanInjectionNode {
    private String beanInjection;
    private JavaNode value;
}
