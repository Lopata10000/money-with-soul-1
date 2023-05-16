package com.fanta.moneywithsoul.entity;

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

@Entity
@Table(name = "costs")
public class Cost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cost_id")
    private Long costId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @NotNull(message = "Для синхронізації витрат повинен бути користувач якому належить ця витрата")
    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cost_category_id", insertable = false, updatable = false)
    private CostCategory costCategory;

    @NotNull(message = "Поле не може бути порожнім")
    @Column(name = "cost_category_id")
    private Long costCategoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_id", insertable = false, updatable = false)
    private Budget budget;

    @NotNull(message = "Для синхронізації витрат повинен бути бюджет в межах якого була здійснена ця витрата")
    @Column(name = "budget_id")
    private Long budgetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", insertable = false, updatable = false)
    private Transaction transaction;

    @NotNull(message = "Для синхронізації витрат повинна бути транзакція якій належить ця витрата")
    @Column(name = "transaction_id")
    private Long transactionId;

    @NotNull(message = "Дата витрати не може бути відсутньою")
    @PastOrPresentDate
    @Column(name = "cost_date")
    private Timestamp costDate;

    @NotNull(message = "Сума витрати не може бути пустою")
    @Digits(integer = 10, fraction = 2, message = "Сума може бути вказана тільки в цифрах")
    @Column(name = "cost_amount")
    private BigDecimal costAmount;

    @OnlyLetters
    @NotEmpty(message = "Інформація про витрату не може бути відсутньою")
    @Column(name = "cost_description")
    private String costDescription;

    public Long getCostId() {
        return costId;
    }

    public void setCostId(Long costId) {
        this.costId = costId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CostCategory getCostCategory() {
        return costCategory;
    }

    public void setCostCategory(CostCategory costCategory) {
        this.costCategory = costCategory;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Timestamp getCostDate() {
        return costDate;
    }

    public void setCostDate(Timestamp costDate) {
        this.costDate = costDate;
    }

    public BigDecimal getCostAmount() {
        return costAmount;
    }

    public void setCostAmount(BigDecimal costAmount) {
        this.costAmount = costAmount;
    }

    public String getCostDescription() {
        return costDescription;
    }

    public void setCostDescription(String costDescription) {
        this.costDescription = costDescription;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCostCategoryId() {
        return costCategoryId;
    }

    public void setCostCategoryId(Long costCategoryId) {
        this.costCategoryId = costCategoryId;
    }

    public Long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Long budgetId) {
        this.budgetId = budgetId;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Cost(Long costId, Long userId, Long costCategoryId, Long budgetId, Long transactionId, Timestamp costDate, BigDecimal costAmount, String costDescription) {
        this.costId = costId;
        this.userId = userId;
        this.costCategoryId = costCategoryId;
        this.budgetId = budgetId;
        this.transactionId = transactionId;
        this.costDate = costDate;
        this.costAmount = costAmount;
        this.costDescription = costDescription;
    }

    public Cost() {
    }
}
