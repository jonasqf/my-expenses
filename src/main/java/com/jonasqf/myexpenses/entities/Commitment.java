package com.jonasqf.myexpenses.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name="commitment")
public class Commitment {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String status;
    private String type;
    private String description;
    private BigDecimal balance;
    private BigDecimal amount;
    @Column(name="down_payment")
    private BigDecimal downPayment;
    @Column(name="number_installments")
    private int numberInstallments;
    @Column(name="account_id")
    private UUID accountId;

    public Commitment(String status,
                      String type,
                      String description,
                      BigDecimal amount,
                      BigDecimal downPayment,
                      int numberInstallments,
                      UUID accountId) {
        this.status = status;
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.downPayment = downPayment;
        this.numberInstallments = numberInstallments;
        this.accountId = accountId;
    }
}
