package com.fanta.entity;

import com.fanta.validator.PastOrPresentDate;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
    @Column(name = "user_id")
    private Long userId;
    @NotEmpty
    @Column(name = "transaction_type")
    private String transactionType;
    @PastOrPresentDate
    @Column(name = "transaction_date")
    private Timestamp transactionDate;
    @Positive
    @Column(name = "transaction_amount")
    private BigDecimal transactionAmount;
    @Size(min = 5, max = 200, message = "Мынымальна довжина інформації про транзакцію = 5 символів, а максимальна 200 символів")
    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exchange_id", insertable = false, updatable = false)
    private ExchangeRate exchangeRateId;

    @Column(name = "exchange_id")
    private Long exchangeId;

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(Long exchangeId) {
        this.exchangeId = exchangeId;
    }

    public Transaction(Long transactionId, Long userId, String transactionType, Timestamp transactionDate, BigDecimal transactionAmount, String description, Long exchangeId, String exchangeName) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.transactionAmount = transactionAmount;
        this.description = description;
        this.exchangeId = exchangeId;
//        this.exchangeName = exchangeName;
    }

    public Transaction() {

    }

}
