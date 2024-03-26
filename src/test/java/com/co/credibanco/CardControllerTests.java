package com.co.credibanco;

import com.co.credibanco.controller.CardController;
import com.co.credibanco.domain.Card;
import com.co.credibanco.service.CardService;
import com.co.credibanco.service.ICardService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CardControllerTests {

    private ICardService cardService;
    private CardController cardController;

    @BeforeEach
    void setUp() {
        cardService = mock(CardService.class);
        cardController = new CardController(cardService);
    }

    @Test
    void testActivateCard() {
        // Preparar datos de prueba
        Card card = new Card();
        card.setCard_id(123L);

        // Simular el comportamiento del servicio
        doNothing().when(cardService).activateCard(card);

        // Ejecutar el método del controlador
        ResponseEntity<String> response = cardController.activateCard(card);

        // Verificar la respuesta
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Tarjeta activada correctamente", response.getBody());

        // Verificar que el metodo del servicio fue llamado con el argumento correcto
        verify(cardService).activateCard(card);
    }
}
