package com.jonasqf.myexpenses.commitment;

import com.jonasqf.myexpenses.entities.Commitment;
import com.jonasqf.myexpenses.entities.Transaction;
import com.jonasqf.myexpenses.repositories.CommitmentRepository;
import com.jonasqf.myexpenses.services.CommitmentService;
import com.jonasqf.myexpenses.services.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CommitmentServiceTest {

    private CommitmentService underTest;
    private Transaction transaction;
    private TransactionService transactionService;
    @Mock
    private CommitmentRepository commitmentRepository;

    private Commitment commitment;

    @BeforeEach
    void setUp() {
        underTest = new CommitmentService(commitmentRepository, transactionService);
        commitment = new Commitment("CREATED",
                "INCOME",
                "Monthly biweekly salary",
                BigDecimal.valueOf(4300.00),
                BigDecimal.valueOf(0.00),
                1,
                UUID.randomUUID()
                );
    }

    @Test
    void registerNewCommitment() {
        underTest.register(commitment);
        verify(commitmentRepository).save(commitment);
    }

    @Test
    void registerAccount_whenDownPaymentEqualsToAmount_thenBalanceShouldBeZero() {
        //given
        commitment.setDownPayment(BigDecimal.valueOf(4300.00));
        //when
        underTest.register(commitment);
        //then
        BigDecimal expected = BigDecimal.valueOf(0.00);
        assertThat(expected, equalTo(commitment.getBalance()));
    }

    /*@Test
    void registerAccount_whenNumberPaymentsGreaterThenZero_thenItShouldCreateSameNumberOfTransactions() {
        //given
        commitment.setNumberInstallments(6);
        commitment.setDownPayment(BigDecimal.valueOf(0.00));
        //when
        Commitment commitmentRegistered = underTest.register(commitment);
        //then
        transactionService.findby
        assertThat(expected, equalTo(commitment.getBalance()));
    }*/
}