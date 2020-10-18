package com.diploma.master.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class AbstractException extends RuntimeException {

    protected AbstractException(String message) {
        super(message);
    }

    protected AbstractException(Throwable exception) {
        super(exception);
    }
}
