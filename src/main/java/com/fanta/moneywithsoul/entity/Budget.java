package com.fanta.moneywithsoul.entity;

import com.fanta.moneywithsoul.dao.UserDAO;
import com.fanta.moneywithsoul.validator.OnlyLetters;
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

/**
 * The type Budget.
 */
@Entity
@Table(name = "budgets")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "budget_id")
    private Long budgetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @NotNull(
            message = "Для синхронізації бюджета повинен бути користувач якому належить цей бюджет")
    @Column(name = "user_id")
    private Long userId;

    @NotEmpty(message = "Назва бюджета не може бути порожня")
    @OnlyLetters(message = "Назва бюджету може бути вказаний тільки в буквах")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Дата початку не може бути порожня")
    //    @PastOrPresentDate
    @Column(name = "start_date")
    private Timestamp startDate;

    //    @ChronologicalDates(startDate = "startDate", endDate = "endDate")
    @Column(name = "end_date")
    private Timestamp endDate;

    @NotNull(message = "Сума бюджету не може бути порожня")
    @Digits(integer = 10, fraction = 2, message = "Сума може бути вказана тільки в цифрах")
    @Column(name = "amount")
    private BigDecimal amount;

    /**
     * Instantiates a new Budget.
     */
    public Budget() {}

    /**
     * Gets budget id.
     *
     * @return the budget id
     */
    public Long getBudgetId() {
        return budgetId;
    }

    /**
     * Sets budget id.
     *
     * @param budgetId the budget id
     */
    public void setBudgetId(Long budgetId) {
        this.budgetId = budgetId;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets start date.
     *
     * @return the start date
     */
    public Timestamp getStartDate() {
        return startDate;
    }

    /**
     * Sets start date.
     *
     * @param startDate the start date
     */
    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets end date.
     *
     * @return the end date
     */
    public Timestamp getEndDate() {
        return endDate;
    }

    /**
     * Sets end date.
     *
     * @param endDate the end date
     */
    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets amount.
     *
     * @return the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Sets amount.
     *
     * @param amount the amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
     * Instantiates a new Budget.
     *
     * @param budgetId  the budget id
     * @param userId    the user id
     * @param name      the name
     * @param startDate the start date
     * @param endDate   the end date
     * @param amount    the amount
     */
    public Budget(
            Long budgetId,
            Long userId,
            String name,
            Timestamp startDate,
            Timestamp endDate,
            BigDecimal amount) {
        this.budgetId = budgetId;
        this.userId = userId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
    }
}
