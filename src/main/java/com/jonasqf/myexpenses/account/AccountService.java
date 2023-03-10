package com.jonasqf.myexpenses.account;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account register(Account account) {
        BigDecimal currentBalance = BigDecimal.ZERO;
        account.setBalance(currentBalance);
        return accountRepository.save(account);
    }

    public Collection<Account> findAll() {
        return accountRepository.findAll();
    }


    public void delete(Account transaction) {
        accountRepository.delete(transaction);
    }

    public Optional <Account> findById(UUID id) {
        return accountRepository.findById(id);

    }

    public void update(Account transaction) {
        accountRepository.save(transaction);
    }
}
