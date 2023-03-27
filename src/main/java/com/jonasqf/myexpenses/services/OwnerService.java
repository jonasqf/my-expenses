package com.jonasqf.myexpenses.services;

import com.jonasqf.myexpenses.entities.Owner;
import com.jonasqf.myexpenses.repositories.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;

    @Autowired
    public OwnerService(OwnerRepository accountRepository) {
        this.ownerRepository = accountRepository;
    }

    public Owner register(Owner account) {
        BigDecimal currentBalance = BigDecimal.ZERO;
        return ownerRepository.save(account);
    }

    public Collection<Owner> findAll() {
        return ownerRepository.findAll();
    }


    public void delete(Owner transaction) {
        ownerRepository.delete(transaction);
    }

    public Optional <Owner> findById(UUID id) {
        return ownerRepository.findById(id);

    }

    public void update(Owner transaction) {
        ownerRepository.save(transaction);
    }
}
