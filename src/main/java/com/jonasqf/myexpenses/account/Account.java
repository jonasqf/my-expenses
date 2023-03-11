package com.jonasqf.myexpenses.account;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name="transaction")
@NoArgsConstructor
public class Account {

    @Id
    @Column(name = "id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.UUID)
    @Getter
    @Setter
    private UUID id;
    @Column(name="balance")
    @Getter
    @Setter
    private BigDecimal balance;

    @Column(name="owner_id")
    @Getter
    @Setter
    private UUID ownerId;

    public Account(BigDecimal balance, UUID ownerId) {
        this.balance = balance;
        this.ownerId = ownerId;
    }
}
