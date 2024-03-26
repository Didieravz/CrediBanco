
package com.co.credibanco.controller;

import com.co.credibanco.exception.TransactionCancellationException;
import com.co.credibanco.exception.TransactionNotFoundException;
import com.co.credibanco.service.ITransactionCancelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *Api para cancelar las transacciones
 */
@RestController
@RequestMapping("/api/transaction")
public class TransactionCancelationController {
    
    @Autowired
    private ITransactionCancelationService transactionCancellationService;
    
    //Metodo para cancelar una transaccion
    @PostMapping("/anulation")
    public ResponseEntity<Object> cancelTransaction(@RequestBody Long transactionId) {
        try {
            transactionCancellationService.cancelTransaction(transactionId);
            return ResponseEntity.ok().build();
        } catch (TransactionNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (TransactionCancellationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
    
}
