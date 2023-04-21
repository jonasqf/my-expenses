package com.jonasqf.myexpenses.Account;

import com.jonasqf.myexpenses.Account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {


    Optional<Account> findById(UUID id);
}