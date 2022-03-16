package com.example.parserservice.model.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Resource {

    public static final List<String> PARSER = new ArrayList<>(
            Arrays.asList(
                    "java-parser",
                    "spring-parser",
                    "jsf-parser"
            ));
}
