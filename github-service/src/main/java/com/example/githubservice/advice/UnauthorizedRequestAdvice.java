package com.example.githubservice.advice;

import com.example.githubservice.dto.ErrorMessage;
import com.example.githubservice.exception.UnauthorizedRequestException;
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
     * @ResponseStatus return Forbidden
     * @param ex
     * @param request
     * @return
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
