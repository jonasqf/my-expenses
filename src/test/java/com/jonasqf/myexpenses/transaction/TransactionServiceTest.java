package com.jonasqf.myexpenses.transaction;

import com.jonasqf.myexpenses.entities.Transaction;
import com.jonasqf.myexpenses.repositories.TransactionRepository;
import com.jonasqf.myexpenses.services.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @InjectMocks
    private TransactionService underTest;
    @Mock
    private TransactionRepository transactionRepository;
    private Transaction transaction;


    @BeforeEach
    void setUp() {
        underTest = new TransactionService(transactionRepository);
        transaction = new Transaction("Test Description",
                "BILL", 1, new BigDecimal("200.0"),
                new BigDecimal("200.0"));
    }

    @Test
    void itShouldRegisterAnewTransaction() {

        given(transactionRepository.save(transaction)).willAnswer(invocation -> invocation.getArgument(0));
        //when
        Transaction savedTransaction = underTest.register(transaction);
        //then
        assertThat(savedTransaction).isNotNull();
        verify(transactionRepository).save(transaction);
    }

    @Test
    void theTransactionBalanceShouldBeZero() {

        given(transactionRepository.save(transaction)).willAnswer(invocation -> invocation.getArgument(0));
        //when
        Transaction savedTransaction = underTest.register(transaction);
        //then
        BigDecimal expected = new BigDecimal("0.0");

        assertThat(savedTransaction).isNotNull();
        assertThat(expected).isEqualTo(transaction.getBalance());
    }

    @Test
    void itShouldListAllTransaction() {
        //given
        List<Transaction> transactions = new ArrayList();
        transactions.add(new Transaction("Test Description 1",
                "BILL", 1, new BigDecimal("100.0"),
                new BigDecimal("200.0")));
        transactions.add(new Transaction("Test Description 2",
                "BILL", 1, new BigDecimal("300.0"),
                new BigDecimal("200.0")));
        transactions.add(new Transaction("Test Description 1",
                "BILL", 1, new BigDecimal("50.0"),
                new BigDecimal("50.0")));

        //when
        given(transactionRepository.findAll()).willReturn(transactions);

        //then
        Collection<Transaction> expected = underTest.findAll();
        assertEquals(expected, transactions);
    }

    @Test
    void itShouldListTransactionById() {
        //given
        final UUID id = UUID.fromString("f0d45730-b812-4b21-a7c1-22a574ebbdb4");

        given(underTest.findById(id)).willReturn(Optional.of(transaction));
        //when
        final Optional<Transaction> expected = underTest.findById(id);
        //then
        assertThat(expected).isNotNull();
    }

    @Test
    void itShouldDeleteAnTransaction() {
        //when
        underTest.delete(transaction);
        //then
        verify(transactionRepository).delete(transaction);
    }

    @Test
    void itShouldUpdateAnTransaction() {
        underTest.update(transaction);
        //then
        verify(transactionRepository).save(transaction);
    }

}