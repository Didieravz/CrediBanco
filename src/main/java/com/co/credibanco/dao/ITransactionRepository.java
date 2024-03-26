package com.co.credibanco.dao;

import com.co.credibanco.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITransactionRepository extends JpaRepository<Transaction, Long>{
    
}
