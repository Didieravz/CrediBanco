package com.co.credibanco.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author usuario
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class TransactionCancellationException extends RuntimeException  {

    public TransactionCancellationException(String message) {
        super(message);
    }

    
}
