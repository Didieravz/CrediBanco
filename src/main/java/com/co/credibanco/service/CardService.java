
package com.co.credibanco.service;

import com.co.credibanco.exception.CardNotFoundException;
import com.co.credibanco.dao.ICardRepository;
import com.co.credibanco.domain.Card;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *Clase encargada de implementar la logica de negocio
 * de la tarjeta
 */
@Service
public class CardService implements ICardService{
    
    //Inyeccion del repository
    @Autowired
    ICardRepository cardRepository;
    
    //Metodo encargado de crear el numero de la tarjeta
    @Override
    public String generateCardNumber(String productoId) {
        
        //Verifica si el id producto tiene la longitud correcta (6 digitos)
        if(productoId.length() != 6){
            return "El id producto debe ser de 6 digitos";
        }
        //Genera los 10 digitos extra para el numero de la tarjeta
        StringBuilder digitosAleatorios = new StringBuilder();
        Random random = new Random();
        for(int i=0; i<10; i++){
            digitosAleatorios.append(random.nextInt(10));
        }
        //se crea el numero de la tarjeta completo idProducto + numero aleatorio 10 digits
        String cardNumber = productoId + digitosAleatorios.toString();
        
        return cardNumber;
    }

    //Metodo encargado de activar y guardar los datos de la tarjeta
    @Override
    public void activateCard(Card card) {  
        // Obtener la fecha actual
        Date currentDate = new Date();
        // Calcular la fecha de vencimiento (3 años desde la fecha actual)
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.YEAR, 3);
        Date expirationDate = calendar.getTime();
        
        //establece la fecha de vencimiento y activa tarjeta
        card.setExpiration_date(expirationDate);
        card.setActivated(true);
        cardRepository.save(card);        
    }

    //Metodo de negocio que bloquea una tarjeta
    @Override
    public void blockCard(Long cardId) {
        //Verifica que la tarjeta exista
        Card card = cardRepository.findById(cardId).
                orElseThrow(() -> new IllegalArgumentException("Tarjeta no encontrada"));
        
        //Bloquea la tarjeta        
        card.setBlocked(true);
        card.setActivated(false);
        cardRepository.save(card);
    }

    //Metodo encargado de recargar saldo a al tarjeta
    @Override
    public void racargarSaldo(Long cardId, BigDecimal amount) {
        Card card = cardRepository.findById(cardId).
                orElseThrow(() -> new IllegalArgumentException("Tarjeta no encontrada"));
        
        //Verifica el saldo actual
        BigDecimal saldoActual = card.getBalance();
        //actualiza el sadoActual + recarga        
        card.setBalance(saldoActual.add(amount));
        //Guarda el saldo actualizado
        cardRepository.save(card);
    }
    
    //Metodo encargado de retornar el saldo de la tarjeta
    @Override
    public BigDecimal getSaldo(Long cardId) {
        Optional<Card> card = cardRepository.findById(cardId);
        
        if(card.isPresent()){
            return card.get().getBalance();
        }else{
            throw new CardNotFoundException("Tarjeta no encontrada con el Id: "+ cardId);
        }
    }

    //Metodo que encuentra una tarjeta por id y la retorna
    @Override
    public Card getCard(Long cardId) throws CardNotFoundException {        
        return cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException("No se encontró la tarjeta con el ID: " + cardId));
    }
    
    //Metodo para actualizar la tarjeta
    @Override
    public void updateCard(Card card) {
        cardRepository.save(card);
    }
    
    
}
