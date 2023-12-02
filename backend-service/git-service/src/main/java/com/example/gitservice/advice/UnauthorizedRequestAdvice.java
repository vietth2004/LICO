package com.example.gitservice.advice;

import com.example.gitservice.dto.ErrorMessage;
import com.example.gitservice.exception.UnauthorizedRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class UnauthorizedRequestAdvice {
    /**
     * Handle exception when token-refresh field expired
     *
     * @param ex
     * @param request
     * @return
     * @ResponseStatus return Forbidden
     */
    @ExceptionHandler(value = UnauthorizedRequestException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage handleUnauthorizedRequestAdvice(UnauthorizedRequestException ex, WebRequest request) {
        return new ErrorMessage(
                HttpStatus.FORBIDDEN.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
    }
}
