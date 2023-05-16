package com.jonasqf.myexpenses.payment;

import com.jonasqf.myexpenses.commitment.Commitment;
import com.jonasqf.myexpenses.commitment.CommitmentRepository;
import com.jonasqf.myexpenses.utils.FinancialStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private CommitmentRepository commitmentRepository;
    public PaymentService(PaymentRepository transactionRepository, CommitmentRepository commitmentRepository) {
        this.paymentRepository = transactionRepository;
        this.commitmentRepository = commitmentRepository;
    }
    public Payment register(Payment payment) {
        payment.setPeriod(payment.getDueDate().with(lastDayOfMonth()));
        payment.setBalance(getPaymentBalance(payment));
        return paymentRepository.save(payment);
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
    public void update(Payment payment) {
        payment.setBalance(getPaymentBalance(payment));
        paymentRepository.save(payment);
        updateCommitment(payment);
    }
    public BigDecimal getPaymentBalance(Payment payment) {
       return payment.getAmountPaid().subtract(payment.getTotalAmount());
    }
    public void updateCommitment(Payment payment) {
        if (payment.getCommitment() != null) {
            Optional<Commitment> commitment = commitmentRepository.findById(payment.getCommitment().getId());
            if (commitment.isPresent()) {
                if (payment.getStatus().equals(FinancialStatus.DONE)) {
                   commitment.get().setBalance(commitment.get().getBalance().add(payment.getAmountPaid()));
                }
                if (commitment.get().getNumberInstallments() == payment.getNumberPayment()) {
                    commitment.get().setStatus(FinancialStatus.DONE);
                }
                commitmentRepository.save(commitment.get());
            }

        }
    }
    public Collection<Payment> findByPeriod(LocalDate period) {
        return paymentRepository.findByPeriod(period);
    }

}
