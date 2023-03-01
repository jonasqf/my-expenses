package com.jonasqf.myexpenses.account;

import org.junit.jupiter.api.AfterEach;
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
    private AccountModel accountModel;
    private AutoCloseable autoCloseable;


    @BeforeEach
    void setUp() {
        underTest = new AccountService(accountRepository);
        accountModel = new AccountModel("Test Description",
                "BILL", 1, new BigDecimal("200.0"),
                new BigDecimal("200.0"));
    }

    @Test
    void itShouldRegisterAnewAccount() {
        //when
        underTest.register(accountModel);
        //then
        verify(accountRepository).save(accountModel);
    }

    @Test
    void theAccountBalanceShouldBeZero() {
        //when
        underTest.register(accountModel);
        //then
        BigDecimal expected = new BigDecimal("0.0");

        assertThat(expected, equalTo(accountModel.getBalance()));
    }
}