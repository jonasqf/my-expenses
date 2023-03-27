package com.jonasqf.myexpenses.owner;

import com.jonasqf.myexpenses.entities.Transaction;
import com.jonasqf.myexpenses.repositories.TransactionRepository;
import com.jonasqf.myexpenses.services.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OwnerServiceTest {

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
    void itShouldRegisterAnewAccount() {
        //when
        underTest.register(transaction);
        //then
        verify(transactionRepository).save(transaction);
    }

    @Test
    void theAccountBalanceShouldBeZero() {
        //when
        underTest.register(transaction);
        //then
        BigDecimal expected = new BigDecimal("0.0");

        assertThat(expected, equalTo(transaction.getBalance()));
    }

    @Test
    void itShouldListAllAccounts() {
        //when
        underTest.findAll();
        //then
        verify(transactionRepository).findAll();
    }

    @Test
    void itShouldListAccountById() {
        //when
        underTest.findById(transaction.getId());
        //then
        verify(transactionRepository).findById(transaction.getId());
    }

    @Test
    void itShouldDeleteAnAccount() {
        //when
        underTest.delete(transaction);
        //then
        verify(transactionRepository).delete(transaction);
    }

    @Test
    void itShouldUpdateAnAccount() {
        //when
        underTest.update(transaction);
        //then
        verify(transactionRepository).save(transaction);
    }

}