package com.example.gitservice.dto;

import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Getter
public class ErrorMessage implements Serializable {

    private static final long serialVersionUID = 34728752345L;

    private int statusCode;
    private Date timestamp;
    private String message;
    private String description;

    public ErrorMessage(int statusCode, Date timestamp, String message, String description) {
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.message = message;
        this.description = description;
    }
}