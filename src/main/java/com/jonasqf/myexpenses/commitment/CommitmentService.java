package com.jonasqf.myexpenses.commitment;

import com.jonasqf.myexpenses.payment.Payment;
import com.jonasqf.myexpenses.payment.PaymentService;
import com.jonasqf.myexpenses.utils.FinancialStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommitmentService {

    private final CommitmentRepository commitmentRepository;
    private final PaymentService transactionService;
    public CommitmentService(CommitmentRepository commitmentRepository,
                             PaymentService transactionService) {

        this.commitmentRepository = commitmentRepository;
        this.transactionService = transactionService;
    }
    public Commitment register(Commitment commitment) {
        //balance should always be the result of amount - down Payment
        commitment.setTotalAmount(this.getTotalAmount(commitment));
        commitment.setBalance(this.getBalance(commitment).multiply(BigDecimal.ONE.negate()));
        Commitment commitmentSaved;
        try {
            commitmentSaved = commitmentRepository.save(commitment);

            for (int i = 0; i < commitment.getNumberInstallments(); i++) {
                Payment transaction = new Payment(commitment.getDescription(),
                        commitment.getType().toString(),
                        i + 1,
                        commitment.getAmount(),
                        BigDecimal.ZERO,
                        commitment,
                        commitment.getDueDate().plusMonths(i+1),
                        FinancialStatus.CREATED,
                        commitment.getType());
                transactionService.register(transaction);
            }
        } catch (Exception e) {
        throw new RuntimeException(e);
    }
        return commitmentSaved;
    }
    public Collection<Commitment> findAll() {
        return commitmentRepository.findAll();
    }
    public void delete(Commitment commitment) {
        commitmentRepository.delete(commitment);
    }
    public Optional<Commitment> findById(UUID id) {
        return commitmentRepository.findById(id);
    }
    public void update(Commitment commitment) {
        commitment.setBalance(getBalance(commitment));
        commitmentRepository.save(commitment);
    }

    public BigDecimal getBalance(Commitment commitment) {
        return (commitment.getTotalAmount().subtract(commitment.getDownPayment()));
    }

    public BigDecimal getTotalAmount(Commitment commitment) {
        float totalAmount = commitment.getAmount().floatValue();
        totalAmount = totalAmount*commitment.getNumberInstallments();
        return BigDecimal.valueOf(totalAmount);
    }
}
