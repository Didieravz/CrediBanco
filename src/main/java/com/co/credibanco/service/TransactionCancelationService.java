package com.co.credibanco.service;

import com.co.credibanco.dao.ITransactionRepository;
import com.co.credibanco.domain.Card;
import com.co.credibanco.domain.Transaction;
import com.co.credibanco.exception.TransactionNotFoundException;
import com.co.credibanco.service.CardService;
import com.co.credibanco.exception.TransactionCancellationException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Clase que implementa los metodos de negocio para cancelar una transaccion
 */
@Service
public class TransactionCancelationService implements ITransactionCancelationService{

    @Autowired
    private ITransactionRepository transactionRepository;

    @Autowired
    private CardService cardService;

    @Override
    public void cancelTransaction(Long transactionId) {
        // Buscar la transacción por su ID
        Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
        if (optionalTransaction.isPresent()) {
            Transaction transaction = optionalTransaction.get();
            // Verificar si la transacción está dentro del límite de tiempo para anulación (24 horas)
            if (isWithin24Hours(transaction.getCurrent_time_transaction())) {
                // Marcar la transacción como anulada
                transaction.setCancelled(true);
                transactionRepository.save(transaction);

                // Devolver el valor de la compra al saldo de la tarjeta
                Card card = transaction.getCard();
                BigDecimal amountToRefund = transaction.getAmount();
                BigDecimal currentBalance = card.getBalance();
                BigDecimal newBalance = currentBalance.add(amountToRefund);
                card.setBalance(newBalance);
                cardService.updateCard(card);
            } else {
                throw new TransactionCancellationException
        ("La transacción no puede ser anulada porque ha pasado más de 24 horas desde su creacion");
            }
        } else {
            throw new TransactionNotFoundException("Transacción no encontrada con el ID: " 
                    + transactionId);
        }
    }

    private boolean isWithin24Hours(Date transactionDate) {
        // Obtener la marca de tiempo actual
        LocalDateTime currentTime = LocalDateTime.now();
        // Convertir la fecha de la transacción a LocalDateTime
        LocalDateTime transactionTime = transactionDate.toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime();
        // Calcular la diferencia en horas
        long hoursBetween = ChronoUnit.HOURS.between(transactionTime, currentTime);
        // La transacción es anulable si ha pasado menos de 24 horas desde su realización
        return hoursBetween <= 24;
    }

}
