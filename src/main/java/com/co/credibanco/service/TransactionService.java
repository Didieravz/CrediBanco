package com.co.credibanco.service;

import com.co.credibanco.dao.ITransactionRepository;
import com.co.credibanco.domain.Card;
import com.co.credibanco.domain.Transaction;
import com.co.credibanco.dto.PurchaseRequestDto;
import com.co.credibanco.exception.TransactionException;
import com.co.credibanco.exception.TransactionNotFoundException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 *Clase encargada de implementar los metodos de la logica de negocio 
 * para las transacciones
 */
@Service
public class TransactionService implements ITransactionService{
    
    //Inyeccion del repositorio para la persistencia a la BD
    @Autowired
    ITransactionRepository transactionRepository;
    
    //Inyeccion de CardService para realizar operaciones en Card
    @Autowired
    ICardService cardService;
    
    @Override
    public Transaction makePurchase(PurchaseRequestDto purchaseRequestDto) throws TransactionException {
        //Verifica que la tarjeta este activa
        Card card = cardService.getCard(purchaseRequestDto.getCard_id());
        if(!card.isActivated()){
            throw new TransactionException("La tarjeta no esta activa", HttpStatus.BAD_REQUEST);
        }
        //Verifica que el saldo de la tarjeta sea suficiente para la compra
        if(card.getBalance().compareTo(purchaseRequestDto.getAmount()) < 0){
            throw new TransactionException("Saldo insuficiente en la tarjeta", HttpStatus.BAD_REQUEST);
        }
        
        //Realiza el proceso de compra
        Transaction transaction = new Transaction();
//        transaction.setCard_id(purchaseRequestDto.getCard_id());
        transaction.setCard(card);
        transaction.setAmount(purchaseRequestDto.getAmount());
        
        //Se guarda el tiempo de la transaccion
        //Genera tiempo actual para la transaccion
        Date currentTimeTransaction = new Date();
        transaction.setCurrent_time_transaction(currentTimeTransaction);
        
        //Actualiza el saldo en la tarjeta
        BigDecimal nuevoSaldo = card.getBalance().subtract(purchaseRequestDto.getAmount());
        card.setBalance(nuevoSaldo);
        cardService.updateCard(card);
        
        return transactionRepository.save(transaction);
        
    }

    @Override
    public Transaction getTransaction(Long transactionId) throws TransactionNotFoundException {
        
        //Busca la transaccion por el id
        Optional<Transaction> transaction = transactionRepository.findById(transactionId);
        if(transaction.isPresent()){
            return transaction.get();
        }else{
            throw new TransactionNotFoundException("Transacción no encontrada con el ID: " + transactionId);
        }
    }
    
}
