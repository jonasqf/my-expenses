package com.jonasqf.myexpenses.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public AccountModel register(AccountModel accountModel) {
        BigDecimal currentBalance = BigDecimal.ZERO;
        currentBalance = accountModel.getTotalAmount().subtract(accountModel.getDownPayment());
        accountModel.setBalance(currentBalance);
        return accountRepository.save(accountModel);
    }
}
