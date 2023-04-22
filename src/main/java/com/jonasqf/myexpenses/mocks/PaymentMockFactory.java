package com.jonasqf.myexpenses.mocks;

import com.jonasqf.myexpenses.payment.Payment;
import com.jonasqf.myexpenses.payment.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class PaymentMockFactory {

    public Payment createMockPayment() {
        return new Payment("Test Description",
                "BILL", 1, new BigDecimal("200.0"),
                new BigDecimal("200.0"),
                UUID.randomUUID(),
                LocalDate.of(2023, 4, 15),
                PaymentStatus.CREATED);
    }

}
