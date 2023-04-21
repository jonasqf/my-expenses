package com.jonasqf.myexpenses.owner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Owner register(Owner owner) {
        return ownerRepository.save(owner);
    }
    public Collection<Owner> findAll() {
        return ownerRepository.findAll();
    }
    public void delete(Owner owner) {
        ownerRepository.delete(owner);
    }
    public Optional <Owner> findById(UUID id) {
        return ownerRepository.findById(id);
    }
    public void update(Owner owner) {
        ownerRepository.save(owner);
    }
}
