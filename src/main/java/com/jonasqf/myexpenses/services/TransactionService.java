package com.jonasqf.myexpenses.services;

import com.jonasqf.myexpenses.entities.Transaction;
import com.jonasqf.myexpenses.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction register(Transaction transaction) {
        BigDecimal currentBalance = BigDecimal.ZERO;
        currentBalance = transaction.getTotalAmount().subtract(transaction.getDownPayment());
        transaction.setBalance(currentBalance);
        return transactionRepository.save(transaction);
    }

    public Collection<Transaction> findAll() {
        return transactionRepository.findAll();
    }


    public void delete(Transaction transaction) {
        transactionRepository.delete(transaction);
    }

    public Optional <Transaction> findById(UUID id) {

        return transactionRepository.findById(id);

    }

    public void update(Transaction transaction) {
        transactionRepository.save(transaction);
    }
}
