package com.jonasqf.myexpenses.commitment;

import com.jonasqf.myexpenses.utils.FinancialStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class CommitmentMockFactory {

    public Commitment createMockCommitment() {
        return new Commitment(FinancialStatus.CREATED,
                CommitmentType.INCOME,
                "Monthly biweekly salary",
                BigDecimal.valueOf(4300.00),
                BigDecimal.valueOf(0.00),
                2,
                UUID.randomUUID(),
                LocalDate.of(2023, 4, 15),
                BigDecimal.valueOf(8600.00)
        );
    }
}
