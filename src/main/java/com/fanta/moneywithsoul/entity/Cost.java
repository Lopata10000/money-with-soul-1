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


/**
 * The type Cost.
 */
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

    @NotNull(
            message =
                    "Для синхронізації витрат повинен бути бюджет в межах якого була здійснена ця"
                            + " витрата")
    @Column(name = "budget_id")
    private Long budgetId;

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


    /**
     * Gets cost id.
     *
     * @return the cost id
     */
    public Long getCostId() {
        return costId;
    }


    /**
     * Sets cost id.
     *
     * @param costId the cost id
     */
    public void setCostId(Long costId) {
        this.costId = costId;
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
     * Gets cost category.
     *
     * @return the cost category
     */
    public CostCategory getCostCategory() {
        return costCategory;
    }


    /**
     * Sets cost category.
     *
     * @param costCategory the cost category
     */
    public void setCostCategory(CostCategory costCategory) {
        this.costCategory = costCategory;
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
     * Gets cost date.
     *
     * @return the cost date
     */
    public Timestamp getCostDate() {
        return costDate;
    }


    /**
     * Sets cost date.
     *
     * @param costDate the cost date
     */
    public void setCostDate(Timestamp costDate) {
        this.costDate = costDate;
    }


    /**
     * Gets cost amount.
     *
     * @return the cost amount
     */
    public BigDecimal getCostAmount() {
        return costAmount;
    }


    /**
     * Sets cost amount.
     *
     * @param costAmount the cost amount
     */
    public void setCostAmount(BigDecimal costAmount) {
        this.costAmount = costAmount;
    }


    /**
     * Gets cost description.
     *
     * @return the cost description
     */
    public String getCostDescription() {
        return costDescription;
    }


    /**
     * Sets cost description.
     *
     * @param costDescription the cost description
     */
    public void setCostDescription(String costDescription) {
        this.costDescription = costDescription;
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
     * Gets cost category id.
     *
     * @return the cost category id
     */
    public Long getCostCategoryId() {
        return costCategoryId;
    }


    /**
     * Sets cost category id.
     *
     * @param costCategoryId the cost category id
     */
    public void setCostCategoryId(Long costCategoryId) {
        this.costCategoryId = costCategoryId;
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
     * Instantiates a new Cost.
     *
     * @param userId          the user id
     * @param costCategoryId  the cost category id
     * @param budgetId        the budget id
     * @param costDate        the cost date
     * @param costAmount      the cost amount
     * @param costDescription the cost description
     */
    public Cost(
            Long userId,
            Long costCategoryId,
            Long budgetId,
            Timestamp costDate,
            BigDecimal costAmount,
            String costDescription) {
        this.userId = userId;
        this.costCategoryId = costCategoryId;
        this.budgetId = budgetId;
        this.costDate = costDate;
        this.costAmount = costAmount;
        this.costDescription = costDescription;
    }


    /**
     * Instantiates a new Cost.
     */
    public Cost() {}
}
