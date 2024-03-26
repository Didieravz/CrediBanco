
package com.co.credibanco.exception;

/*
*Clase personalizada que arroja una excepcion cuando no se encuentra una tarjeta
*/

public class CardNotFoundException extends RuntimeException {

    //constructor que acepta un mensaje como argumento
    public CardNotFoundException(String mensaje) {
        //Llama al constructor de la clase Padre y le proporciona el mns
        super(mensaje);
    }
    
    //Constructor que acepta un mensaje y una causa como argumento
    public CardNotFoundException (String mensaje, Throwable causa){
        super(mensaje, causa);
    }
    
    
}
