package com.jonasqf.myexpenses.repositories;

import com.jonasqf.myexpenses.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {


    Optional<Owner> findById(UUID id);
}