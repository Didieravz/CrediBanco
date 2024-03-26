
package com.co.credibanco.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 *Entidad de Java que representa la tabla card en BD
 */
@Entity
@Data
@Table(name="card")
public class Card implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long card_id;
    
    private String card_number;
    
    private String nombre_titular;
    
    private Date expiration_date;
    
    private boolean activated;
    
    private BigDecimal balance;
    
    private boolean blocked;
    
}
