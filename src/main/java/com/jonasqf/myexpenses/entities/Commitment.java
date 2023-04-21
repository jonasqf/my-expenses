package com.jonasqf.myexpenses.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Column(name="created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @Column(name="due_date")
    private LocalDate dueDate;

    public Commitment(String status,
                      String type,
                      String description,
                      BigDecimal amount,
                      BigDecimal downPayment,
                      int numberInstallments,
                      UUID accountId,
                      LocalDate dueDate) {
        this.status = status;
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.downPayment = downPayment;
        this.numberInstallments = numberInstallments;
        this.accountId = accountId;
        this.dueDate = dueDate;
    }
}
