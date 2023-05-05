package com.jonasqf.myexpenses.commitment;

import com.jonasqf.myexpenses.payment.Payment;
import com.jonasqf.myexpenses.payment.PaymentService;
import com.jonasqf.myexpenses.payment.PaymentStatus;
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
        commitment.setBalance(updateBalance(commitment));
        BigDecimal nrPaymentsBD = BigDecimal.valueOf(commitment.getNumberInstallments());
        float totalAmount = commitment.getAmount().floatValue();
        totalAmount = totalAmount*commitment.getNumberInstallments();
        commitment.setTotalAmount(BigDecimal.valueOf(totalAmount));
        Commitment commitmentSaved;
        UUID commitmentId;
        try {
            commitmentSaved = commitmentRepository.save(commitment);
            commitmentId = commitmentSaved.getId();

            for (int i = 0; i < commitment.getNumberInstallments(); i++) {
                Payment transaction = new Payment(commitment.getDescription(),
                        commitment.getType().toString(),
                        i + 1,
                        commitment.getAmount(),
                        BigDecimal.ZERO,
                        commitmentId,
                        commitment.getDueDate().plusMonths(i+1),
                        PaymentStatus.CREATED,
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
        commitment.setBalance(updateBalance(commitment));
        commitmentRepository.save(commitment);
    }

    public BigDecimal updateBalance(Commitment commitment) {
        return (commitment.getAmount().subtract(commitment.getDownPayment()));
    }
}
