
package com.co.credibanco.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

/**
 *Entidad que representa la tabla transaction en BD
 */
@Entity
@Table(name="transaction", schema="credi_banco")
@Data
public class Transaction implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_transaction;
    
    @ManyToOne
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;
    
    private BigDecimal amount;
    private boolean cancelled;
    private Date current_time_transaction;
}
