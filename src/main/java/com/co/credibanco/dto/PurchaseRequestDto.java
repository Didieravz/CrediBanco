
package com.co.credibanco.dto;

import java.math.BigDecimal;
import lombok.Data;

/**
 *Dto que representa la solicitud de compra
 */
@Data
public class PurchaseRequestDto {
    
    private Long card_id;
    private BigDecimal amount;
    
}
