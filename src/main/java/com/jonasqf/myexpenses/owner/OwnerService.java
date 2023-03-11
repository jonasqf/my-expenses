package com.jonasqf.myexpenses.owner;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class OwnerService {

    private final OwnerRepository accountRepository;

    public OwnerService(OwnerRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Owner register(Owner account) {
        BigDecimal currentBalance = BigDecimal.ZERO;
        return accountRepository.save(account);
    }

    public Collection<Owner> findAll() {
        return accountRepository.findAll();
    }


    public void delete(Owner transaction) {
        accountRepository.delete(transaction);
    }

    public Optional <Owner> findById(UUID id) {
        return accountRepository.findById(id);

    }

    public void update(Owner transaction) {
        accountRepository.save(transaction);
    }
}
