package com.example.xmlservice.resource;

import java.util.Arrays;
import java.util.List;

public class Resource {

    public List<String> JSF_BEAN_ANNOTATION = Arrays.asList(
            "javax.inject.Named", "javax.enterprise.inject.Model",
            "javax.faces.bean.ManagedBean", "javax.faces.bean.ReferencedBean"
    );

    public List<String> JSF_BEAN_INJECT = Arrays.asList(
            "javax.faces.bean.ManagedProperty"
    );

}
