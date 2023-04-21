package com.jonasqf.myexpenses.commitment;

import com.jonasqf.myexpenses.transaction.Transaction;
import com.jonasqf.myexpenses.transaction.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommitmentServiceTest {

    private CommitmentService underTest;
    @Mock
    private TransactionService transactionService;

    @Mock
    private CommitmentRepository commitmentRepository;

    private Commitment commitment;
    final UUID commitmentId = UUID.fromString("f0d45730-b812-4b21-a7c1-22a574ebbdb4");


    @BeforeEach
    void setUp() {
        underTest = new CommitmentService(commitmentRepository, transactionService);
        commitment = new Commitment(CommitmentStatus.CREATED,
                CommitmentType.INCOME,
                "Monthly biweekly salary",
                BigDecimal.valueOf(4300.00),
                BigDecimal.valueOf(0.00),
                2,
                UUID.randomUUID(),
                LocalDate.of(2023, 4, 15)
                );
        commitment.setId(commitmentId);
    }

    @Test
    void registerNewCommitment() {
        when(commitmentRepository.save(any())).thenReturn(commitment);
        Commitment commitmentSaved = underTest.register(commitment);
        assertNotNull(commitmentSaved.getId());
    }

    @Test
    void registerAccount_whenDownPaymentEqualsToAmount_thenBalanceShouldBeZero() {
        //given
        when(commitmentRepository.save(any())).thenReturn(commitment);
        commitment.setDownPayment(BigDecimal.valueOf(4300.00));
        //when
        underTest.register(commitment);
        //then
        BigDecimal expected = BigDecimal.valueOf(0.00);
        assertThat(expected, equalTo(commitment.getBalance()));
    }

    @Test
    void registerAccount_thenItShouldCreateSameNumberOfTransactions() {
        //given
        when(commitmentRepository.save(any())).thenReturn(commitment);
        List<Transaction> transactionList = new ArrayList();
        Transaction transaction1 = new Transaction("Monthly biweekly salary","BILL",
                1, new BigDecimal("2150.0"), new BigDecimal("200.0"), UUID.randomUUID(),
                LocalDate.of(2023, 4, 15));
        Transaction transaction2 = new Transaction("Monthly biweekly salary","BILL",
                1, new BigDecimal("2150.0"), new BigDecimal("200.0"), UUID.randomUUID(),
                LocalDate.of(2023, 4, 15));
        transaction1.setCommitmentId(commitmentId);
        transaction2.setCommitmentId(commitmentId);
        transactionList.add(transaction1);
        transactionList.add(transaction2);
        when(transactionService.findAll()).thenReturn(transactionList);
        //when
        underTest.register(commitment);
        Collection<Transaction> transactionResponse = transactionService.findAll();
        //then
        assertNotNull(transactionResponse);
        assertIterableEquals(transactionResponse, transactionList);
        assertEquals(transactionResponse.iterator().next().getTotalAmount(), transactionList.get(0).getTotalAmount());
    }

    @Test
    void getAllCommitments() {
        //when
        underTest.findAll();
        //then
        verify(commitmentRepository).findAll();
    }

    @Test
    void getCommitment_ById() {
        //when
        underTest.findById(commitment.getId());
        //then
        verify(commitmentRepository).findById(commitment.getId());
    }

    @Test
    void deleteCommitment() {
        //when
        underTest.delete(commitment);
        //then
        verify(commitmentRepository).delete(commitment);
    }

    @Test
    void updateAccount() {
        //when
        underTest.update(commitment);
        //then
        verify(commitmentRepository).save(commitment);
    }
}