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

/** The type Earning. */
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

    @NotNull(
            message =
                    "Для синхронізації прибутку повинен бути користувач якому належить ця витрата")
    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "earning_category_id", insertable = false, updatable = false)
    private EarningCategory earningCategory;

    @NotNull(message = "Поле не може бути порожнім")
    @Column(name = "earning_category_id")
    private Long earningCategoryId;

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

    /**
     * Gets earning id.
     *
     * @return the earning id
     */
    public Long getEarningId() {
        return earningId;
    }

    /**
     * Sets earning id.
     *
     * @param earningId the earning id
     */
    public void setEarningId(Long earningId) {
        this.earningId = earningId;
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
     * Gets earning category.
     *
     * @return the earning category
     */
    public EarningCategory getEarningCategory() {
        return earningCategory;
    }

    /**
     * Sets earning category.
     *
     * @param earningCategory the earning category
     */
    public void setEarningCategory(EarningCategory earningCategory) {
        this.earningCategory = earningCategory;
    }

    /**
     * Gets budget.
     *
     * @return the budget
     */
    public Budget getBudget() {
        return budget;
    }

    /**
     * Sets budget.
     *
     * @param budget the budget
     */
    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    /**
     * Gets earning date.
     *
     * @return the earning date
     */
    public Timestamp getEarningDate() {
        return earningDate;
    }

    /**
     * Sets earning date.
     *
     * @param earningDate the earning date
     */
    public void setEarningDate(Timestamp earningDate) {
        this.earningDate = earningDate;
    }

    /**
     * Gets earning amount.
     *
     * @return the earning amount
     */
    public BigDecimal getEarningAmount() {
        return earningAmount;
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
    }

    /**
     * Gets earning category id.
     *
     * @return the earning category id
     */
    public Long getEarningCategoryId() {
        return earningCategoryId;
    }

    /**
     * Sets earning category id.
     *
     * @param earningCategoryId the earning category id
     */
    public void setEarningCategoryId(Long earningCategoryId) {
        this.earningCategoryId = earningCategoryId;
    }


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
     * Sets earning amount.
     *
     * @param earningAmount the earning amount
     */
    public void setEarningAmount(BigDecimal earningAmount) {
        this.earningAmount = earningAmount;
    }

    /**
     * Instantiates a new Earning.
     *
     * @param userId the user id
     * @param earningCategoryId the earning category id
     * @param budgetId the budget id
     * @param earningDate the earning date
     * @param earningAmount the earning amount
     */
    public Earning(
            Long userId,
            Long earningCategoryId,
            Long budgetId,
            Timestamp earningDate,
            BigDecimal earningAmount) {
        this.userId = userId;
        this.earningCategoryId = earningCategoryId;
        this.budgetId = budgetId;
        this.earningDate = earningDate;
        this.earningAmount = earningAmount;
    }

    /** Instantiates a new Earning. */
    public Earning() {}
}
