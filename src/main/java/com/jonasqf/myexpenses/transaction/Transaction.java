package com.jonasqf.myexpenses.transaction;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name="transaction")
@NoArgsConstructor
public class Transaction {

    @Id
    @Column(name = "id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Integer id;
    @Column(name = "description")
    @Getter
    @Setter
    private String description;
    @Column(name = "category")
    @Getter
    @Setter
    private String category;

    @Column(name="number_payment")
    @Getter
    @Setter
    private int numberPayment;

    @Column(name="total_amount")
    @Getter
    @Setter
    private BigDecimal totalAmount;

    @Column(name="down_payment")
    @Getter
    @Setter
    private BigDecimal downPayment;

    @Column(name="balance")
    @Getter
    @Setter
    private BigDecimal balance;

}
