
package com.co.credibanco.service;

import com.co.credibanco.domain.Transaction;
import com.co.credibanco.dto.PurchaseRequestDto;
import com.co.credibanco.exception.TransactionException;
import com.co.credibanco.exception.TransactionNotFoundException;

public interface ITransactionService {
    
    /**
     * Realiza una compra utilizando la tarjeta especificada en la solicitud de compra.
     * @param PurchaseRequestDto La solicitud de compra que contiene la información 
     * de la tarjeta y el monto de la compra.
     * @return La transacción creada después de realizar la compra.
     * @throws TransactionException Si ocurre algún error durante el proceso de compra.
     */    
    Transaction makePurchase(PurchaseRequestDto purchaseRequestDto) throws TransactionException;
    
    /**
     * Obtiene una transacción por su ID.
     * @param transactionId El ID de la transacción a obtener.
     * @return La transacción correspondiente al ID especificado.
     * @throws TransactionNotFoundException Si no se encuentra ninguna transacción con el ID especificado.
     */
    Transaction getTransaction(Long transactionId) throws TransactionNotFoundException;
}
