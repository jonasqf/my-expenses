package com.jonasqf.myexpenses.bill;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name="bill")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String description;
    private BigDecimal amount;
    private LocalDate dueDate;

    public Bill(){

    }
    public Bill(String description, BigDecimal amount, LocalDate dueDate) {
        this.description = description;
        this.amount = amount;
        this.dueDate = dueDate;
    }

}
