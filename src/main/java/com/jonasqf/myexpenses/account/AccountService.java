package com.jonasqf.myexpenses.account;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountModel register(AccountModel accountModel) {
        BigDecimal currentBalance = BigDecimal.ZERO;
        currentBalance = accountModel.getTotalAmount().subtract(accountModel.getDownPayment());
        accountModel.setBalance(currentBalance);
        return accountRepository.save(accountModel);
    }
}
