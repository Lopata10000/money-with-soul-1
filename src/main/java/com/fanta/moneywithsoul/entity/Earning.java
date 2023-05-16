package com.fanta.moneywithsoul.entity;

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
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "earnings")
public class Earning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "earning_id")
    private Long earningId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @NotNull(message = "Для синхронізації прибутку повинен бути користувач якому належить ця витрата")
    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "earning_category_id", insertable = false, updatable = false)
    private EarningCategory earningCategory;

    @NotNull(message = "Поле не може бути порожнім")
    @Column(name = "earning_category_id")
    private Long earningCategoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", insertable = false, updatable = false)
    private Transaction transaction;

    @NotNull(message = "Для синхронізації прибутку повинна бути транзакція якій належить цей прибуток")
    @Column(name = "transaction_id")
    private Long transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_id", insertable = false, updatable = false)
    private Budget budget;

    @NotNull(message = "Для синхронізації прибутку повинен бути бюджет в межах якого був прибуток")
    @Column(name = "budget_id")
    private Long budgetId;

    @NotNull(message = "Дата прибутку не може бути відсутньою")
    @PastOrPresentDate
    @Column(name = "earning_date")
    private Timestamp earningDate;

    @NotNull(message = "Сума прибутку не може бути пустою")
    @Digits(integer = 10, fraction = 2, message = "Сума може бути вказана тільки в цифрах")
    @Column(name = "earning_amount")
    private BigDecimal earningAmount;

    public Long getEarningId() {
        return earningId;
    }

    public void setEarningId(Long earningId) {
        this.earningId = earningId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public EarningCategory getEarningCategory() {
        return earningCategory;
    }

    public void setEarningCategory(EarningCategory earningCategory) {
        this.earningCategory = earningCategory;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public Timestamp getEarningDate() {
        return earningDate;
    }

    public void setEarningDate(Timestamp earningDate) {
        this.earningDate = earningDate;
    }

    public BigDecimal getEarningAmount() {
        return earningAmount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getEarningCategoryId() {
        return earningCategoryId;
    }

    public void setEarningCategoryId(Long earningCategoryId) {
        this.earningCategoryId = earningCategoryId;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Long budgetId) {
        this.budgetId = budgetId;
    }

    public void setEarningAmount(BigDecimal earningAmount) {
        this.earningAmount = earningAmount;
    }

    public Earning(Long earningId, Long userId, Long earningCategoryId, Long transactionId, Long budgetId, Timestamp earningDate, BigDecimal earningAmount) {
        this.earningId = earningId;
        this.userId = userId;
        this.earningCategoryId = earningCategoryId;
        this.transactionId = transactionId;
        this.budgetId = budgetId;
        this.earningDate = earningDate;
        this.earningAmount = earningAmount;
    }

    public Earning() {}

}
