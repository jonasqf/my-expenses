package com.jonasqf.myexpenses.repositories;

import com.jonasqf.myexpenses.entities.Commitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommitmentRepository extends JpaRepository<Commitment, Integer> {
    Optional<Commitment> findById(UUID id);
}
