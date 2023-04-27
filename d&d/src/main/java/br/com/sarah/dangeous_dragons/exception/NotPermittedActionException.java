package br.com.sarah.dangeous_dragons.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotPermittedActionException extends RuntimeException {
    public NotPermittedActionException(String message) {
        super(message);
    }
}
