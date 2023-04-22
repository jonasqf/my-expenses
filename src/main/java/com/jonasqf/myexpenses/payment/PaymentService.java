package com.jonasqf.myexpenses.payment;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {
    private final PaymentRepository transactionRepository;
    public PaymentService(PaymentRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
    public Payment register(Payment transaction) {
        BigDecimal currentBalance;
        currentBalance = transaction.getTotalAmount().subtract(transaction.getDownPayment());
        transaction.setBalance(currentBalance);
        return transactionRepository.save(transaction);
    }
    public Collection<Payment> findAll() {
        return transactionRepository.findAll();
    }
    public void delete(Payment transaction) {
        transactionRepository.delete(transaction);
    }
    public Optional <Payment> findById(UUID id) {
        return transactionRepository.findById(id);
    }
    public void update(Payment transaction) {
        transactionRepository.save(transaction);
    }
}
