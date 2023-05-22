package com.fanta.moneywithsoul.entity;

import com.fanta.moneywithsoul.validator.OrFutureDate;
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

/**
 * The type Planning cost.
 */
@Entity
@Table(name = "planning_costs")
public class PlanningCost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "planning_cost_id")
    private Long planningCostId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @NotNull(
            message =
                    "Для синхронізації плавнових витрат повинен бути користувач якому належить ця"
                            + " планова витрата")
    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cost_category_id", insertable = false, updatable = false)
    private CostCategory costCategory;

    @NotNull(message = "Категорія витрат не може бути пустою")
    @Column(name = "cost_category_id")
    private Long costCategoryId;

    @NotNull(message = "Дата не може бути пустою")
    @OrFutureDate
    @Column(name = "planning_cost_date")
    private Timestamp planningCostDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_id", insertable = false, updatable = false)
    private Budget budget;

    @NotNull(message = "Потрібно обрати бюджет")
    @Column(name = "budget_id")
    private Long budgetId;

    @NotNull(message = "Сума планового платежу не може бути пустою")
    @Digits(integer = 10, fraction = 2, message = "Сума може бути вказана тільки в цифрах")
    @Column(name = "planned_amount")
    private BigDecimal plannedAmount;

    /**
     * Gets planning cost id.
     *
     * @return the planning cost id
     */
    public Long getPlanningCostId() {
        return planningCostId;
    }

    /**
     * Sets planning cost id.
     *
     * @param planningCostId the planning cost id
     */
    public void setPlanningCostId(Long planningCostId) {
        this.planningCostId = planningCostId;
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
     * Gets planning cost date.
     *
     * @return the planning cost date
     */
    public java.sql.Timestamp getPlanningCostDate() {
        return planningCostDate;
    }

    /**
     * Sets planning cost date.
     *
     * @param planningCostDate the planning cost date
     */
    public void setPlanningCostDate(java.sql.Timestamp planningCostDate) {
        this.planningCostDate = planningCostDate;
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
     * Gets planned amount.
     *
     * @return the planned amount
     */
    public BigDecimal getPlannedAmount() {
        return plannedAmount;
    }

    /**
     * Sets planned amount.
     *
     * @param plannedAmount the planned amount
     */
    public void setPlannedAmount(BigDecimal plannedAmount) {
        this.plannedAmount = plannedAmount;
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
     * Instantiates a new Planning cost.
     *
     * @param planningCostId   the planning cost id
     * @param userId           the user id
     * @param costCategoryId   the cost category id
     * @param planningCostDate the planning cost date
     * @param budgetId         the budget id
     * @param plannedAmount    the planned amount
     */
    public PlanningCost(
            Long planningCostId,
            Long userId,
            Long costCategoryId,
            Timestamp planningCostDate,
            Long budgetId,
            BigDecimal plannedAmount) {
        this.planningCostId = planningCostId;
        this.userId = userId;
        this.costCategoryId = costCategoryId;
        this.planningCostDate = planningCostDate;
        this.budgetId = budgetId;
        this.plannedAmount = plannedAmount;
    }

    /**
     * Instantiates a new Planning cost.
     */
    public PlanningCost() {}
}
