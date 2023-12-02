package com.example.gitservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedRequestException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UnauthorizedRequestException(String message) {
        super(String.format("Failed for: %s", message));
    }
}
