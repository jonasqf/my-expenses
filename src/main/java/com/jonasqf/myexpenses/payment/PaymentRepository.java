package com.jonasqf.myexpenses.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    Optional<Payment> findById(UUID id);
    @Query("FROM Payment p WHERE p.period = :period")
    Collection<Payment> findByPeriod(LocalDate period);
}