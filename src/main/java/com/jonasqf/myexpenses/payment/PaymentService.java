package com.jonasqf.myexpenses.payment;

import com.jonasqf.myexpenses.commitment.Commitment;
import com.jonasqf.myexpenses.commitment.CommitmentRepository;
import com.jonasqf.myexpenses.commitment.CommitmentStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private CommitmentRepository commitmentRepository;
    public PaymentService(PaymentRepository transactionRepository, CommitmentRepository commitmentRepository) {
        this.paymentRepository = transactionRepository;
        this.commitmentRepository = commitmentRepository;
    }
    public Payment register(Payment transaction) {
        BigDecimal currentBalance;
        currentBalance = transaction.getTotalAmount().subtract(transaction.getDownPayment());
        transaction.setBalance(currentBalance);
        return paymentRepository.save(transaction);
    }
    public Collection<Payment> findAll() {
        return paymentRepository.findAll();
    }
    public void delete(Payment transaction) {
        paymentRepository.delete(transaction);
    }
    public Optional <Payment> findById(UUID id) {
        return paymentRepository.findById(id);
    }
    public void update(Payment transaction) {
        if (transaction.getCommitmentId() != null && transaction.getStatus().equals(PaymentStatus.DONE)) {
        Optional<Commitment> commitment = commitmentRepository.findById(transaction.getCommitmentId());
        if (commitment.isPresent()) {
            if (commitment.get().getNumberInstallments() == transaction.getNumberPayment()) {
                commitment.get().setStatus(CommitmentStatus.DONE);
            }
            commitment.get().setDownPayment(transaction.getTotalAmount());

            commitmentRepository.save(commitment.get());
        }
        }
        paymentRepository.save(transaction);}
}
