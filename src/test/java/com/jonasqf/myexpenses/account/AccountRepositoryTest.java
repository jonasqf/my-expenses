package com.jonasqf.myexpenses.account;

import com.jonasqf.myexpenses.Account.Account;
import com.jonasqf.myexpenses.Account.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class AccountRepositoryTest {
    @Autowired
    private AccountRepository underTest;
    final UUID ownerId = UUID.randomUUID();
    @Test
    void findById() {
        Account account = new Account(BigDecimal.ZERO, null);

        Account accountSaved = underTest.save(account);

        Account accountFound = underTest.findById(accountSaved.getId()).get();

        assertThat(accountFound).isNotNull();
        assertThat(accountFound.getId()).isEqualTo(accountSaved.getId());
    }
}