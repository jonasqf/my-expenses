package com.jonasqf.myexpenses.commitment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jonasqf.myexpenses.payment.Payment;
import com.jonasqf.myexpenses.utils.FinancialStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    private FinancialStatus status;
    private CommitmentType type;
    private String description;
    private BigDecimal balance;
    private BigDecimal amount;
    @Column(name="total_amount")
    private BigDecimal totalAmount;
    @Column(name="down_payment")
    private BigDecimal downPayment;
    @Column(name="number_installments")
    private int numberInstallments;
    @Column(name="account_id")
    private UUID accountId;
    @Column(name="created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @Column(name="due_date", nullable = false)
    private LocalDate dueDate;

    @OneToMany(mappedBy="commitment")
    private List<Payment> payments = new ArrayList<>();

    public Commitment(FinancialStatus status,
                      CommitmentType type,
                      String description,
                      BigDecimal amount,
                      BigDecimal downPayment,
                      int numberInstallments,
                      UUID accountId,
                      LocalDate dueDate,
                      BigDecimal totalAmount) {
        this.status = status;
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.downPayment = downPayment;
        this.numberInstallments = numberInstallments;
        this.accountId = accountId;
        this.dueDate = dueDate;
        this.totalAmount = totalAmount;
    }
}
