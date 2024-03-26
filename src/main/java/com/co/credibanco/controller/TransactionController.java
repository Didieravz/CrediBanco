package com.co.credibanco.controller;

import com.co.credibanco.domain.Transaction;
import com.co.credibanco.dto.PurchaseRequestDto;
import com.co.credibanco.exception.TransactionException;
import com.co.credibanco.exception.TransactionNotFoundException;
import com.co.credibanco.service.ITransactionService;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Clase controladora Rest de las transacciones
 */
@RestController
@RequestMapping("/transaction")
@Slf4j
public class TransactionController {

    //Inyeccion de la clase service de negocio
    @Autowired
    ITransactionService transactionService;

    //Metodo encargado de hacer una transaccion de compra
    @PostMapping("/purchase")
    public ResponseEntity<Object>
            makePurchase(@RequestBody PurchaseRequestDto purchaseRequestDto) {

        try {
            Transaction transaction = transactionService.makePurchase(purchaseRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
        } catch (TransactionException e) {
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        }
    }

    //Metodo que consulta una transaccion
    @GetMapping("/{transactionId}")
    public ResponseEntity<Object> getTransaction(@PathVariable Long transactionId) {

        try {
            Transaction transaction = transactionService.getTransaction(transactionId);
            return ResponseEntity.ok().body(transaction);
        } catch (TransactionNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
