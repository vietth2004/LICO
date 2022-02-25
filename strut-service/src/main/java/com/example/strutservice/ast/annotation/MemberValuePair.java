package com.example.strutservice.ast.annotation;

public class MemberValuePair {
    private String key = new String();
    private String value = new String();

    public MemberValuePair() {
    }

    public MemberValuePair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
