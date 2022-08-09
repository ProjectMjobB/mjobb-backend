package com.mjobb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WebServiceException extends RuntimeException {
    public WebServiceException(String message) {
        super(message);
    }
}


