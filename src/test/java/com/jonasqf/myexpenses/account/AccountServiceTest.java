package com.jonasqf.myexpenses.account;

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
class AccountServiceTest {

    private AccountService underTest;
    @Mock
    private AccountRepository accountRepository;
    private Account account;


    @BeforeEach
    void setUp() {
        underTest = new AccountService(accountRepository);
        account = new Account();
    }

    @Test
    void itShouldRegisterAnewAccount() {
        //when
        underTest.register(account);
        //then
        verify(accountRepository).save(account);
    }

    @Test
    void theAccountBalanceShouldBeZero() {
        //when
        underTest.register(account);
        //then
        BigDecimal expected = BigDecimal.ZERO;

        assertThat(expected, equalTo(account.getBalance()));
    }

    @Test
    void itShouldListAllAccounts() {
        //when
        underTest.findAll();
        //then
        verify(accountRepository).findAll();
    }

    @Test
    void itShouldListAccountById() {
        //when
        underTest.findById(account.getId());
        //then
        verify(accountRepository).findById(account.getId());
    }

    @Test
    void itShouldDeleteAnAccount() {
        //when
        underTest.delete(account);
        //then
        verify(accountRepository).delete(account);
    }

    @Test
    void itShouldUpdateAnAccount() {
        //when
        underTest.update(account);
        //then
        verify(accountRepository).save(account);
    }

}