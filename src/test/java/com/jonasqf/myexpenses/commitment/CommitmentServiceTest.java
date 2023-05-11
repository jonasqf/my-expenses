package com.jonasqf.myexpenses.commitment;

import com.jonasqf.myexpenses.payment.Payment;
import com.jonasqf.myexpenses.payment.PaymentMockFactory;
import com.jonasqf.myexpenses.payment.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
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
    private PaymentService transactionService;

    @Mock
    private CommitmentRepository commitmentRepository;

    private Commitment commitment;
    final UUID commitmentId = UUID.fromString("f0d45730-b812-4b21-a7c1-22a574ebbdb4");


    @BeforeEach
    void setUp() {
        underTest = new CommitmentService(commitmentRepository, transactionService);
        commitment = new CommitmentMockFactory().createMockCommitment();
        commitment.setId(commitmentId);
    }

    @Test
    void registerNewCommitment() {
        when(commitmentRepository.save(any())).thenReturn(commitment);
        Commitment commitmentSaved = underTest.register(commitment);
        assertNotNull(commitmentSaved.getId());
    }

    @Test
    void registerCommitment_whenDownPaymentEqualsToAmount_thenBalanceShouldBeZero() {
        //given
        when(commitmentRepository.save(any())).thenReturn(commitment);
        commitment.setDownPayment(BigDecimal.valueOf(8600.00));
        //when
        underTest.register(commitment);
        //then
        BigDecimal expected = BigDecimal.valueOf(0.00);
        assertThat(commitment.getBalance(), equalTo(expected));
    }

    @Test
    void registerCommitment_thenItShouldCreateSameNumberOfTransactions() {
        //given
        when(commitmentRepository.save(any())).thenReturn(commitment);
        List<Payment> transactionList = new ArrayList();
        Payment transaction1 = new PaymentMockFactory().createMockPayment();
        Payment transaction2 = new PaymentMockFactory().createMockPayment();

        transaction1.setCommitment(commitment);
        transaction2.setCommitment(commitment);
        transactionList.add(transaction1);
        transactionList.add(transaction2);
        when(transactionService.findAll()).thenReturn(transactionList);
        //when
        underTest.register(commitment);
        Collection<Payment> transactionResponse = transactionService.findAll();
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
    void updateCommitment() {
        //when
        underTest.update(commitment);
        //then
        verify(commitmentRepository).save(commitment);
    }
}