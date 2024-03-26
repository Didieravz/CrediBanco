package com.co.credibanco.service;

public interface ITransactionCancelationService {

    /**
     * Cancela una transacción a partir de su ID.
     *
     * @param transactionId El ID de la transacción que se va a cancelar.
     * @throws TransactionNotFoundException Si no se encuentra ninguna
     * transacción con el ID especificado.
     * @throws TransactionCancellationException Si la transacción no puede ser
     * anulada por alguna razón, como haber pasado más de 24 horas desde su
     * realización.
     */
    void cancelTransaction(Long transactionId);
}
