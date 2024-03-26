package com.co.credibanco.service;

import com.co.credibanco.domain.Card;
import com.co.credibanco.exception.CardNotFoundException;
import java.math.BigDecimal;

/**
 *Interface que contiene los metodos necesarios
 * para las operaciones sobre la tarjeta
 */
public interface ICardService {
    
    String generateCardNumber(String productoId);
    
    void activateCard(Card card);
    
    void blockCard(Long cardId);
    
    void racargarSaldo(Long cardId, BigDecimal amount);
    
    BigDecimal getSaldo(Long cardId);
    
    Card getCard(Long cardId) throws CardNotFoundException ;
    
    void updateCard(Card card);
    
}
