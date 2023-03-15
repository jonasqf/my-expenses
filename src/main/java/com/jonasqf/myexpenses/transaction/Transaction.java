package com.jonasqf.myexpenses.transaction;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name="transaction")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Transaction {

    @Id
    @Column(name = "id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "description")

    private String description;
    @Column(name = "category")
    private String category;

    @Column(name="number_payment")
    private int numberPayment;

    @Column(name="total_amount")
    private BigDecimal totalAmount;

    @Column(name="down_payment")
    private BigDecimal downPayment;

    @Column(name="balance")
    private BigDecimal balance;

    public Transaction(String description, String category, int numberPayment, BigDecimal totalAmount, BigDecimal downPayment) {
        this.description = description;
        this.category = category;
        this.numberPayment = numberPayment;
        this.totalAmount = totalAmount;
        this.downPayment = downPayment;
    }

}
