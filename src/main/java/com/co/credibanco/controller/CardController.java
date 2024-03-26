
package com.co.credibanco.controller;

import com.co.credibanco.domain.Card;
import com.co.credibanco.exception.CardNotFoundException;
import com.co.credibanco.service.ICardService;
import java.math.BigDecimal;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *Clase controladora Rest de la tarjeta
 */
@RestController
@Slf4j
@RequestMapping("/card")
public class CardController {
    
    private final ICardService cardService;

    public CardController(ICardService cardService) {
        this.cardService = cardService;
    }
    
    //Genera el numero de tarjeta a partir del Id del producto
    @GetMapping("/{productId}/number")
    public ResponseEntity<String> generateCardNumber(@PathVariable ("productId") String productId){
        log.info("Inicia metodo genera numeros tarjeta ");
        
        String cardNumber = cardService.generateCardNumber(productId);
        
        return ResponseEntity.ok(cardNumber);
    }
    
    //Metodo de la api que activa la tarjeta
    @PostMapping("/activate")
    public ResponseEntity<String> activateCard(@RequestBody Card card){
        log.info("Inicia metodo activar tarjeta");
        cardService.activateCard(card);
        return ResponseEntity.ok("Tarjeta activada correctamente");
    }
    
    //Metodo encargado de bloquear una tarjeta
    @DeleteMapping("/{cardId}")
    public ResponseEntity<String> bloquearTarjeta(@PathVariable Long cardId){
        log.info("Inicia metodo bloquear tarjeta");
        cardService.blockCard(cardId);
        return ResponseEntity.ok("Tarjeta bloqueada satisfactoriamente");
    }
    
    //Metodo encargado de recargar saldo
    @PostMapping("/balance")
    public ResponseEntity<String> recargarSaldo(@RequestBody Card card){        
        log.info("Inicia metodo recargar saldo");
        Long cardId = card.getCard_id();
        BigDecimal amount = card.getBalance();
        cardService.racargarSaldo(cardId, amount);
        
        return ResponseEntity.ok("Tarjeta recargada correctamente");
    }
    
    //Metodo encargado de Consulta de saldo
    @GetMapping("balance/{cardId}")
    public ResponseEntity<BigDecimal> consultarSaldo(@PathVariable Long cardId){        
            BigDecimal saldo = cardService.getSaldo(cardId);
            return ResponseEntity.ok(saldo);            
    }
}
