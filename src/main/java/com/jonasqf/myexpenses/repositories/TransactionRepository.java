package com.jonasqf.myexpenses.repositories;

import com.jonasqf.myexpenses.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {


    Optional<Transaction> findById(UUID id);
}