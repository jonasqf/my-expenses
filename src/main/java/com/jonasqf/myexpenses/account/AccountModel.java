package com.jonasqf.myexpenses.account;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.MathContext;

@Entity
@Table(name="account")
public class AccountModel {

    @Id
    @Column(name = "id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
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


    public AccountModel(String description, String category, int numberPayment,
                        BigDecimal totalAmount, BigDecimal downPayment) {
        this.description = description;
        this.category = category;
        this.numberPayment = numberPayment;
        this.totalAmount = totalAmount;
        this.downPayment = downPayment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getNumberPayment() {
        return numberPayment;
    }

    public void setNumberPayment(int numberPayment) {
        this.numberPayment = numberPayment;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(BigDecimal downPayment) {
        this.downPayment = downPayment;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
