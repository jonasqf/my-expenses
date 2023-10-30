package com.jonasqf.myexpenses.bill;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BillRepository extends JpaRepository<Bill, Long> {
    Bill findById(UUID billId);
}
