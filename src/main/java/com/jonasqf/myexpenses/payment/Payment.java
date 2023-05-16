package com.jonasqf.myexpenses.payment;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jonasqf.myexpenses.commitment.Commitment;
import com.jonasqf.myexpenses.commitment.CommitmentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="payment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Payment {
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
    @Column(name="amount_paid")
    private BigDecimal amountPaid;
    @Column(name="balance")
    private BigDecimal balance;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="commitment_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Commitment commitment;
    @Column(name="payment_type")
    private CommitmentType paymentType;
    @Column(name="created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @Column(name="due_date")
    private LocalDate dueDate;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @Column(name="period")
    private LocalDate period;
    @Column(name = "status")
    private PaymentStatus status;
    public Payment(String description,
                   String category,
                   int numberPayment,
                   BigDecimal totalAmount,
                   BigDecimal amountPaid,
                   Commitment commitment,
                   LocalDate dueDate,
                   PaymentStatus status,
                   CommitmentType paymentType) {
        this.description = description;
        this.category = category;
        this.numberPayment = numberPayment;
        this.totalAmount = totalAmount;
        this.amountPaid = amountPaid;
        this.commitment = commitment;
        this.dueDate = dueDate;
        this.status = status;
        this.paymentType = paymentType;
    }
}
