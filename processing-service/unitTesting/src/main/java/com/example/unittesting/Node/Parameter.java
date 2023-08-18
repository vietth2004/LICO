package com.example.unittesting.Node;

public class Parameter {
    private String nameParameter;
    private String type;

    public Parameter(String nameParameter, String type) {
        this.nameParameter = nameParameter;
        this.type = type;
    }

    public String getNameParameter() {
        return nameParameter;
    }

    public void setParameter(String nameParameter) {
        this.nameParameter = nameParameter;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
