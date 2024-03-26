package com.co.credibanco.exception;

import org.springframework.http.HttpStatus;

/**
 *Clase que genera una excepcion cuando ocurre un error en la transaccion
 */
public class TransactionException extends Exception{
    
    private HttpStatus httpStatus;

    public TransactionException(String message) {
        super(message);
    }

    public TransactionException(String message, HttpStatus  httpStatus ) {
        super(message);
        this.httpStatus = httpStatus;
    }
    
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    
}
