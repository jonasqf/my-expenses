package com.jonasqf.myexpenses.payment;

import com.jonasqf.myexpenses.commitment.Commitment;
import com.jonasqf.myexpenses.commitment.CommitmentMockFactory;
import com.jonasqf.myexpenses.commitment.CommitmentType;
import com.jonasqf.myexpenses.utils.FinancialStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentMockFactory {

    public Payment createMockPayment() {
        Commitment commitment = new CommitmentMockFactory().createMockCommitment();

        return new Payment("Test Description",
                "BILL", 1, new BigDecimal("200.0"),
                new BigDecimal("200.0"),
                commitment,
                LocalDate.of(2023, 4, 15),
                FinancialStatus.CREATED,
                CommitmentType.WANTS);
    }

}
