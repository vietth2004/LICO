package com.example.springservice.dom;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Created by jcia on 24/03/2017.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.MINIMAL_CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "json_type")
public class Element {
}
