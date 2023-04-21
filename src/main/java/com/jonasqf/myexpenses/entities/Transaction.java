package com.jonasqf.myexpenses.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="transaction")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Transaction {
    @Id
    @Column(name = "id")
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
    @Column(name="commitment_id")
    private UUID commitmentId;
    @Column(name="created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @Column(name="due_date")
    private LocalDate dueDate;
    public Transaction(String description,
                       String category,
                       int numberPayment,
                       BigDecimal totalAmount,
                       BigDecimal downPayment,
                       UUID commitmentId,
                       LocalDate dueDate) {
        this.description = description;
        this.category = category;
        this.numberPayment = numberPayment;
        this.totalAmount = totalAmount;
        this.downPayment = downPayment;
        this.commitmentId = commitmentId;
        this.dueDate = dueDate;
    }

}
