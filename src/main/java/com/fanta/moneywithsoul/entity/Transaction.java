package com.fanta.moneywithsoul.entity;

import com.fanta.moneywithsoul.dao.ExchangeRateDAO;
import com.fanta.moneywithsoul.dao.UserDAO;
import com.fanta.moneywithsoul.validator.OnlyLetters;
import com.fanta.moneywithsoul.validator.PastOrPresentDate;
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
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The type Transaction.
 */
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

    @NotNull(
            message =
                    "Для синхронізації транзакції повинен бути користувач якому належить ця"
                            + " транзакція")
    @Column(name = "user_id")
    private Long userId;

    @NotEmpty(message = "Тип транзакції не може бути пустим")
    @OnlyLetters(message = "Тип транзакції може бути вказаний тільки в буквах")
    @Column(name = "transaction_type")
    private String transactionType;

    @NotNull(message = "Дата транзакції не може бути відсутньою")
    @PastOrPresentDate
    @Column(name = "transaction_date")
    private Timestamp transactionDate;

    @NotNull(message = "Сума транзакції не може бути пустою")
    @Digits(integer = 10, fraction = 2, message = "Сума може бути вказана тільки в цифрах")
    @Column(name = "transaction_amount")
    private BigDecimal transactionAmount;

    @Size(
            min = 4,
            max = 200,
            message =
                    "Мінімальна довжина інформації про транзакцію = 4 символи, а максимальна 200"
                            + " символів")
    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exchange_id", insertable = false, updatable = false)
    private ExchangeRate exchangeRateId;

    @NotNull(message = "Валюта транзакції не може бути відсутня")
    @Column(name = "exchange_id")
    private Long exchangeId;

    /**
     * Gets transaction id.
     *
     * @return the transaction id
     */
    public Long getTransactionId() {
        return transactionId;
    }

    /**
     * Sets transaction id.
     *
     * @param transactionId the transaction id
     */
    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * Gets transaction type.
     *
     * @return the transaction type
     */
    public String getTransactionType() {
        return transactionType;
    }

    /**
     * Sets transaction type.
     *
     * @param transactionType the transaction type
     */
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    /**
     * Gets transaction date.
     *
     * @return the transaction date
     */
    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    /**
     * Sets transaction date.
     *
     * @param transactionDate the transaction date
     */
    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }

    /**
     * Gets transaction amount.
     *
     * @return the transaction amount
     */
    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    /**
     * Sets transaction amount.
     *
     * @param transactionAmount the transaction amount
     */
    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
        this.user = new UserDAO().findById(userId);
    }

    /**
     * Gets exchange id.
     *
     * @return the exchange id
     */
    public Long getExchangeId() {
        return exchangeId;
    }

    /**
     * Sets exchange id.
     *
     * @param exchangeId the exchange id
     */
    public void setExchangeId(Long exchangeId) {
        this.exchangeId = exchangeId;
        this.exchangeRateId = new ExchangeRateDAO().findById(exchangeId);
    }

    /**
     * Instantiates a new Transaction.
     *
     * @param transactionId     the transaction id
     * @param userId            the user id
     * @param transactionType   the transaction type
     * @param transactionDate   the transaction date
     * @param transactionAmount the transaction amount
     * @param description       the description
     * @param exchangeId        the exchange id
     */
    public Transaction(
            Long transactionId,
            Long userId,
            String transactionType,
            Timestamp transactionDate,
            BigDecimal transactionAmount,
            String description,
            Long exchangeId) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.transactionAmount = transactionAmount;
        this.description = description;
        this.exchangeId = exchangeId;
    }

    /**
     * Instantiates a new Transaction.
     */
    public Transaction() {}
}
