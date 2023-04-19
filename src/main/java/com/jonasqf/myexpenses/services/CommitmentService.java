package com.jonasqf.myexpenses.services;

import com.jonasqf.myexpenses.entities.Commitment;
import com.jonasqf.myexpenses.entities.Transaction;
import com.jonasqf.myexpenses.repositories.CommitmentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommitmentService {

    private final CommitmentRepository commitmentRepository;
    private final TransactionService transactionService;
    public CommitmentService(CommitmentRepository commitmentRepository,
                             TransactionService transactionService) {

        this.commitmentRepository = commitmentRepository;
        this.transactionService = transactionService;
    }
    public Commitment register(Commitment commitment) {
        //balance should always be the result of amount - downPayment
        commitment.setBalance(commitment.getAmount().subtract(commitment.getDownPayment()));
        BigDecimal nrPaymentsBD = BigDecimal.valueOf(commitment.getNumberInstallments());
        BigDecimal installmentAmount = commitment.getBalance().divide(nrPaymentsBD);

        Commitment commitmentSaved = commitmentRepository.save(commitment);
        UUID commitmentId = commitmentSaved.getId();

        for (int i=0;i<commitment.getNumberInstallments();i++){
            Transaction transaction = new Transaction(commitment.getDescription(),
                    commitment.getType(),
                    i+1,
                    installmentAmount,
                    BigDecimal.ZERO,
                    commitmentId);
            transactionService.register(transaction);

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
        commitmentRepository.save(commitment);
    }
}
