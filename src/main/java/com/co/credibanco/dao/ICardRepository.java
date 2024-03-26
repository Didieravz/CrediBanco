
package com.co.credibanco.dao;

import com.co.credibanco.domain.Card;
import org.springframework.data.repository.CrudRepository;

public interface ICardRepository extends CrudRepository<Card, Long>{
    
}
