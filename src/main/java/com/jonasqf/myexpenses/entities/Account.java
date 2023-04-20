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
@Table(name="account")
public class Account {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private BigDecimal balance;
    @Column(name="owner_id")
    private UUID ownerId;
    public Account(BigDecimal balance, UUID ownerId) {
        this.balance = balance;
        this.ownerId = ownerId;
    }
}
